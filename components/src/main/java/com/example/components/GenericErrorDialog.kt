package com.example.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties

@Composable
fun GenericErrorDialog(title: String, modifier: Modifier = Modifier, description: String?, onRetry: (() -> Unit)? = null, onDismiss: () -> Unit) {
    AlertDialog(
        modifier = modifier,
        properties = DialogProperties(dismissOnClickOutside = false),
        title = {
            Text(title)
        },
        text = {
            description?.let { description ->
                Text(text = description)
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onRetry?.invoke() },
                content = {
                    Text("Retry".uppercase())
                }
            )
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() },
                content = {
                    Text("Cancel".uppercase())
                }
            )
        },
        onDismissRequest = { onDismiss() }
    )
}

@Preview
@Composable
fun PreviewGenericDialog() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        GenericErrorDialog(title = "Title", description = "Description", onDismiss = {})
    }
}