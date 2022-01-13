package com.zeemoog.marvelcompose.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zeemoog.marvelcompose.ui.navigation.NavItem
import com.zeemoog.marvelcompose.ui.navigation.navigatePoppingUpToStartDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// creamos un remember personalizado
// es necesario q sea @composable, para poder acceder a funciones composables
// "remember(..)" tiene argumentos los cuales, si alguno de ellos cambia el
//      MarvelAppState se vuelve a generar
@Composable
fun rememberMarvelAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): MarvelAppState = remember(scaffoldState, navController, coroutineScope) {
    MarvelAppState(scaffoldState, navController, coroutineScope)
}

/*
    Es un stateholder
 */

/**
 * ScaffoldState
 *  para gestionar el estado del menu (drawer), ya q el scaffold es quien lo controla con su estado
 *  tiene info del:
 *  --> drawerState
 *  --> snackbarHostState
 */

/**
 * CoroutineScope
 *  la funcion "open" del drawer necesita ejecutarse en contexto de corrutina
 */

class MarvelAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {
    // Constantes

    companion object {
        // declaramos q vamos a mostrar en el drawer
        val DRAWER_OPTIONS = listOf(NavItem.HOME, NavItem.SETTINGS)

        // declaramos q vamos a mostrar en el bottombar
        val BOTTOM_NAV_OPTIONS = listOf(NavItem.CHARACTERS, NavItem.COMICS, NavItem.EVENTS)
    }

    // Properties

    /** BackStackEntry
     * es el q permite acceder a la ruta actual
     * se usa para saber si tenemos q marcar un elemento
     * como seleccionado o no.
     * Y necesita de una funcion composable para poder usarse (@Composable get())**/

    /** si viene alguna ruta vacia, devolvemos cadena vacia **/
    val currentRoute: String
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""

    /** basicamente si estamos en pantalla ppal no muestra btn up, y si
    * esta en alguna de detalle lo muestra  **/
    val showUpNavigation: Boolean
        @Composable get() = currentRoute !in NavItem.values().map { it.navCommand.route }


    /** determinara cuando mostrar la bottombar (ya q en settings no deberia verse)
    * la funcion "any" retorna un boolean
    *  --> es true si alguno de elementos de la coleccion satisface al predicado (opciones del bottombar)
    *      es decir, si character, comics o events  **/
    val showBottomNavigation: Boolean
        @Composable get() = BOTTOM_NAV_OPTIONS.any { currentRoute.contains(it.navCommand.feature.route) }

    /** contiene indice del elemento seleccionado
    * si se ve el bottombar, entonces es character, comics o events
    * entonces asigna indice cero. Sino asigna indice correspondiente (1 o 2 o etc)
    * utilizado para saber cual pintar en el menu drawer  **/
    val drawerSelectedIndex: Int
        @Composable get() = if (showBottomNavigation) {
            DRAWER_OPTIONS.indexOf(NavItem.HOME)
        } else {
            DRAWER_OPTIONS.indexOfFirst { it.navCommand.route == currentRoute }
        }


    // Logica de UI

    fun onUpClick() {
        navController.popBackStack()
    }

    fun onMenuClick() {
        coroutineScope.launch { scaffoldState.drawerState.open() }
    }

    fun onNavItemClick(navItem: NavItem) {
        navController.navigatePoppingUpToStartDestination(navItem.navCommand.route)
    }

    fun onDrawerOptionClick(navItem: NavItem) {
        // antes de la navegacion de cada item seleccionado (cerrar menu)
        coroutineScope.launch { scaffoldState.drawerState.close() }
        // luego la navegacion
        onNavItemClick(navItem)
    }

}