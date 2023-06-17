package com.example.datasource.cache

import com.example.datasource.cache.model.toCharacter
import com.example.domain.Character

class CharactersCacheImpl(
    charactersDb: CharactersDatabase
) : CharactersCache {

    private val queries = charactersDb.charactersDbQueries

    override suspend fun getAllCharacters(): List<Character> {
        return queries.getAllCharacters().executeAsList().map { it.toCharacter() }
    }
}