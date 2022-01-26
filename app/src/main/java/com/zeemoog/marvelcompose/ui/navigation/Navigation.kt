package com.zeemoog.marvelcompose.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.zeemoog.marvelcompose.ui.screens.characters.CharacterDetailScreen
import com.zeemoog.marvelcompose.ui.screens.characters.CharactersScreen
import com.zeemoog.marvelcompose.ui.screens.comics.ComicDetailScreen
import com.zeemoog.marvelcompose.ui.screens.comics.ComicsScreen
import com.zeemoog.marvelcompose.ui.screens.events.EventDetailScreen
import com.zeemoog.marvelcompose.ui.screens.events.EventsScreen

/**
 * Navegacion anidada o Grafo anidado de navegacion
 */

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Navigation(navController: NavHostController) {
   // val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        /**startDestination = NavCommand.Characters.route**/
        /** aca se carga la ruta de home
         * startDestination = NavCommand.ContentType(Feature.CHARACTERS).route**/
        /* ahora le decimos q carga la ruta de navegacion
        * para el uso del grafo de navegacion */
        startDestination = Feature.CHARACTERS.route
    ) {
        // creamos en grafo de navegacion
        charactersNav(navController)
        comicsNav(navController)
        eventsNav(navController)
        // creamos la navegacion de settings
        // no es necesario una grafo de nav muy complejo, ya q solo es una pantalla
        composable(NavCommand.ContentType(Feature.SETTINGS)) {
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Settings", style = MaterialTheme.typography.h3)
            }
        }
    }
}


@ExperimentalMaterialApi
@ExperimentalFoundationApi
fun NavGraphBuilder.charactersNav(
    navController: NavController
) {
    /**
     * este navigation es el q se encarga de crear el grafo de navegacion
     * 'route'--> ruta padre
     * 'startDestination' --> pantalla home
     */
    navigation(
        startDestination = NavCommand.ContentType(Feature.CHARACTERS).route,
        route = Feature.CHARACTERS.route
    ) {
        composable(NavCommand.ContentType(Feature.CHARACTERS)) {
            CharactersScreen(
                onClick = { character ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.CHARACTERS).createRoute(character.id)
                    )
                }
            )
        }
        composable(NavCommand.ContentTypeDetail(Feature.CHARACTERS)) {
            //val id = it.findArg<Int>(NavArg.ItemId) sin viewmodel
            CharacterDetailScreen() //x defecto tomara el viewmodel q necesita
        }
    }
}


@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.comicsNav(navController: NavController) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.COMICS).route,
        route = Feature.COMICS.route
    ) {
        composable(NavCommand.ContentType(Feature.COMICS)) {
            ComicsScreen(
                onClick = { comic ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.COMICS).createRoute(comic.id)
                    )
                }
            )
        }

        composable(NavCommand.ContentTypeDetail(Feature.COMICS)) {
            //val id = it.findArg<Int>(NavArg.ItemId)
            ComicDetailScreen()
        }
    }
}


@ExperimentalMaterialApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.eventsNav(navController: NavController) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.EVENTS).route,
        route = Feature.EVENTS.route
    ) {
        composable(NavCommand.ContentType(Feature.EVENTS)) {
            EventsScreen(
                onClick = { event ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.EVENTS).createRoute(event.id)
                    )
                }
            )
        }

        composable(NavCommand.ContentTypeDetail(Feature.EVENTS)) {
            //val id = it.findArg<Int>(NavArg.ItemId)
            EventDetailScreen()
        }
    }
}


private fun NavGraphBuilder.composable(
    navCommand: NavCommand,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = navCommand.route,
        arguments = navCommand.args
    ) {
        content(it)
    }
}

/**private inline fun <reified T> NavBackStackEntry.findArg(arg: NavArg): T {
    val value = arguments?.get(arg.key)
    requireNotNull(value)
    return value as T
}**/