package com.example.modularized_rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.components.theme.ModularizedRickAndMortyAppTheme
import com.example.ui_character_details.ui.CharacterDetailsScreen
import com.example.ui_character_details.ui.CharacterDetailsViewModel
import com.example.ui_character_list.ui.CharactersListScreen
import com.example.ui_character_list.ui.CharactersListViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModularizedRickAndMortyAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.CharactersListScreen
                ) {
                    charactersListScreen(
                        onCharacterSelected = { characterId ->
                            navController.navigate(Route.CharacterDetailsScreen(characterId)) {
                                launchSingleTop = true
                            }
                        }
                    )
                    characterDetailsScreen()
                }
            }
        }
    }
}

@Serializable
sealed interface Route {

    @Serializable
    data object CharactersListScreen : Route

    @Serializable
    data class CharacterDetailsScreen(val characterId: Int) : Route
}

fun NavGraphBuilder.charactersListScreen(onCharacterSelected: (characterId: Int) -> Unit) {
    composable<Route.CharactersListScreen> {
        val viewModel: CharactersListViewModel = getViewModel()
        CharactersListScreen(
            state = viewModel.state,
            navigateToDetailScreen = { characterId ->
                onCharacterSelected(characterId)
            },
            onTriggerEvent = {
                viewModel.onTriggerEvent(it)
            }
        )
    }
}

fun NavGraphBuilder.characterDetailsScreen() {
    composable<Route.CharacterDetailsScreen> {
        val viewModel: CharacterDetailsViewModel = getViewModel()
        CharacterDetailsScreen(
            state = viewModel.state,
            onTriggerEvent = {
                viewModel.onTriggerEvent(it)
            }
        )
    }
}