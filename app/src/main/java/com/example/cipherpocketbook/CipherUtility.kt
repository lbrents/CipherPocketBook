package com.example.cipherpocketbook

fun Int.isMultipleOf(value: Int): Boolean = (this % value) == 0
fun Int.isEven(): Boolean = this.isMultipleOf(2)
fun Int.isOdd(): Boolean = !this.isEven()

infix fun Int.gcd(value: Int): Int {
    var a = this
    var b = value
    while (b != 0) {
        val i = b
        b = a % b
        a = i
    }

    return a
}

fun String.safeShift(index: Int, shift: Int): Char {
    return this[Math.floorMod(index + shift, this.length)]
}
fun String.safeShift(char: Char, shift: Int, errorChar: Char? = null): Char {
    val index = this.indexOf(char)
    if (index == -1) return errorChar ?: char
    return this.safeShift(index, shift)
}

fun String.chunkIntoColumns(size: Int, padChar: Char): List<String> {
    val rows = this.chunked(size) { it.padEnd(size, padChar) }
    return (0..<size).map { i -> rows.map { it[i] }.joinToString("") }
}
fun String.repeatToLength(length: Int): String {
    return (0..<length)
        .map { this[it % this.length] }
        .joinToString("")
}
fun String.splitAtIndex(index: Int) = take(index) to substring(index)

object Alphabet {

    fun lowercase(): String = ('a'..'z').joinToString("")
    fun uppercase(): String = ('A'..'Z').joinToString("")
    fun numbers(): String   = ('0'..'9').joinToString("")
    fun special(): String   = (
            (':'..'@').joinToString("") +
                    ('['..'`').joinToString("") +
                    ('{'..'~').joinToString("")
            )

    fun custom(
        lowercase: Boolean = false,
        whitespace: Boolean = false,
        numbers: Boolean = false,
        special: Boolean = false
    ): String {
        var alphabet = if (lowercase) lowercase() else uppercase()
        if (whitespace) alphabet += ' '
        if (numbers) alphabet += numbers()
        if (special) alphabet += special()

        return alphabet
    }

    fun createShuffledAlphanumeric(): String = (uppercase() + numbers())
        .toList()
        .shuffled()
        .joinToString("")

}
