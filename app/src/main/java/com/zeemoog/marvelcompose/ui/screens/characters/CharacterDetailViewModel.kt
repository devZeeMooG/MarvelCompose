package com.zeemoog.marvelcompose.ui.screens.characters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.Either
import arrow.core.right
import com.zeemoog.marvelcompose.data.entities.Character
import com.zeemoog.marvelcompose.data.entities.Result
import com.zeemoog.marvelcompose.data.repositories.CharactersRepository
import com.zeemoog.marvelcompose.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterDetailViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    // utilizamos "SavedStateHandle" para poder acceder a todos los argumentos
    //      q este recibiendo el "NavBackStackEntry"
    //      de esta forma podemos recuperar el id de character
    // esto puede retornar un null, en ese caso le asignamos cero
    private val id = savedStateHandle.get<Int>(NavArg.ItemId.key) ?: 0

    private val _state = MutableStateFlow(UiState())
    //val state: StateFlow<UiState> = _state.asStateFlow()
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(character = CharactersRepository.find(id))
        }
    }

    // sin usar Either para manejo de errores
    /** data class UiState(
        val loading: Boolean = false,
        val character: Character? = null
    )  **/

    // usando Either para manejo de errores
    data class UiState(
        val loading: Boolean = false,
        val character: Result<Character?> = Either.Right(null)
    )

}