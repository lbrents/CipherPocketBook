package com.example.cipherpocketbook.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cipherpocketbook.Alphabet
import com.example.cipherpocketbook.Components
import com.example.cipherpocketbook.ciphers.hill

@Composable
fun HillScreen(onBack: () -> Unit) {

    /* ----------------------------------------------------------------------------------------- */
    // State

    var showInfoDialog by remember { mutableStateOf(false) }

    var key by remember { mutableStateOf("") }
    var plaintext by remember { mutableStateOf("") }
    var reverse by remember { mutableStateOf(false) }
    var padChar by remember { mutableStateOf<Char?>(null) }
    var includeWhitespace by remember { mutableStateOf(false) }
    var includeNumbers by remember { mutableStateOf(false) }
    var includeSpecial by remember { mutableStateOf(false) }

    val alphabet by remember { derivedStateOf {
        Alphabet.custom(
            lowercase = false,
            includeWhitespace,
            includeNumbers,
            includeSpecial
        )
    } }
    val ciphertext by remember { derivedStateOf {
        plaintext.hill(
            key = key,
            alphabet = alphabet,
            padChar = padChar,
            decrypt = reverse
        )
    } }

    /* ----------------------------------------------------------------------------------------- */
    // Info

    Components.InfoDialog(
        show = showInfoDialog,
        onDismiss = { showInfoDialog = false },
        title = "Hill Cipher",
        description = """
            Invented by Lester Hill in 1929, the Hill cipher is a polygraphic cipher that features the use of linear algebra to encrypt a message.
            
            Each character in the given message is represented using a number modulo the length of the given alphabet. The message is split into vectors of n characters in length and then multiplies by the key which is an invertible n x n matrix modulo the length of the alphabet. Each number in the resulting matrix is then matched to its associated character.
        """.trimIndent()
    )

    /* ----------------------------------------------------------------------------------------- */
    // UI

    Scaffold(
        topBar = {
            Components.TopBar(
                title = "Hill",
                onBack = onBack,
                onInfo = { showInfoDialog = true }
            )
        }
    ) { innerPadding ->

        Components.ScreenColumn(innerPadding) {

            Components.QuickTextField(
                label = "Key",
                value = key,
                onValueChange = { key = it.uppercase() }
            )
            Components.QuickTextField(
                label = if (!reverse) "Plaintext" else "Ciphertext",
                value = plaintext,
                onValueChange = { plaintext = it }
            )
            Components.QuickTextField(
                label = if (reverse) "Plaintext" else "Ciphertext",
                value = ciphertext,
                onValueChange = { /* DO NOTHING HERE */ },
                readOnly = true
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Text("Options", style = MaterialTheme.typography.titleLarge)
            Components.QuickCheckBox(
                label = "Reverse",
                state = reverse,
                onStateChange = { reverse = it }
            )
            Components.QuickCharField(
                label = "Pad Character",
                char = padChar,
                onCharChange = { padChar = it }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text("Alphabet", style = MaterialTheme.typography.titleLarge)
            Components.QuickCheckBox(
                label = "Include Whitespace",
                state = includeWhitespace,
                onStateChange = { includeWhitespace = it }
            )
            Components.QuickCheckBox(
                label = "Include Numbers",
                state = includeNumbers,
                onStateChange = { includeNumbers = it }
            )
            Components.QuickCheckBox(
                label = "Include Special Characters",
                state = includeSpecial,
                onStateChange = { includeSpecial = it }
            )

        }

    }

}