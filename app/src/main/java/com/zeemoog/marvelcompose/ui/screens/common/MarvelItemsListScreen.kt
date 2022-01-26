package com.zeemoog.marvelcompose.ui.screens.common

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.zeemoog.marvelcompose.data.entities.MarvelItem
import com.zeemoog.marvelcompose.data.entities.Result
import kotlinx.coroutines.launch

//sin uso de Either para manejo de errores
/**
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun <T : MarvelItem> MarvelItemsListScreen(
    loading: Boolean = false,
    items: List<T>,
    onClick: (T) -> Unit
) {
    MarvelItemsList(
        loading = loading,
        items = items,
        onItemClick = onClick
    )
}   **/

// con uso de Either para manejo de errores

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun <T : MarvelItem> MarvelItemsListScreen(
    loading: Boolean = false,
    items: Result<List<T>>,
    onClick: (T) -> Unit
) {
    // si hay un error muestra esa pantalla sino muestra el listado
    // dentro del parentisis el "left" - caso excepcional o error
    // dentro del bloque de llaves el "right" - camino correcto
    items.fold( { ErrorMessage(it) } ) { marvelItems ->

        // inicialmente esta en nulo la parte inferior del  sheet
        var bottomSheetItem by remember { mutableStateOf<T?>(null) }

        // este val permite controlar cuando mostrar/ocultar el sheet
        //  --> con show() - hide()
        // inicialmente lo seteamos oculto
        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

        //permite crear un entorno de corrutina
        // asi implementar las funciones del bottomsheet
        val scope = rememberCoroutineScope()

        // manejo del back (manualmente)
        /**BackPressedHandler(sheetState.isVisible) {
            scope.launch { sheetState.hide() }
        }**/

        // manejo del back (funcion ya definida)
        BackHandler(sheetState.isVisible) {
            scope.launch { sheetState.hide() }
        }

        // permite añadir un bottomSheet en cualquier punto q necesitemos
        ModalBottomSheetLayout(
            sheetContent = {
                MarvelItemBottomPreview(
                    item = bottomSheetItem,
                    //si lo dejamos asi, al ir al detalle y luego volver
                    // se bloquea pantalla ppal con un bottomsheet de 1dp invisible
                    /**onGoToDetail = onClick**/
                    onGoToDetail = {
                        //creamos un contexto de corrutinas
                        // asi utilizamos la funcion ocultar del bottomsheet
                        scope.launch {
                            //gracias a las corrutinas, hasta q no oculte el sheet
                            // no realizara la navegacion
                            sheetState.hide()
                            onClick(it)
                        }
                    }
                )
            },
            sheetState = sheetState
        ) {
            MarvelItemsList(
                loading = loading,
                items = marvelItems,
                onItemClick = onClick,
                onItemMore = {
                    bottomSheetItem = it
                    //aqui tenemos la necesidad de un sideeffect
                    //xq necesitamos mostrar el bottomsheet y requiere
                    // un contexto de corrutinas
                    // el .show() es una funcion de suspension
                    scope.launch { sheetState.show() }
                }
            )
        }
    }
}

// Manejo de la navegacion BACK (manualmente)
/**@Composable
fun BackPressedHandler(enabled: Boolean, onBack: () -> Unit) {

    val lifecycleOwner = LocalLifecycleOwner.current
    //esto puede ser null, pero como en este punto no deberia serlo entonces
    // utilizamos el "requireNotNull" para decir q esa expresion no es null
    //  y si lo es, lanza una excepcion
    val backDispatcher =
        requireNotNull(LocalOnBackPressedDispatcherOwner.current).onBackPressedDispatcher

    //se agrega un callBack, el cual crea un objeto q implemente el pressedCallBack
    // PROBLEMA: siempre q se regenere esta parte del codigo, se va a crear un nuevo callBack
    //          y se va agregar al dispatcher lo cual puede generar un problema
    /**backDispatcher.addCallback(lifecycleOwner, object : OnBackPressedCallback(true) {
    override fun handleOnBackPressed() {
    scope.launch { sheetState.hide() }
    }
    })**/
    // SOLUCION: utilizar "launchedEffect"
    //          permite q su contenido se ejecute segun lo q le digamos mediante sus argumentos (keys)
    //          en este caso, queremos q si el "owner" como "dispatcher" cambian se genere un
    //          nuevo callBack
    // PROBLEMAS:
    //        - cuando esta composicion desaparesca y ya no necesitemos el "callBack"
    //          este aun seguira añadido en el "backDispatcher" x lo q es necesario quitarlo
    //        - siempre esta activo el dispatcher con el "true", x lo q el btn back queda inutilizado
    /**LaunchedEffect(lifecycleOwner, backDispatcher) {
    backDispatcher.addCallback(lifecycleOwner, object : OnBackPressedCallback(true) {
    override fun handleOnBackPressed() {
    scope.launch { sheetState.hide() }
    }
    })
    }**/

    // SOLUCION: utilizar "DisposableEffect"
    //          permite mediante la funcion "onDispose" eliminar el callBack del dispatcher

    //si el sheet esta visible, estara activo.
    //y si esta invisible, lo desactivamos para utilizar el btn back normalmente
    /**val enabled = sheetState.isVisible**/ //viene x argumento

    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                /**scope.launch { sheetState.hide() }**/ //se realiza afuera
                onBack()
            }
        }
    }
    //permite modificar el enable q se le pasa al "callBack" en el remember
    // se mete dentro de un SideEffect para asegurarnos q se va a llamar cuando termine la
    // recomposicion, asi evitar o q se ejecute varias veces o ninguna
    SideEffect {
        backCallback.isEnabled = enabled
    }

    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwner, backCallback)

        onDispose { backCallback.remove() }
    }
} **/


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun <T : MarvelItem> MarvelItemsList(
    loading: Boolean,
    items: List<T>,
    onItemClick: (T) -> Unit,
    onItemMore: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator()
        }

        if (items.isNotEmpty()) {
            LazyVerticalGrid(
                cells = GridCells.Adaptive(180.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(items) {
                    MarvelListItem(
                        marvelItem = it,
                        modifier = Modifier.clickable { onItemClick(it) },
                        onItemMore = onItemMore
                    )
                }
            }
        }
    }
}