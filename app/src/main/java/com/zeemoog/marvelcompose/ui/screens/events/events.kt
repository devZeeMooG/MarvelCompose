package com.zeemoog.marvelcompose.ui.screens.events

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import com.zeemoog.marvelcompose.data.entities.Event
import com.zeemoog.marvelcompose.ui.screens.common.MarvelItemDetailScreen
import com.zeemoog.marvelcompose.ui.screens.common.MarvelItemsListScreen
import androidx.lifecycle.viewmodel.compose.viewModel


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun EventsScreen(onClick: (Event) -> Unit, viewModel: EventsViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    MarvelItemsListScreen(
        loading = state.loading,
        items = state.items,
        onClick = onClick
    )
}


@ExperimentalMaterialApi
@Composable
fun EventDetailScreen(viewModel: EventDetailViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    MarvelItemDetailScreen(
        loading = state.loading,
        marvelItem = state.event
    )
}

