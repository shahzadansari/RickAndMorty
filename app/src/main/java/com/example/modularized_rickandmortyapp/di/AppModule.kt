package com.example.modularized_rickandmortyapp.di

import com.example.character_interactors.CharacterInteractors
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.dsl.module

val appModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = CharacterInteractors.schema,
            context = get(),
            name = CharacterInteractors.dbName
        )
    }
}