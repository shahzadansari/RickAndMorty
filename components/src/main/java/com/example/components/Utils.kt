package com.example.components

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

fun Modifier.drawDebugBorder(color: Color = Color.Gray): Modifier = composed {
    border(2.dp, color = color, shape = RectangleShape)
}