package com.zeemoog.marvelcompose.ui

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.zeemoog.marvelcompose.R
import com.zeemoog.marvelcompose.ui.navigation.*

import com.zeemoog.marvelcompose.ui.theme.MarvelComposeTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zeemoog.marvelcompose.ui.screens.common.AppBarOverflowMenu
import kotlinx.coroutines.launch


@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MarvelApp() {

    val appState = rememberMarvelAppState()

    MarvelScreen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        if (appState.showUpNavigation) {
                            AppBarIcon(
                                imageVector = Icons.Default.ArrowBack,
                                onClick = { appState.onUpClick() })
                        } else {
                            AppBarIcon(
                                imageVector = Icons.Default.Menu,
                                onClick = { appState.onMenuClick() }
                            )
                        }
                    }
                )
            },
            bottomBar = {
                // no se vera cuando estes en settings
                if (appState.showBottomNavigation) {
                    AppBottomNavigation(
                        bottomNavOptions = MarvelAppState.BOTTOM_NAV_OPTIONS,
                        currentRoute = appState.currentRoute,
                        onNavItemClick = { appState.onNavItemClick(it) })
                }
            },
            drawerContent = {
                DrawerContent(
                    drawerOptions = MarvelAppState.DRAWER_OPTIONS,
                    selectedIndex = appState.drawerSelectedIndex,
                    onOptionClick = { appState.onDrawerOptionClick(it) }
                )
            },
            scaffoldState = appState.scaffoldState
        ) { padding ->
           Box(modifier = Modifier.padding(padding)) {
               Navigation(appState.navController)
           }
        }
        // modificamos el color de las status/navigation bar
        SetStatusBarColorEffect()
    }
}


@Composable
fun SetStatusBarColorEffect(
    color: Color = MaterialTheme.colors.primaryVariant,
    systemUiController: SystemUiController = rememberSystemUiController()
) {
    // como ahora vienen x argumento las sacamos de aca
    //con esta val podremos modificar las barras de estado
    /**val systemUiController = rememberSystemUiController()  **/
    //recuperamos el primaryVariant del "theme"
    /**val primaryVariant = MaterialTheme.colors.primaryVariant  **/

    //recomendado x las guias
    // x lo tanto cada vez q haya una recomposision del componente
    // se actualizen las barras de ui
    SideEffect {
        //modifica tanto la barra de status y de navigation
        systemUiController.setSystemBarsColor(color = color)

        //modifica la status bar
        /**systemUiController.setStatusBarColor()**/

        //modifica la navigation bar
        /**systemUiController.setNavigationBarColor()**/
    }
}


@Composable
fun MarvelScreen(content: @Composable () -> Unit) {
    MarvelComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}