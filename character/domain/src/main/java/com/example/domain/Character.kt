package com.example.domain

import com.example.core.Empty

data class Character(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String = String.Empty,
    val type: String = String.Empty,
    val gender: Gender,
    val origin: Location,
    val location: Location,
    val imageUrl: String
)

enum class CharacterStatus(val status: String) {
    Alive("Alive"),
    Dead("Dead"),
    Unknown("unknown");
}

enum class Gender(val gender: String) {
    Male("Male"),
    Female("Female"),
    Genderless("Genderless"),
    Unknown("unknown");
}

data class Location(
    val name: String,
    val url: String = String.Empty
)
