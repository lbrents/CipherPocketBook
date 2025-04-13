package com.example.cipherpocketbook.ciphers

import com.example.cipherpocketbook.Alphabet
import com.example.cipherpocketbook.isEven
import com.example.cipherpocketbook.isOdd
import com.example.cipherpocketbook.splitAtIndex
import kotlin.text.uppercase

private fun playfairSquare(key: String, alphabet: String): String {
    return key
        .uppercase()
        .let { k ->
            val included = mutableListOf<Char>()
            val ttKey = k.filter { char ->
                if (char !in included) { included.add(char); true }
                else false }
            return@let ttKey + alphabet.filter { it !in included }
        }
}

private fun String.findPlayfairDouble(): Int {
    this.indices.forEach { index ->
        // Check if on odd index
        if (index.isOdd()) return@forEach
        // Check if next character exists
        if ( (index + 1) >= this.length) return@forEach
        // If next char is same, return current index
        if (this[index] == this[index + 1]) return index + 1
    }
    return -1
}
private fun String.genPlayfairDigrams(aBogus: Char, bBogus: Char): String {
    var current = ""
    var next = this

    var index = this.findPlayfairDouble()

    while (index != -1) {

        val split = next.splitAtIndex(index)
        current += split.first + if (split.first.last() != aBogus) aBogus else bBogus
        next = split.second

        index = next.findPlayfairDouble()

    }

    return current + next
}

private fun String.padEndIfOdd(aBogus: Char, bBogus: Char): String {
    return if (length.isEven()) this else {
        this.padEnd(length + 1, if (last() != aBogus) aBogus else bBogus)
    }
}

private fun String.getCoordinates(digram: String): Array<Int> {
    val a = this.indexOf(digram.first())
    val ax = a % 5
    val ay = a / 5

    val b = this.indexOf(digram.last())
    val bx = b % 5
    val by = b / 5

    return arrayOf(ax, ay, bx, by)
}
private fun String.getCharAt(x: Int, y: Int): Char {
    return this[ (y * 5) + x ]
}

private fun String.diagonalOperation(ax: Int, ay: Int, bx: Int, by: Int): String {
    return "${this.getCharAt(bx, ay)}${this.getCharAt(ax, by)}"
}
private fun String.horizontalOperation(ax: Int, ay: Int, bx: Int, by: Int): String {
    return "${this.getCharAt(Math.floorMod(ax + 1, 5), ay)}${this.getCharAt(Math.floorMod(bx + 1, 5), by)}"
}
private fun String.verticalOperation(ax: Int, ay: Int, bx: Int, by: Int): String {
    return "${this.getCharAt(ax, Math.floorMod(ay + 1, 5))}${this.getCharAt(bx, Math.floorMod(by + 1, 5))}"
}

private fun encrypt(
    msg: String,
    key: String,
    toExclude: Char,
    replaceWith: Char
): String {

    // Check arguments for validity
    if (msg.isEmpty()) return ""
    if (key.isEmpty()) return msg.uppercase()

    // Cleaned arguments
    val clnAlphabet = Alphabet
        .uppercase()
        .filter { it != toExclude }
    val clnMsg = msg
        .uppercase()
        .replace(toExclude, replaceWith)
        .filter { it in clnAlphabet }
    val cleanKey = key.uppercase()
    val aBogus = if (toExclude == 'X') 'A' else 'X'
    val bBogus = if (aBogus == 'A') 'B' else 'A'
    val square = playfairSquare(cleanKey, clnAlphabet)

    /**
     * 1. Generate the message's digrams, following the rules on duplicate characters
     * 2. If the message length is odd, pad with an appropriate bogus character
     * 3. Split the message into the individual digrams
     * 4. Perform one of three different playfair operations on each digram
     * 5. Join to a single string
     */
    return clnMsg
        .genPlayfairDigrams(aBogus, bBogus)
        .padEndIfOdd(aBogus, bBogus)
        .chunked(2)
        .map { digram ->

            val (ax, ay, bx, by) = square.getCoordinates(digram)
            return@map if (ax != bx && ay != by) {
                square.diagonalOperation(ax, ay, bx, by)
            } else if (ax != bx) {
                square.horizontalOperation(ax, ay, bx, by)
            } else if (ay != by) {
                square.verticalOperation(ax, ay, bx, by)
            } else {
                " XXX "
            }

        }.joinToString("")

}

private fun decrypt(
    msg: String,
    key: String,
    toExclude: Char
): String {

    // Check arguments for validity
    if (msg.isEmpty()) return ""
    if (msg.length.isOdd()) return "Message should not be odd"
    if (key.isEmpty()) return msg.lowercase()

    // Cleaned arguments
    val clnAlphabet = Alphabet
        .uppercase()
        .filter { it != toExclude }
    val clnKey = key.uppercase()
    val square = playfairSquare(clnKey, clnAlphabet)

    /**
     * 1. Split the message into digrams
     * 2. Perform the reverse playfair operations on each digram
     * 3. Join to a single string
     * 4. Transform message to lowercase
     */
    return msg
        .chunked(2)
        .map { digram ->

            val (ax, ay, bx, by) = square.getCoordinates(digram)
            return@map if (ax != bx && ay != by) {
                square.diagonalOperation(ax, ay, bx, by)
            } else if (ax != bx) {
                square.horizontalOperation(ax - 2, ay, bx - 2, by)
            } else if (ay != by) {
                square.verticalOperation(ax, ay - 2, bx, by - 2)
            } else {
                " XXX "
            }

        }.joinToString("")
        .lowercase()

}

fun String.playfair(
    key: String,
    toExclude: Char = 'J',
    replaceWith: Char = 'G',
    decrypt: Boolean = false
): String = when (decrypt) {

    true  -> decrypt(this, key, toExclude)
    false -> encrypt(this, key, toExclude, replaceWith)

}
