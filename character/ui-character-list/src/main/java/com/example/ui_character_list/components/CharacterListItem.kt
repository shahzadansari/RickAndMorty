package com.example.ui_character_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.domain.Character
import com.example.domain.CharacterStatus
import com.example.modularized_rickandmortyapp.character.ui_character_list.R

@Composable
fun CharacterListItem(
    character: Character,
    onCharacterSelected: (id: Int) -> Unit
) {
    Surface(
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCharacterSelected(character.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.imageUrl)
                    .placeholder(R.drawable.white_background)
                    .error(R.drawable.error_image)
                    .crossfade(true)
                    .build(),
                contentDescription = character.name,
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = character.name)
                    Text(text = character.status.name, color = character.status.colorResource())
                }

                Text(text = "Species: ${character.species}")
                Text(text = "Origin: ${character.origin.name}")
            }
        }
    }
}

fun CharacterStatus.colorResource() =
    when (this) {
        CharacterStatus.Alive -> Color.Green
        CharacterStatus.Dead -> Color.Red
        CharacterStatus.Unknown -> Color.Blue
    }