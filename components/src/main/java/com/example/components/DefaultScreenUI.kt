package com.example.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.core.Queue
import com.example.core.UIComponent

@Composable
fun DefaultScreenUI(
    isLoading: Boolean,
    errorQueue: Queue<UIComponent> = Queue(mutableListOf()),
    onRemoveHeadFromQueue: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        content()
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if (errorQueue.isNotEmpty()) {
            errorQueue.peek()?.let { uiComponent ->
                if (uiComponent is UIComponent.Dialog) {
                    GenericDialog(
                        title = uiComponent.title,
                        description = uiComponent.description,
                        onDismiss = { onRemoveHeadFromQueue() }
                    )
                }
            }
        }
    }
}