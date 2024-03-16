package com.example.ui_character_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.character_domain.Character
import com.example.character_domain.example
import com.example.components.Previews
import com.example.components.isInPreview
import com.example.components.theme.ModularizedRickAndMortyAppTheme
import com.example.modularized_rickandmortyapp.components.R as componentsR

@Composable
fun CharacterListItem(
    character: Character,
    modifier: Modifier = Modifier,
    onCharacterSelected: (id: Int) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { onCharacterSelected(character.id) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!isInPreview) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(character.imageUrl)
                        .placeholder(componentsR.drawable.white_background)
                        .error(componentsR.drawable.error_image)
                        .crossfade(true)
                        .build(),
                    contentDescription = character.name,
                    modifier = Modifier.size(50.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = componentsR.drawable.character_avatar),
                    contentDescription = character.name,
                    modifier = Modifier.size(50.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = character.name,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(8.dp))

            Row(
                modifier = Modifier.padding(end = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End)
            ) {
                Tag(label = character.status.name)
                Tag(label = character.gender.name)
            }
        }
    }
}

@Composable
private fun Tag(label: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, style = MaterialTheme.typography.labelSmall)
    }
}

@Previews
@Composable
private fun CharacterListItemPreview() {
    ModularizedRickAndMortyAppTheme {
        CharacterListItem(character = Character.example, onCharacterSelected = {})
    }
}

@Preview
@Composable
private fun TagPreview() {
    ModularizedRickAndMortyAppTheme {
        Box(modifier = Modifier.padding(8.dp)) {
            Tag(label = "Alive", modifier = Modifier.align(Alignment.Center))
        }
    }
}