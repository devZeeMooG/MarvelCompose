package com.zeemoog.marvelcompose.ui.screens.events

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import com.zeemoog.marvelcompose.data.entities.Event
import com.zeemoog.marvelcompose.data.repositories.EventsRepository
import com.zeemoog.marvelcompose.ui.screens.common.MarvelItemDetailScreen
import com.zeemoog.marvelcompose.ui.screens.common.MarvelItemsListScreen
import androidx.lifecycle.viewmodel.compose.viewModel


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun EventsScreen(onClick: (Event) -> Unit, viewModel: EventsViewModel = viewModel()) {
    MarvelItemsListScreen(
        loading = viewModel.state.loading,
        items = viewModel.state.items,
        onClick = onClick
    )
}


@ExperimentalMaterialApi
@Composable
fun EventDetailScreen(viewModel: EventDetailViewModel = viewModel()) {
    MarvelItemDetailScreen(
        loading = viewModel.state.loading,
        marvelItem = viewModel.state.event
    )
}

