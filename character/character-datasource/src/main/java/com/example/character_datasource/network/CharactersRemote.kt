package com.example.character_datasource.network

import com.example.character_domain.Character
import com.example.core.DataState

interface CharactersRemote {
    suspend fun getCharacters(page: Int): DataState<List<Character>>
}