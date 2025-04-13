package com.example.cipherpocketbook

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly

object Components {

    @Composable
    fun QuickIconButton(icon: ImageVector, onClick: () -> Unit) {
        IconButton(onClick) { Icon(icon, null) }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar(
        title: String,
        onBack: () -> Unit,
        onInfo: () -> Unit,
        backIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack
    ) {

        TopAppBar(
            title = { Text(title) },
            navigationIcon = { QuickIconButton(backIcon, onBack) },
            actions = { QuickIconButton(Icons.Default.Info, onInfo) }
        )

    }

    @Composable
    fun ScreenColumn(
        innerPadding: PaddingValues,
        content: @Composable ColumnScope.() -> Unit
    ) {
        Column(
            modifier = Modifier.Companion
                .padding(innerPadding)
        ) {

            HorizontalDivider()

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Companion.Start,
                modifier = Modifier.Companion
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                content()

            }

        }
    }

    @Composable
    fun InfoDialog(
        show: Boolean,
        onDismiss: () -> Unit,
        title: String,
        description: String
    ) {

        if (show) {

            Dialog(
                onDismissRequest = onDismiss
            ) {

                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Companion.CenterHorizontally,
                        modifier = Modifier.Companion
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {

                        Text(
                            text = title,
                            fontSize = 24.sp,
                            modifier = Modifier.Companion
                                .padding(bottom = 24.dp)
                        )
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline
                        )

                    }

                }

            }

        }

    }

    @Composable
    fun NumberTextField(
        label: String,
        value: String,
        onValueChange: (String) -> Unit,
        readOnly: Boolean = false
    ) {

        OutlinedTextField(
            value = value,
            onValueChange = { newString ->
                if (newString.isDigitsOnly() && newString.length < 8) {
                    onValueChange(newString)
                }
            },
            label = { Text(label) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Companion.Number),
            singleLine = true,
            readOnly = readOnly,
            modifier = Modifier.Companion
                .fillMaxWidth()
        )

    }

    @Composable
    fun QuickTextField(
        label: String,
        value: String,
        onValueChange: (String) -> Unit,
        readOnly: Boolean = false
    ) {

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            readOnly = readOnly,
            modifier = Modifier.Companion
                .fillMaxWidth()
        )

    }

    @Composable
    fun QuickCheckBox(
        label: String,
        state: Boolean,
        onStateChange: (Boolean) -> Unit
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Companion.CenterVertically,
            modifier = Modifier.Companion
                .fillMaxWidth()
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(4.dp))
                .clickable { onStateChange(!state) }
        ) {

            Text(label, modifier = Modifier.Companion.padding(start = 8.dp))
            Checkbox(
                checked = state,
                onCheckedChange = onStateChange
            )

        }

    }

    @Composable
    fun QuickCharField(
        label: String,
        char: Char?,
        onCharChange: (Char?) -> Unit
    ) {

        QuickTextField(
            label = label,
            value = char?.toString() ?: "",
            onValueChange = {
                onCharChange(if (it.isEmpty()) null else it.last())
            }
        )

    }

}