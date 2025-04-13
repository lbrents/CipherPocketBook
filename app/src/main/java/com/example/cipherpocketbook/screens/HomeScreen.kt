package com.example.cipherpocketbook.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cipherpocketbook.Ciphers
import com.example.cipherpocketbook.Components
import com.example.cipherpocketbook.NavScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onInfo: () -> Unit) {
    TopAppBar(
        title = { Text("Cipher Pocket Book") },
        actions = { Components.QuickIconButton(Icons.Default.Info, onInfo) }
    )
}

@Composable
private fun CipherEntryList(onClick: (NavScreen) -> Unit) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {

        items(
            items = Ciphers.entries,
            key = { entry -> entry.ordinal }
        ) { entry ->
            ListItem(
                headlineContent = { Text(entry.label) },
                supportingContent = { Text(entry.descriptor, style = MaterialTheme.typography.bodySmall) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(entry.screen) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(64.dp))
        }

    }

}

@Composable
fun HomeScreen(nav: NavHostController) {

    var showInfoDialog by remember { mutableStateOf(false) }

    Components.InfoDialog(
        show = showInfoDialog,
        onDismiss = { showInfoDialog = false },
        title = "Pocket Book",
        description = """
            Made for MTH 312, this app serves to provide quick access to encrypting/decrypting various ciphers.
                     
            Created by: Luke Brents
        """.trimIndent()
    )

    Scaffold(
        topBar = { TopBar { showInfoDialog = true } }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            HorizontalDivider()
            CipherEntryList { screen -> nav.navigate(screen) }

        }

    }

}