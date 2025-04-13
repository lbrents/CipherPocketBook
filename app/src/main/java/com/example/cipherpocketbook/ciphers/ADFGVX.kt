package com.example.cipherpocketbook.ciphers

private fun encrypt(
    msg: String,
    key: String,
    translationTable: String,
    headers: String
): String {

    // Check arguments for validity
    if (msg.isEmpty()) return ""
    if (key.isEmpty()) return msg.uppercase()
    if (translationTable.length != 36) return "Translation table must be a combination of all letters and numbers"
    if (headers.length != 6) return "Headers must be 6 characters long"

    // Cleaned arguments
    val clnTable = translationTable
        .uppercase()

    // Map each character in the table to its associated header digram
    val digramMap = clnTable
        .uppercase()
        .mapIndexed { index, char -> char to "${ headers[index / 6] }${ headers[index % 6] }" }
        .toMap()

    /**
     * 1. Transform original message to uppercase
     * 2. Filter out all characters not found in the translation table
     * 3. Map each character to its associated header digram
     * 4. Join to a single string
     * 5. Perform a columnar transposition encryption
     */
    return msg
        .uppercase()
        .filter { it in clnTable }
        .map { char -> digramMap[char] }
        .joinToString("")
        .columnarTransposition(key)

}

private fun decrypt(
    msg: String,
    key: String,
    translationTable: String,
    headers: String
): String {

    // Check arguments for validity
    if (msg.isEmpty()) return ""
    if (key.isEmpty()) return msg.lowercase()
    if (translationTable.length != 36) return "Translation table must be a combination of all letters and numbers"
    if (headers.length != 6) return "Headers must be 6 characters long"

    // Cleaned arguments
    val clnTable = translationTable
        .uppercase()
    val clnHeaders = headers
        .lowercase()

    // Map each character in the table to its associated header digram
    val digramMap = clnTable
        .uppercase()
        .mapIndexed { index, char -> "${ clnHeaders[index / 6] }${ clnHeaders[index % 6] }" to char }
        .toMap()

    /**
     * 1. Perform a columnar transposition decryption on the original message
     * 2. Chunk into digrams to match the original characters associated headers
     * 3. Match each digram to its associated original character
     * 4. Join the list of characters to a string
     * 5. Transform string to lowercase
     */
    return msg
        .columnarTransposition(key, decrypt = true)
        .chunked(2)
        .map { digram -> digramMap[digram] }
        .joinToString("") { it?.toString() ?: "" }
        .lowercase()

}

fun String.adfgvx(
    key: String,
    translationTable: String,
    headers: String = "ADFGVX",
    decrypt: Boolean = false
): String = when (decrypt) {

    true  -> decrypt(this, key, translationTable, headers)
    false -> encrypt(this, key, translationTable, headers)

}