package com.example.modularized_rickandmortyapp.di

import com.example.interactors.CharacterInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterInteractorsModule {

    @Provides
    @Singleton
    fun provideCharacterInteractors(): CharacterInteractors {
        return CharacterInteractors.build()
    }
}