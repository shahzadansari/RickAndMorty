package com.example.character_datasource.network.model

import com.example.character_domain.Character
import com.example.character_domain.CharacterStatus
import com.example.character_domain.Gender
import com.example.character_domain.Location
import com.example.core.Empty
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCharactersDto(
    val info: InfoDto,
    val results: List<CharacterDto>
)

@Serializable
data class InfoDto(
    val count: Int,
    val pages: Int,
    @SerialName("next") val nextPageUrl: String?,
    @SerialName("prev") val previousPageUrl: String?
)

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
    @SerialName("image") val imageUrl: String
)

@Serializable
data class LocationDto(
    val name: String,
    val url: String = String.Empty
)

fun CharacterDto.toCharacter() = Character(
    id = id,
    name = name,
    status = CharacterStatus.values().find { it.value == status } ?: CharacterStatus.Unknown,
    species = species,
    type = type,
    gender = Gender.values().find { it.gender == gender } ?: Gender.Unknown,
    origin = origin.toLocation(),
    location = location.toLocation(),
    imageUrl = imageUrl
)

fun LocationDto.toLocation() = Location(
    name = name,
    url = url
)