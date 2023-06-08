package com.example.domain

data class Character(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
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
    val url: String
)
