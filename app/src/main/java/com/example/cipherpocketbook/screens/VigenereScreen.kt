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
import com.example.cipherpocketbook.Components
import com.example.cipherpocketbook.ciphers.vigenere

@Composable
fun VigenereScreen(onBack: () -> Unit) {

    /* ----------------------------------------------------------------------------------------- */
    // State

    var showInfoDialog by remember { mutableStateOf(false) }

    var key by remember { mutableStateOf("") }
    var plaintext by remember { mutableStateOf("") }
    var reverse by remember { mutableStateOf(false) }

    val ciphertext by remember { derivedStateOf {
        plaintext.vigenere(
            key = key,
            decrypt = reverse
        )
    } }

    /* ----------------------------------------------------------------------------------------- */
    // Info

    Components.InfoDialog(
        show = showInfoDialog,
        onDismiss = { showInfoDialog = false },
        title = "Vigenere Cipher",
        description = """
            The vigenere cipher is a polyalphabetic substitution cipher named after Blaise de Vigenere despite not having been the first person to utilize it.
            
            The cipher works by applying a caesar shift on each character in the message, with the shift amount being determined by the index of the given key in the alphabet. If they key is not long enough to match the length of the message, then it is repeated.
        """.trimIndent()
    )

    /* ----------------------------------------------------------------------------------------- */
    // UI

    Scaffold(
        topBar = {
            Components.TopBar(
                title = "Vigenere Cipher",
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

        }

    }

}