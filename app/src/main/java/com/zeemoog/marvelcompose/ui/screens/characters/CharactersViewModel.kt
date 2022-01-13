package com.zeemoog.marvelcompose.ui.screens.characters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeemoog.marvelcompose.data.entities.Character
import com.zeemoog.marvelcompose.data.repositories.CharactersRepository
import kotlinx.coroutines.launch

// necesario q extienda de viewModel()
// para poder usar el scope, contexto de corrutina
// y asi lanzar funciones de suspension
class CharactersViewModel: ViewModel() {

    //se recomienda privado para q no sea modificado desde afuera
    //forma de ocultar q es un matableState
    // bloque "init"
    //  --> muestra el loading hasta q cargue la lista de characters
    /**private val _state = mutableStateOf(UiState())
    val state: State<UiState> get() = _state

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(items = CharactersRepository.get())
        }
    }  **/

    //vamos a resumir el codigo de arriba
    // - usamos delegado "by"
    // - ahora sera "var" para q se modifique directamente
    // - se importa "get" y "set"
    // - ya no usamos el ".value" ya q al asignar un valor al state
    //      automaticamente el seter llamara al .value
    //      pasa lo mismo para el geter
    // - como no queremos q el state se modifique desde afuera
    //      ponemos el "set" privao
    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(items = CharactersRepository.get())
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val items: List<Character> = emptyList()
    )

}