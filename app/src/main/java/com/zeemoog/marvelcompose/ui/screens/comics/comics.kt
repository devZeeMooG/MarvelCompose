package com.zeemoog.marvelcompose.ui.screens.comics

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.*
import com.zeemoog.marvelcompose.R
import com.zeemoog.marvelcompose.data.entities.Comic
import com.zeemoog.marvelcompose.ui.screens.common.ErrorMessage
import com.zeemoog.marvelcompose.ui.screens.common.MarvelItemDetailScreen
import com.zeemoog.marvelcompose.ui.screens.common.MarvelItemsList
import com.zeemoog.marvelcompose.ui.screens.common.MarvelItemsListScreen
import kotlinx.coroutines.launch

// sin uso de Either para manejo de errores
/**
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun ComicsScreen(onClick: (Comic) -> Unit, viewModel: ComicsViewModel = viewModel()) {

    // contiene el listado de formatos de un comic
    // por el momento tomamos solo 3
    // val formats = Comic.Format.values().take(3)

    // nos devuelve todos los formatos de comics
    val formats = Comic.Format.values().toList()

    // para saber q pestaña seleccionar cuando movemos el pager
    // necesitamos saber en q posicion esta el pager
    val pagerState = rememberPagerState()

    Column() {
        ComicFormatsTabRow(pagerState, formats)
        HorizontalPager(
            count = formats.size,
            state = pagerState
        ) { page ->
            //recuperamos posicion, dentro de listado de formatos q corresponde con la pagina
            val format = formats[page]

            //accion o evento q lanzamos al viewmodel
            viewModel.formatRequested(format)

            //recuperamos el estado de la paginas
            // para el manejo de ESTADO de JETPACK COMPOSE
            //val pageState by viewModel.state.getValue(format)

            // para el manejo de ESTADO de STATE FLOW
            val pageState = viewModel.state.getValue(format).value

            MarvelItemsList(
                loading = pageState.loading,
                items = pageState.comics,
                onItemClick = onClick
            )
        }
    }
}   **/

// usando Either para manejo de errores

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun ComicsScreen(onClick: (Comic) -> Unit, viewModel: ComicsViewModel = viewModel()) {

    // contiene el listado de formatos de un comic
    // por el momento tomamos solo 3
    // val formats = Comic.Format.values().take(3)

    // nos devuelve todos los formatos de comics
    val formats = Comic.Format.values().toList()

    // para saber q pestaña seleccionar cuando movemos el pager
    // necesitamos saber en q posicion esta el pager
    val pagerState = rememberPagerState()

    Column() {
        ComicFormatsTabRow(
            pagerState = pagerState,
            formats = formats
        )
        HorizontalPager(
            count = formats.size,
            state = pagerState
        ) { page ->
            //recuperamos posicion, dentro de listado de formatos q corresponde con la pagina
            val format = formats[page]

            //accion o evento q lanzamos al viewmodel
            viewModel.formatRequested(format)

            //recuperamos el estado de la paginas
            // para el manejo de ESTADO de JETPACK COMPOSE
            //val pageState by viewModel.state.getValue(format)

            // para el manejo de ESTADO de STATE FLOW
            val pageState by viewModel.state.getValue(format).collectAsState()

            //ya no es necesario xq llamamos a pantalla ppal
            // el cual, implementa el manejo de errores
            /**pageState.comics.fold( { ErrorMessage(it)} ) {
                MarvelItemsList(
                    loading = pageState.loading,
                    items = it,
                    onItemClick = onClick
                )
            } **/
            MarvelItemsListScreen(
                loading = pageState.loading,
                items = pageState.comics,
                onClick = onClick
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
private fun ComicFormatsTabRow(
    pagerState: PagerState,
    formats: List<Comic.Format>
) {
    // necesario para q el composable "pagerState.animateScrollToPage(it.ordinal)"
    // se ejecute en contexto de corrutina
    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        //quita el espacio vacio al inicio de pestañas
        edgePadding = 0.dp,
        //animacion de reflejar el desplazamiento de pestaña sobre el idicador de pestaña
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        //por cada formato una pestaña
        formats.forEach {
            Tab(
                selected = it.ordinal == pagerState.currentPage,
                onClick = { scope.launch { pagerState.animateScrollToPage(it.ordinal) } },
                //muestra los nombres como vienen del repo (no muy vistoso, xq vienen con "_")
                //text = { Text(text = it.name) }
                // x eso, hacemos una conversion de los nombres
                text = { Text(text = stringResource(id = it.toStringRes()).uppercase()) }
            )
        }
    }
}


@StringRes
private fun Comic.Format.toStringRes(): Int = when (this) {
    Comic.Format.COMIC -> R.string.comic
    Comic.Format.MAGAZINE -> R.string.magazine
    Comic.Format.TRADE_PAPERBACK -> R.string.trade_paperback
    Comic.Format.HARDCOVER -> R.string.hardcover
    Comic.Format.DIGEST -> R.string.digest
    Comic.Format.GRAPHIC_NOVEL -> R.string.graphic_novel
    Comic.Format.DIGITAL_COMIC -> R.string.digital_comic
    Comic.Format.INFINITE_COMIC -> R.string.infinite_comic
}

// sin uso de Either para manejo de errores
/**
@ExperimentalMaterialApi
@Composable
fun ComicDetailScreen(viewModel: ComicDetailViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    MarvelItemDetailScreen(
        loading = state.loading,
        item = state.comic
    )
}   **/

// usando Either para manejo de errores

@ExperimentalMaterialApi
@Composable
fun ComicDetailScreen(viewModel: ComicDetailViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    MarvelItemDetailScreen(
        loading = state.loading,
        marvelItem = state.comic
    )
}