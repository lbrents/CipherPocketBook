package com.example.cipherpocketbook.ciphers

import com.example.cipherpocketbook.Alphabet
import com.example.cipherpocketbook.safeShift

private fun encrypt(
    msg: String,
    shift: Int,
    alphabet: String,
    ignoreWhitespace: Boolean
): String {

    // Check arguments for validity
    if (msg.isEmpty()) return ""
    if (shift == 0) return msg.uppercase()
    if (alphabet.isEmpty()) return ""

    // Cleaned arguments
    val clnAlphabet = alphabet.uppercase() + if (ignoreWhitespace) " " else ""

    /**
     * 1. Transform original message uppercase
     * 2. Filter out all characters not found in the given alphabet
     * 3. Map each character to the character found at its shifted position
     * 4. Join the result to a single string
     */
    return msg
        .uppercase()
        .filter { it in clnAlphabet }
        .map { if (it == ' ' && ignoreWhitespace) it else clnAlphabet.safeShift(it, shift) }
        .joinToString("")

}

private fun decrypt(
    msg: String,
    shift: Int,
    alphabet: String,
    ignoreWhitespace: Boolean
): String {
    return encrypt(msg, -shift, alphabet, ignoreWhitespace)
        .lowercase()
}

fun String.caesar(
    shift: Int,
    alphabet: String = Alphabet.uppercase(),
    ignoreWhitespace: Boolean = true,
    decrypt: Boolean = false
): String = when (decrypt) {

    true  -> decrypt(this, shift, alphabet, ignoreWhitespace)
    false -> encrypt(this, shift, alphabet, ignoreWhitespace)

}
