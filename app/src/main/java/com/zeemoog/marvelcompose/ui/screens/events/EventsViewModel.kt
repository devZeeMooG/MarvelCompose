package com.zeemoog.marvelcompose.ui.screens.events

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeemoog.marvelcompose.data.entities.Event
import com.zeemoog.marvelcompose.data.repositories.EventsRepository
import kotlinx.coroutines.launch

class EventsViewModel(): ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(items = EventsRepository.get())
        }
    }


    data class UiState(
        val loading: Boolean = false,
        val items: List<Event> = emptyList()
    )

}