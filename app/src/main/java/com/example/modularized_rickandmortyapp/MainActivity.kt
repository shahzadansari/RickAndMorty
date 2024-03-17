package com.example.modularized_rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.components.theme.ModularizedRickAndMortyAppTheme
import com.example.modularized_rickandmortyapp.ui.navigation.Screen
import com.example.ui_character_details.ui.CharacterDetailsScreen
import com.example.ui_character_details.ui.CharacterDetailsViewModel
import com.example.ui_character_list.components.CharacterListItem
import com.example.ui_character_list.ui.CharactersListScreen
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
//        CharactersListScreen(
//            state = viewModel.state,
//            navigateToDetailScreen = { characterId ->
//                navController.navigate("${Screen.CharacterDetails.route}/$characterId") {
//                    launchSingleTop = true
//                }
//            },
//            onTriggerEvent = {
//                viewModel.onTriggerEvent(it)
//            }
//        )

        val charactersPagingItems = viewModel.charactersPagingData.collectAsLazyPagingItems()
        LazyColumn {
            items(charactersPagingItems.itemSnapshotList.items) {
                CharacterListItem(character = it, onCharacterSelected = {})
            }
        }
    }
}

fun NavGraphBuilder.characterDetailsScreen() {
    composable(
        route = Screen.CharacterDetails.route + "/{characterId}",
        arguments = Screen.CharacterDetails.arguments
    ) {
        val viewModel: CharacterDetailsViewModel = getViewModel()
        CharacterDetailsScreen(
            state = viewModel.state,
            onTriggerEvent = {
                viewModel.onTriggerEvent(it)
            }
        )
    }
}