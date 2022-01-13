package com.zeemoog.marvelcompose.ui.screens.characters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zeemoog.marvelcompose.data.entities.Character
import com.zeemoog.marvelcompose.data.repositories.CharactersRepository
import com.zeemoog.marvelcompose.ui.navigation.NavArg
import kotlinx.coroutines.launch

class CharacterDetailViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    // utilizamos "SavedStateHandle" para poder acceder a todos los argumentos
    //      q este recibiendo el "NavBackStackEntry"
    //      de esta forma podemos recuperar el id de character
    // esto puede retornar un null, en ese caso le asignamos cero
    private val id = savedStateHandle.get<Int>(NavArg.ItemId.key) ?: 0

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(character = CharactersRepository.find(id))
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val character: Character? = null
    )

}