package com.example.ui_character_list.di

import com.example.ui_character_list.paging.GetCharactersPagingSource
import com.example.ui_character_list.ui.CharactersListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val charactersListModule = module {
    viewModel { CharactersListViewModel(getCharactersUsecase = get()) }

    factory {
        GetCharactersPagingSource(getCharactersUsecase = get())
    }
}