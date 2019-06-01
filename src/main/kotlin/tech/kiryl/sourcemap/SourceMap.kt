package tech.kiryl.sourcemap

data class SourceMap(
    val version: Int,
    val file: String,
    val sourceRoot: String,
    val sources: List<String>,
    val names: List<String>,
    val mappings: String,
    val sourcesContent: List<String>
)

data class Pointer(val startingLine: Int, val startingCol: Int) {

    operator fun minus(that: Pointer) = Pointer(
        startingLine = this.startingLine - that.startingLine,
        startingCol = this.startingCol - that.startingCol
    )

}

data class Field(
    val gen: Pointer,
    val sourcesIndex: Int = 0,
    val orig: Pointer = Pointer(0, 0),
    val namesIndex: Int = 0
) {

    operator fun minus(that: Field) = Field(
        gen = if (this.gen.startingLine != that.gen.startingLine)
            this.gen.copy(startingLine = this.gen.startingLine - that.gen.startingLine)
        else
            this.gen - that.gen,
        sourcesIndex = this.sourcesIndex - that.sourcesIndex,
        orig = this.orig - that.orig,
        namesIndex = this.namesIndex - that.namesIndex
    )

    fun toList() = listOfNotNull(
        this.gen.startingCol,
        this.sourcesIndex,
        this.orig.startingLine,
        this.orig.startingCol,
        this.namesIndex.takeUnless { it == 0 }
    )

}