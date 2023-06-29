package com.example.modularized_rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.DataState
import com.example.interactors.GetAllCharacters
import com.example.modularized_rickandmortyapp.ui.theme.ModularizedRickAndMortyAppTheme
import com.example.ui_character_list.ui.CharactersList
import com.example.ui_character_list.ui.CharactersListState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getAllCharacters: GetAllCharacters

    private val state = mutableStateOf(CharactersListState())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect(Unit) {
                getAllCharacters.execute().collect { dataState ->
                    state.value = state.value.copy(isLoading = dataState is DataState.Loading)
                    if (dataState is DataState.Success) {
                        state.value = state.value.copy(characters = dataState.data)
                    }
                }
            }
            ModularizedRickAndMortyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CharactersList(state = state.value)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ModularizedRickAndMortyAppTheme {
        Greeting("Android")
    }
}