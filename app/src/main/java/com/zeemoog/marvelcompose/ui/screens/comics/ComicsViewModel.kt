package com.zeemoog.marvelcompose.ui.screens.comics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.right
import com.zeemoog.marvelcompose.data.entities.Comic
import com.zeemoog.marvelcompose.data.entities.Result
import com.zeemoog.marvelcompose.data.repositories.ComicsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ComicsViewModel : ViewModel() {

    // para el uso de "STATE" de JETPACK COMPOSE
    //val state = Comic.Format.values().associate { it to mutableStateOf(UiState()) }

    // para el manejo de ESTADO de STATE FLOW
    val state = Comic.Format.values().associate { it to MutableStateFlow(UiState()) }

    fun formatRequested(format: Comic.Format) {
        val uiState = state.getValue(format)

        val comics = uiState.value.comics

        //si entra al if es xq ya se ha cargado el listado de formatos

        // de esta forma no carga ningun comic
        /**if (uiState.value.comics.isNotEmpty()) return

        viewModelScope.launch {
            uiState.value = UiState(loading = true)
            uiState.value = UiState(comics = ComicsRepository.get(format))
        } **/
        // queda cargando comics infinitamente
        /**if (comics is Either.Right && uiState.value.comics.isNotEmpty()) {
            viewModelScope.launch {
                uiState.value = UiState(loading = true)
                uiState.value = UiState(comics = ComicsRepository.get(format))
            }
        } **/

        // muestra y carga correctamente los comics
        if (comics is Either.Right && comics.value.isEmpty()) {
            viewModelScope.launch {
                uiState.value = UiState(loading = true)
                uiState.value = UiState(comics = ComicsRepository.get(format))
            }
        }
    }
    data class UiState(
        val loading: Boolean = false,
        val comics: Result<List<Comic>> = emptyList<Comic>().right()
    )
}