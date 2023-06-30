package com.example.components

import androidx.compose.ui.graphics.Color
import com.example.domain.CharacterStatus

fun CharacterStatus.colorResource() =
    when (this) {
        CharacterStatus.Alive -> Color.Green
        CharacterStatus.Dead -> Color.Red
        CharacterStatus.Unknown -> Color.Blue
    }