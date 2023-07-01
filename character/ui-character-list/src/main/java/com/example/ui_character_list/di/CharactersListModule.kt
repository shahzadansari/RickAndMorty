package com.example.ui_character_list.di

import com.example.interactors.CharacterInteractors
import com.example.interactors.GetCharacters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharactersListModule {

    @Provides
    @Singleton
    fun provideGetAllCharactersInteractor(interactors: CharacterInteractors): GetCharacters {
        return interactors.getCharacters
    }

}