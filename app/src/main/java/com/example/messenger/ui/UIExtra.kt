package com.example.messenger.ui

import android.annotation.SuppressLint
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ConfirmDialog(onDismiss: () -> Unit, onDelete: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(
                text = "Delete",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(onClick = onDelete) {
                Text(text = "Delete")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessengerAppBar(
    label: String,
    canNavigateBack: Boolean,
    action: @Composable () -> Unit = {},
    extraAction: @Composable () -> Unit = {},
    onNavigateUp: @Composable () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = label) },
        navigationIcon = {
            if (canNavigateBack) {
                onNavigateUp()
            }
        },
        scrollBehavior = scrollBehavior,
        actions = {
            action()
            extraAction()
        },
        modifier = modifier
    )
}