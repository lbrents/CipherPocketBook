package com.example.cipherpocketbook.screens

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
import com.example.cipherpocketbook.ciphers.caesar

@Composable
fun CaesarScreen(onBack: () -> Unit) {

    /* ----------------------------------------------------------------------------------------- */
    // State

    var showInfoDialog by remember { mutableStateOf(false) }

    var shift by remember { mutableStateOf("") }
    var plaintext by remember { mutableStateOf("") }
    var reverse by remember { mutableStateOf(false) }
    var ignoreWhitespace by remember { mutableStateOf(false) }
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
        plaintext.caesar(
            shift = if (shift.isEmpty()) 0 else shift.toInt(),
            alphabet = alphabet,
            ignoreWhitespace = ignoreWhitespace,
            decrypt = reverse
        )
    } }

    /* ----------------------------------------------------------------------------------------- */
    // Info

    Components.InfoDialog(
        show = showInfoDialog,
        onDismiss = { showInfoDialog = false },
        title = "Caesar Cipher",
        description = """
            The caesar cipher is a very simple substitution cipher named after Julius Caesar, who used it to encrypt messages between him and his military.
            
            Given an alphabet, the caesar cipher takes the index of each character in the message and finds its index in the alphabet. That index is then shifted by a specified amount either forward or backward in the alphabet.
        """.trimIndent()
    )

    /* ----------------------------------------------------------------------------------------- */
    // UI

    Scaffold(
        topBar = {
            Components.TopBar(
                title = "Caesar",
                onBack = onBack,
                onInfo = { showInfoDialog = true }
            )
        }
    ) { innerPadding ->

        Components.ScreenColumn(innerPadding) {

            Components.NumberTextField(
                label = "Shift",
                value = shift,
                onValueChange = { shift = it }
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
            Components.QuickCheckBox(
                label = "Ignore Whitespace",
                state = ignoreWhitespace,
                onStateChange = { ignoreWhitespace = it }
            )

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