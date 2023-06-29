package com.example.modularized_rickandmortyapp.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String, val arguments: List<NamedNavArgument>) {

    object CharactersList : Screen(
        route = "charactersList",
        arguments = emptyList()
    )

    object CharacterDetails : Screen(
        route = "characterDetails",
        arguments = listOf(
            navArgument("characterId") {
                type = NavType.IntType
            }
        )
    )
}
