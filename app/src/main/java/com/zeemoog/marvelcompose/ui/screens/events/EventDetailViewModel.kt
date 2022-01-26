package com.zeemoog.marvelcompose.ui.screens.events

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.zeemoog.marvelcompose.data.entities.Event
import com.zeemoog.marvelcompose.data.entities.Result
import com.zeemoog.marvelcompose.data.repositories.EventsRepository
import com.zeemoog.marvelcompose.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventDetailViewModel(savedStateHandle: SavedStateHandle): ViewModel() {

    private val id = savedStateHandle.get<Int>(NavArg.ItemId.key) ?: 0

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(event = EventsRepository.find(id))
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val event: Result<Event?> = Either.Right(null)
    )

}