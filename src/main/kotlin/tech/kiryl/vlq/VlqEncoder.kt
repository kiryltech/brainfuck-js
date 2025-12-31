package tech.kiryl.vlq

private const val VLQ_BASE_SHIFT = 5
private const val VLQ_BASE = 1 shl VLQ_BASE_SHIFT

private val BASE64_DIGITS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray()

fun encode(vararg input: List<Int>): String {
    return input.joinToString(",") {
        it.joinToString("") { it.encode() }
    }
}

private fun Int.encode(): String {
    var value = if (this < 0) ((-this) shl 1) or 1 else this shl 1
    var result = ""
    do {
        var digit = value and VLQ_BASE - 1
        value = value ushr VLQ_BASE_SHIFT
        if (value > 0) {
            digit = digit or VLQ_BASE
        }
        result += BASE64_DIGITS[digit]
    } while (value > 0)
    return result
}

