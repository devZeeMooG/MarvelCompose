package com.zeemoog.marvelcompose.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

fun NavHostController.navigatePoppingUpToStartDestination(route: String) {
    navigate(route) {
        // navigate genera un dsl el cual permite opciones de navegacion
        // quita toda la navegacion anterior, hasta el punto q se defina en el argumento
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // evita q se re lanze la navegacion sobre la pantalla q estamos posicionamos
        launchSingleTop = true
        // restaura el estado almacenado en el popUpTo()
        restoreState = true
    }
}