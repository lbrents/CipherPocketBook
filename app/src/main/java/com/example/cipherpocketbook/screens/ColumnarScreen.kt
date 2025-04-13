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
import com.example.cipherpocketbook.ciphers.columnarTransposition

@Composable
fun ColumnarScreen(onBack: () -> Unit) {

    /* ----------------------------------------------------------------------------------------- */
    // State

    var showInfoDialog by remember { mutableStateOf(false) }

    var key by remember { mutableStateOf("") }
    var plaintext by remember { mutableStateOf("") }
    var reverse by remember { mutableStateOf(false) }

    val ciphertext by remember { derivedStateOf {
        plaintext.columnarTransposition(
            key = key,
            decrypt = reverse
        )
    } }

    /* ----------------------------------------------------------------------------------------- */
    // Info

    Components.InfoDialog(
        show = showInfoDialog,
        onDismiss = { showInfoDialog = false },
        title = "Columnar Transposition",
        description = """
            The columnar transposition cipher uses a key to divide a message up into chunks the same length as said key. Once the message is divided up, each chunk of text is laid out next to each other from top to bottom with each column of text being associated with a character in the key. Each column of text is then sorted by the key alphabetically. Once this process is done, the final encrypted message is then read down each column.
        """.trimIndent()
    )

    /* ----------------------------------------------------------------------------------------- */
    // UI

    Scaffold(
        topBar = {
            Components.TopBar(
                title = "Columnar Transposition",
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