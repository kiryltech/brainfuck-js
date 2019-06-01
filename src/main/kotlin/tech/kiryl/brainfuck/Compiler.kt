@file:JvmName("Compiler")

package tech.kiryl.brainfuck

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import tech.kiryl.sourcemap.Field
import tech.kiryl.sourcemap.Pointer
import tech.kiryl.sourcemap.SourceMap
import tech.kiryl.vlq.encode
import java.io.File
import java.io.PrintStream
import java.util.*

const val GEN_INDENT = 2

fun main(args: Array<String>) {
    val sourceFile = File(args[0])
    val mappings = mutableListOf(Field(gen = Pointer(0, 0)))
    val outputLines = mutableListOf(
        "let print = (it) => { process.stdout.write(String.fromCharCode(it)) };",
        "((ptr, data) => {"
    )

    sourceFile.readLines().forEachIndexed { lineNum, lineText ->
        lineText.toCharArray().forEachIndexed { posNum, cmd ->
            BrainfuckCommands.byCmd[cmd]?.run {
                mappings.populateSourceMap(this, outputLines.size, GEN_INDENT, lineNum, posNum)
                outputLines += " " * GEN_INDENT + jsCmd
            }
        }
    }

    val outputFile = File("target/${sourceFile.name.removeSuffix(".bf") + ".js"}")
    val sourceMapJson = mappings.createSourceMapModel(outputFile, sourceFile).toJson()
    outputLines += "})(0, Array.from(Array(1024)).map(()=>0));" +
            "//# sourceMappingURL=data:application/json;base64,${sourceMapJson.base64()}"

    PrintStream(outputFile).run { outputLines.forEach(::println) }

    println("Compiled: $sourceFile -> $outputFile")
}

private fun MutableList<Field>.createSourceMapModel(outputFile: File, sourceFile: File) =
    SourceMap(
        version = 3,
        file = outputFile.name,
        sourceRoot = "",
        sources = listOf(sourceFile.name),
        names = listOf(),
        mappings = zipWithNext { a, b -> b - a }.joinToString("") {
            (if (it.gen.startingLine == 0) "," else ";" * it.gen.startingLine) + encode(it.toList())
        },
        sourcesContent = listOf(sourceFile.readText())
    )

private fun MutableList<Field>.populateSourceMap(
    cmd: BrainfuckCommands,
    genLine: Int,
    genPos: Int,
    origLine: Int,
    origPos: Int
) {
    if (this.last().orig.startingLine != origLine) {
        this += Field(
            gen = Pointer(genLine, 0),
            orig = Pointer(origLine, 0)
        )
    }
    this += Field(
        gen = Pointer(genLine, genPos),
        orig = Pointer(origLine, origPos)
    )
    this += Field(
        gen = Pointer(genLine, genPos + cmd.jsCmd.length),
        orig = Pointer(origLine, origPos + 1)
    )
}

private operator fun String.times(v: Int) = (0 until v).joinToString(separator = "") { this }

private val objectMapper = jacksonObjectMapper()

private fun Any?.toJson() = objectMapper.writeValueAsString(this)

private fun String.base64() = Base64.getEncoder().encodeToString(toByteArray())

