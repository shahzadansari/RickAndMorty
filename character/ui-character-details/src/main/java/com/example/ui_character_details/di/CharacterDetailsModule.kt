package com.example.ui_character_details.di

import com.example.ui_character_details.ui.CharacterDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterDetailsModule = module {
    viewModel { CharacterDetailsViewModel(get(), get()) }
}