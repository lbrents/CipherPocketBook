package com.example.cipherpocketbook.ciphers

import com.example.cipherpocketbook.chunkIntoColumns
import com.example.cipherpocketbook.isMultipleOf

private fun String.sortFirstBySecond(mapper: (Int, Char) -> Pair<Int, Char>): String {
    return this
        .mapIndexed { index, char -> mapper(index, char) }
        .sortedBy { it.second }
        .map { it.first }
        .joinToString("")
}
private fun List<String>.sortFirstBySecond(mapper: (Int, String) -> Pair<String, Int>): List<String> {
    return this
        .mapIndexed { index, msg -> mapper(index, msg) }
        .sortedBy { it.second }
        .map { it.first }
}

private fun encrypt(
    msg: String,
    key: String,
    padChar: Char = ' '
): String {

    // Check arguments for validity
    if (msg.isEmpty()) return ""
    if (key.isEmpty()) return msg.uppercase()

    // Cleaned arguments
    val clnKey = key.uppercase()

    /**
     * 1. Transform original message to uppercase
     * 2. Chunk every nth character into their own columns
     * 3. Sort the columns into the order of the sorted key alphabetically
     * 4. Join the product into a single string
     */
    return msg
        .uppercase()
        .chunkIntoColumns(clnKey.length, padChar)
        .sortFirstBySecond { index, col -> col to clnKey[index].code }
        .joinToString("")

}

private fun decrypt(
    msg: String,
    key: String
): String {

    // Check arguments for validity
    if (msg.isEmpty()) return ""
    if (!msg.length.isMultipleOf(key.length)) return "Message length must be multiple of key length"
    if (key.isEmpty()) return msg.lowercase()

    // Calculate size that row should be
    val size = msg.length / key.length

    // Sort the characters in the key, return their sorted indices
    val sortedKeyIndices = key
        .uppercase()
        .sortFirstBySecond { index, char -> index to char }

    // Reverse the message into its columns by chunking it and the sorting it back
    val columns = msg
        .lowercase()
        .chunked(size)
        .sortFirstBySecond { index, col -> col to sortedKeyIndices[index].code }

    // Map each nth character to a chunk and then join it all together
    return (0..<size)
        .map { row -> columns.map { it[row] } }
        .joinToString("") { it.joinToString("") }

}

fun String.columnarTransposition(
    key: String,
    padChar: Char = ' ',
    decrypt: Boolean = false
): String = when (decrypt) {

    true  -> decrypt(this, key)
    false -> encrypt(this, key, padChar)

}