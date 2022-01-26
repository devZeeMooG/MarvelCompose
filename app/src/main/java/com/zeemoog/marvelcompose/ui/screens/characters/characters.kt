package com.zeemoog.marvelcompose.ui.screens.characters

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.zeemoog.marvelcompose.data.entities.Character
import com.zeemoog.marvelcompose.ui.screens.common.MarvelItemDetailScreen
import com.zeemoog.marvelcompose.ui.screens.common.MarvelItemsListScreen
import androidx.lifecycle.viewmodel.compose.viewModel

// Sin utilizar VIEWMODEL
/**
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun CharactersScreen(onClick: (Character) -> Unit) {
    /*
    estado
     */
    var charactersState by remember() { mutableStateOf(emptyList<Character>()) }

    /*
    este se ejecuta cada vez q el argumento cambie
    como no queremos q se ejecute mas de una vez, usamos un valor
    q sabemos q no cambiara. x eso el unit
    tambien puede ser un true
     */
    LaunchedEffect(Unit) {
        charactersState = CharactersRepository.get()
    }

    MarvelItemsListScreen(
        items = charactersState,
        onClick = onClick
    )
}

@ExperimentalMaterialApi
@Composable
fun CharacterDetailScreen(characterId: Int) {
    var characterState by remember { mutableStateOf<Character?>(null) }
    LaunchedEffect(Unit) {
        characterState = CharactersRepository.find(characterId)
    }
    characterState?.let {
        MarvelItemDetailScreen(it)
    }
} **/

// usando VIEWMODEL

// manejando el ESTADO con STATE de JETPACK COMPOSE

//para acceder al viewmodel, lo agregamos como argumento
// es necesario agregar a mano a imports el viewmodel tambien
//  --> import androidx.lifecycle.viewmodel.compose.viewModel
/**
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun CharactersScreen(onClick: (Character) -> Unit, viewModel: CharactersViewModel = viewModel()) {
    // aqui se nota el "STATE HOSTING"
    //  --> "estados" viajan hacia abajo
    //  --> "eventos" hacia arriba
    MarvelItemsListScreen(
        loading = viewModel.state.loading,
        items = viewModel.state.items,
        onClick = onClick
    )
} **/

// manejando el ESTADO con STATE FLOW

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun CharactersScreen(onClick: (Character) -> Unit, viewModel: CharactersViewModel = viewModel()) {
    // aqui se nota el "STATE HOSTING"
    //  --> "estados" viajan hacia abajo
    //  --> "eventos" hacia arriba
    val state by viewModel.state.collectAsState()
    MarvelItemsListScreen(
        loading = state.loading,
        items = state.items,
        onClick = onClick
    )
}

@ExperimentalMaterialApi
@Composable
fun CharacterDetailScreen(viewModel: CharacterDetailViewModel = viewModel()) {
    // lo q estaba antes "esta en el codigo comentado arriba"
    // ya no es necesario xq ahora tenemos el "ui state" siempre
    val state by viewModel.state.collectAsState()
    MarvelItemDetailScreen(
        loading = state.loading,
        marvelItem = state.character
    )
}


