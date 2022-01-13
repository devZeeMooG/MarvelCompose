package com.zeemoog.marvelcompose.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.zeemoog.marvelcompose.R


/*
identifica los elementos de navegacion
q se van a pintar en la bottom navigation (de momento)
ahora tambien los del drawer (menu)
 */
enum class NavItem(
    val navCommand: NavCommand,
    val icon: ImageVector,
    @StringRes val title: Int,
) {
    HOME(NavCommand.ContentType(Feature.CHARACTERS), Icons.Default.Home, R.string.home),
    SETTINGS(NavCommand.ContentType(Feature.SETTINGS), Icons.Default.Settings, R.string.settings),

    CHARACTERS(NavCommand.ContentType(Feature.CHARACTERS), Icons.Default.Face, R.string.characters),
    COMICS(NavCommand.ContentType(Feature.COMICS), Icons.Default.Book, R.string.comics),
    EVENTS(NavCommand.ContentType(Feature.EVENTS), Icons.Default.Event, R.string.events)
}


/*
clase sellada
 */
sealed class NavCommand(
    internal val feature: Feature,
    internal val subRoute: String = "home",
    private val navArgs: List<NavArg> = emptyList()
) {
    /**object Characters: NavCommand("characters")**/
    class ContentType(feature: Feature): NavCommand(feature)

    /**object CharacterDetail: NavCommand("characterDetail", listOf(NavArg.ItemId)) {
        fun createRoute(itemId: Int) = "$baseRoute/$itemId"
    }**/
    class ContentTypeDetail(feature: Feature):
        NavCommand(feature, "detail", listOf(NavArg.ItemId)) {
        fun createRoute(itemId: Int) = "${feature.route}/$subRoute/$itemId"
    }

    /**val route = run {
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(argValues)
            .joinToString("/")
    }**/
    val route = run {
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(feature.route, subRoute)
            .plus(argValues)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

}

enum class NavArg(val key: String, val navType: NavType<*>) {
    ItemId("itemId", NavType.IntType)
}