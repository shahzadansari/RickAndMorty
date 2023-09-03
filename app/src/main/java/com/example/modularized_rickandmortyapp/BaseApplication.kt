package com.example.modularized_rickandmortyapp

import android.app.Application
import com.example.character_interactors.di.dataModule
import com.example.character_interactors.di.interactorsModule
import com.example.modularized_rickandmortyapp.di.appModule
import com.example.ui_character_details.di.characterDetailsModule
import com.example.ui_character_list.di.charactersListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(
                appModule,
                charactersListModule,
                characterDetailsModule,
                interactorsModule,
                dataModule
            )
        }
    }
}