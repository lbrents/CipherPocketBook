package com.example.cipherpocketbook.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.cipherpocketbook.ciphers.adfgvx

@Composable
fun ADFGVXScreen(onBack: () -> Unit) {

    /* ----------------------------------------------------------------------------------------- */
    // State

    var showInfoDialog by remember { mutableStateOf(false) }

    var key by remember { mutableStateOf("") }
    var plaintext by remember { mutableStateOf("") }
    var table by remember { mutableStateOf(Alphabet.createShuffledAlphanumeric()) }
    var headers by remember { mutableStateOf("ADFGVX") }
    var reverse by remember { mutableStateOf(false) }

    val ciphertext by remember { derivedStateOf {
        plaintext.adfgvx(
            key = key,
            translationTable = table,
            headers = headers,
            decrypt = reverse
        )
    } }

    /* ----------------------------------------------------------------------------------------- */
    // Info

    Components.InfoDialog(
        show = showInfoDialog,
        onDismiss = { showInfoDialog = false },
        title = "ADFGVX Cipher",
        description = """
            Serving as the successor to Germany's ADFGX cipher during WWI, the ADFGVX cipher is a combination of both a substitution and transposition cipher.
            
            A 6x6 table of randomly sorted alphanumeric characters is created with the headers for each row and column typically being ADFGVX. Each character in the plaintext is matched to its respective character in the table and then replaced with the characters making up the row and column headers. After this step, a columnar transposition is then applied, completing the cipher.
        """.trimIndent()
    )

    /* ----------------------------------------------------------------------------------------- */
    // UI

    Scaffold(
        topBar = {
            Components.TopBar(
                title = "ADFGVX",
                onBack = onBack,
                onInfo = { showInfoDialog = true },
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

            OutlinedTextField(
                value = table,
                onValueChange = { table = it.uppercase() },
                label = { Text("Translation Table") },
                trailingIcon = {
                    Components.QuickIconButton(Icons.Default.Refresh) {
                        table = Alphabet.createShuffledAlphanumeric()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Components.QuickTextField(
                label = "Headers",
                value = headers,
                onValueChange = { headers = it.uppercase() }
            )

        }

    }

}