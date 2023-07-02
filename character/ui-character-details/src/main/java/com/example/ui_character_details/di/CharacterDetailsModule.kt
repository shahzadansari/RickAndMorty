package com.example.ui_character_details.di

import com.example.character_interactors.CharacterInteractors
import com.example.character_interactors.GetCharacterFromCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterDetailsModule {

    @Provides
    @Singleton
    fun provideGetCharacterFromCacheInteractor(interactors: CharacterInteractors): GetCharacterFromCache {
        return interactors.getCharacterFromCache
    }

}