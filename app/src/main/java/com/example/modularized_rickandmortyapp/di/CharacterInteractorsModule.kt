package com.example.modularized_rickandmortyapp.di

import android.app.Application
import com.example.character_interactors.CharacterInteractors
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
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
    fun provideAndroidDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = CharacterInteractors.schema,
            context = app,
            name = CharacterInteractors.dbName
        )
    }

    @Provides
    @Singleton
    fun provideCharacterInteractors(sqlDriver: SqlDriver): CharacterInteractors {
        return CharacterInteractors.build(sqlDriver)
    }
}