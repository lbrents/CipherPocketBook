package com.example.cipherpocketbook

import kotlinx.serialization.Serializable

sealed class NavScreen {

    @Serializable
    data object HomeScreen : NavScreen()

    @Serializable
    data object ADFGVXScreen : NavScreen()

    @Serializable
    data object CaesarScreen : NavScreen()

    @Serializable
    data object ColumnarScreen : NavScreen()

    @Serializable
    data object HillScreen : NavScreen()

    @Serializable
    data object PlayfairScreen : NavScreen()

    @Serializable
    data object VigenereScreen : NavScreen()

}