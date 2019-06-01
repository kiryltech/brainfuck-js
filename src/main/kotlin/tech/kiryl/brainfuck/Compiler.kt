@file:JvmName("Compiler")

package tech.kiryl.brainfuck

import java.io.File
import java.io.PrintStream

const val GEN_INDENT = 2

fun main(args: Array<String>) {
    val sourceFile = File(args[0])
    val outputLines = mutableListOf(
        "let print = (it) => { process.stdout.write(String.fromCharCode(it)) };",
        "((ptr, data) => {"
    )

    sourceFile.readLines().forEach { lineText ->
        lineText.toCharArray().forEach { cmd ->
            BrainfuckCommands.byCmd[cmd]?.run {
                outputLines += " " * GEN_INDENT + jsCmd
            }
        }
    }

    val outputFile = File("target/${sourceFile.name.removeSuffix(".bf") + ".js"}")
    outputLines += "})(0, Array.from(Array(1024)).map(()=>0));"

    PrintStream(outputFile).run { outputLines.forEach(::println) }

    println("Compiled: $sourceFile -> $outputFile")
}

private operator fun String.times(v: Int) = (0 until v).joinToString(separator = "") { this }
