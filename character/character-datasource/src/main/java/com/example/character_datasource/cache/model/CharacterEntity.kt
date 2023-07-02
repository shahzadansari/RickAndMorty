package com.example.character_datasource.cache.model

import com.example.character_domain.Character
import com.example.character_domain.CharacterStatus
import com.example.character_domain.Gender
import com.example.character_domain.Location
import com.example.characterdatasource.cache.CharacterEntity
import com.example.core.Empty

fun CharacterEntity.toCharacter() = Character(
    id = id.toInt(),
    name = name,
    status = CharacterStatus.values().find { it.value == status } ?: CharacterStatus.Unknown,
    species = species,
    type = type,
    gender = Gender.values().find { it.gender == gender } ?: Gender.Unknown,
    origin = Location(origin, String.Empty), // Only location names are being cached instead of Location objects
    location = Location(location, String.Empty),
    imageUrl = imageUrl
)