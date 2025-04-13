package com.example.cipherpocketbook

enum class Ciphers(
    val label: String,
    val screen: NavScreen,
    val descriptor: String
) {

    ADFGVX(
        label = "ADFGVX",
        screen = NavScreen.ADFGVXScreen,
        descriptor = "Substitution and transposition cipher"
    ),

    CAESAR(
        "Caesar",
        NavScreen.CaesarScreen,
        descriptor = "Substitution cipher"
    ),

    COLUMNAR_TRANSPOSITION(
        "Columnar",
        NavScreen.ColumnarScreen,
        descriptor = "Transposition cipher"
    ),

    HILL("Hill",
        NavScreen.HillScreen,
        descriptor = "Polygraphic substitution cipher"
    ),

    PLAYFAIR(
        "Playfair",
        NavScreen.PlayfairScreen,
        descriptor = "Digraph substitution cipher"
    ),

    VIGENERE(
        "Vigenere",
        NavScreen.VigenereScreen,
        descriptor = "Polyalphabetic shift cipher"
    )

}