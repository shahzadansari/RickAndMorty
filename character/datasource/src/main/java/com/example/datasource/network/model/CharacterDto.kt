package com.example.datasource.network.model

import com.example.core.Empty
import com.example.domain.Character
import com.example.domain.CharacterStatus
import com.example.domain.Gender
import com.example.domain.Location
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String = String.Empty,
    val type: String = String.Empty,
    val gender: String,
    val origin: LocationDto,
    val location: LocationDto,
    val imageUrl: String
)

@Serializable
data class LocationDto(
    val name: String,
    val url: String = String.Empty
)

fun CharacterDto.toCharacter() = Character(
    id = id,
    name = name,
    status = CharacterStatus.valueOf(status),
    species = species,
    type = type,
    gender = Gender.valueOf(gender),
    origin = origin.toLocation(),
    location = location.toLocation(),
    imageUrl = imageUrl
)

fun LocationDto.toLocation() = Location(
    name = name,
    url = url
)