package com.example.ui_character_details.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.components.colorResource
import com.example.modularized_rickandmortyapp.character.ui_character_details.R

@Composable
fun CharacterDetails(state: CharacterDetailsState) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        state.character?.let { character ->
            Column(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(character.imageUrl)
                        .placeholder(R.drawable.white_background)
                        .error(R.drawable.error_image)
                        .crossfade(true)
                        .build(),
                    contentDescription = character.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = character.name)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = character.status.name, color = character.status.colorResource())
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Species: ${character.species}")
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Origin: ${character.origin.name}")
                }
            }
        }
    }
}