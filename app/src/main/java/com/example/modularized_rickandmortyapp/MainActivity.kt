package com.example.modularized_rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.components.theme.ModularizedRickAndMortyAppTheme
import com.example.modularized_rickandmortyapp.ui.navigation.Screen
import com.example.ui_character_details.ui.CharacterDetails
import com.example.ui_character_details.ui.CharacterDetailsViewModel
import com.example.ui_character_list.ui.CharactersList
import com.example.ui_character_list.ui.CharactersListViewModel
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModularizedRickAndMortyAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.CharactersList.route
                ) {
                    charactersListScreen(navController)
                    characterDetailsScreen()
                }
            }
        }
    }
}

fun NavGraphBuilder.charactersListScreen(navController: NavController) {
    composable(
        route = Screen.CharactersList.route
    ) {
        val viewModel: CharactersListViewModel = getViewModel()
        CharactersList(
            state = viewModel.state,
            navigateToDetailScreen = { characterId ->
                navController.navigate("${Screen.CharacterDetails.route}/$characterId")
            },
            onTriggerEvent = {
                viewModel.onTriggerEvent(it)
            }
        )
    }
}

fun NavGraphBuilder.characterDetailsScreen() {
    composable(
        route = Screen.CharacterDetails.route + "/{characterId}",
        arguments = Screen.CharacterDetails.arguments
    ) {
        val viewModel: CharacterDetailsViewModel = getViewModel()
        CharacterDetails(
            state = viewModel.state,
            onTriggerEvent = {
                viewModel.onTriggerEvent(it)
            }
        )
    }
}