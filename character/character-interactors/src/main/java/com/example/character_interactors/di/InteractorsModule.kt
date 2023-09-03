package com.example.character_interactors.di

import com.example.character_interactors.CharacterInteractors
import com.example.character_interactors.GetCharacterFromCache
import com.example.character_interactors.GetCharacters
import com.example.character_datasource.di.dataModule
import org.koin.dsl.module

val interactorsModule = module {
    factory {
        GetCharacters(get(), get())
    }
    factory {
        GetCharacterFromCache(get())
    }
    factory {
        CharacterInteractors(get(), get())
    }
}

val dataModule = dataModule