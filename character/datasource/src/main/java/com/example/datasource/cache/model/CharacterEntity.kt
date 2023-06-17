package com.example.datasource.cache.model

import com.example.core.Empty
import com.example.datasource.cache.CharacterEntity
import com.example.domain.Character
import com.example.domain.CharacterStatus
import com.example.domain.Gender
import com.example.domain.Location

fun CharacterEntity.toCharacter() = Character(
    id = id.toInt(),
    name = name,
    status = CharacterStatus.values().find { it.status == status } ?: CharacterStatus.Unknown,
    species = species,
    type = type,
    gender = Gender.values().find { it.gender == gender } ?: Gender.Unknown,
    origin = Location(origin, String.Empty), // Only location names are being cached instead of Location objects
    location = Location(location, String.Empty),
    imageUrl = imageUrl
)