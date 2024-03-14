package com.example.components

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp

@Stable
val isInPreview @Composable get() = LocalInspectionMode.current

inline fun Modifier.mapIf(predicate: Boolean, block: Modifier.() -> Modifier): Modifier {
    return if (predicate) block() else this
}

@SuppressLint("ComposeComposableModifier, ComposeModifierWithoutDefault")
@Composable
fun Modifier.drawPreviewBorder(color: Color = Color.White): Modifier {
    return this then Modifier.mapIf(isInPreview) {
        border(width = 1.dp, color = color, shape = RoundedCornerShape(8.dp))
    }
}

val String.Companion.Empty inline get() = ""