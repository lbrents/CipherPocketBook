package com.example.cipherpocketbook.ciphers

import com.example.cipherpocketbook.Alphabet
import com.example.cipherpocketbook.repeatToLength
import com.example.cipherpocketbook.safeShift

private fun encrypt(
    msg: String,
    key: String
): String {

    // Check arguments for validity
    if (msg.isEmpty()) return ""
    if (key.isEmpty()) return msg.uppercase()

    // Cleaned arguments
    val clnAlphabet = Alphabet.uppercase()
    val clnMsg = msg
        .uppercase()
        .filter { it in clnAlphabet }
    val clnKey = key
        .uppercase()
        .filter { it in clnAlphabet }
        .repeatToLength(clnMsg.length)

    // Caesar shift each character by the index in the alphabet of the character in the key with
    // the same message index
    return clnMsg
        .mapIndexed { index, char ->
            val keyIndex = clnAlphabet.indexOf(clnKey[index])
            val msgIndex = clnAlphabet.indexOf(char)
            return@mapIndexed clnAlphabet.safeShift(keyIndex, msgIndex)
        }.joinToString("")

}

private fun decrypt(
    msg: String,
    key: String
): String {

    // Check arguments for validity
    if (msg.isEmpty()) return ""
    if (key.isEmpty()) return msg.lowercase()

    // Cleaned arguments
    val clnAlphabet = Alphabet.lowercase()
    val clnMsg = msg
        .lowercase()
        .filter { it in clnAlphabet }
    val clnKey = key
        .lowercase()
        .filter { it in clnAlphabet }
        .repeatToLength(clnMsg.length)

    // Caesar shift each character by the index in the alphabet of the negative key index
    return clnMsg
        .mapIndexed { index, char ->
            val keyIndex = clnAlphabet.indexOf(clnKey[index])
            val msgIndex = clnAlphabet.indexOf(char)
            return@mapIndexed clnAlphabet.safeShift(msgIndex, -keyIndex)
        }.joinToString("")

}

fun String.vigenere(
    key: String,
    decrypt: Boolean = false
) = when (decrypt) {

    true  -> decrypt(this, key)
    false -> encrypt(this, key)

}