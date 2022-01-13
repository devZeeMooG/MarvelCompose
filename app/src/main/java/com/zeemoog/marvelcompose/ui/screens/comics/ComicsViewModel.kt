package com.zeemoog.marvelcompose.ui.screens.comics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeemoog.marvelcompose.data.entities.Comic
import com.zeemoog.marvelcompose.data.repositories.ComicsRepository
import kotlinx.coroutines.launch

class ComicsViewModel: ViewModel() {

    //var state by mutableStateOf(UiState())
    //    private set
    val state = Comic.Format.values().associate { it to mutableStateOf(UiState()) }

    //init {
    //    viewModelScope.launch {
    //        state = UiState(loading = true)
    //        state = UiState(items = ComicsRepository.get())
    //    }
    //}

    data class UiState(
        val loading: Boolean = false,
        val items: List<Comic> = emptyList()
    )

    fun formatRequested(format: Comic.Format) {
        val uiState = state.getValue(format)
        //si entra al if es xq ya se ha cargado el listado de formatos
        if (uiState.value.items.isNotEmpty()) return

        viewModelScope.launch {
            uiState.value = UiState(loading = true)
            uiState.value = UiState(items = ComicsRepository.get(format))
        }
    }

}