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
import com.example.cipherpocketbook.Components
import com.example.cipherpocketbook.ciphers.playfair

@Composable
fun PlayfairScreen(onBack: () -> Unit) {

    /* ----------------------------------------------------------------------------------------- */
    // State

    var showInfoDialog by remember { mutableStateOf(false) }

    var key by remember { mutableStateOf("") }
    var plaintext by remember { mutableStateOf("") }
    var reverse by remember { mutableStateOf(false) }
    var toExclude by remember { mutableStateOf<Char>('J') }
    var replaceWith by remember { mutableStateOf<Char>('G') }

    val ciphertext by remember { derivedStateOf {
        plaintext.playfair(
            key = key,
            toExclude = toExclude,
            replaceWith = replaceWith,
            decrypt = reverse
        )
    } }

    /* ----------------------------------------------------------------------------------------- */
    // Info

    Components.InfoDialog(
        show = showInfoDialog,
        onDismiss = { showInfoDialog = false },
        title = "Playfair Cipher",
        description = """
            The playfair cipher is a substitution cipher invented by Charles Wheatstone in 1854 that utilizes splitting up a message into digraphs and performing operations on each one.
            
            Given a key, each repeated character is removed and then a suffix of every unused character in the alphabet is added on. One character is selected to be removed so that 25 characters remain and can be divided into a 5x5 table. The message is split into digraphs (chunks of 2 characters each), with each digraph with repeated characters having a bogus character placed between them. Lastly, an operation involving the 5x5 table is performed on each digram where each character is swapped with another one.
        """.trimIndent()
    )

    /* ----------------------------------------------------------------------------------------- */
    // UI

    Scaffold(
        topBar = {
            Components.TopBar(
                title = "Playfair",
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
                label = "To Exclude",
                char = toExclude,
                onCharChange = { toExclude = it ?: 'J' }
            )
            Components.QuickCharField(
                label = "Replace With",
                char = replaceWith,
                onCharChange = { replaceWith = it ?: 'G' }
            )
            Spacer(modifier = Modifier.height(8.dp))

        }

    }

}