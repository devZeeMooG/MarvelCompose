package com.zeemoog.marvelcompose.ui.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zeemoog.marvelcompose.ui.MarvelScreen

@Composable
fun DrawerContent(
    drawerOptions: List<NavItem>,
    // se utilizara para dejar pintado el elemento seleccionado del menu
    selectedIndex: Int,
    // para saber q opcion se clickea y hacer algo con ello (navegar)
    // pero el drawer content no necesita saberlo
    onOptionClick: (NavItem) -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colors.primaryVariant,
                        MaterialTheme.colors.secondary
                    )
                )
            )
            .height(200.dp)
            .fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
    drawerOptions.forEachIndexed { index, navItem ->
        // guarda el indice del elemento
        val selected = selectedIndex == index
        // para evitar repetir codigo
        val colors = MaterialTheme.colors

        // definimos los local (de los elementos) q queremos modificar
        // para utilizarlos luego de manera implicita y asi evitar repiticion de codigo

        val localContentColor = if (selected) colors.primary else colors.onBackground

        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            // es necesario crear val localContenColor xq el color depende de un estado
            LocalContentColor provides localContentColor
        ) {
            Row(
                modifier = Modifier
                    .clickable { onOptionClick(navItem) }
                    .fillMaxWidth()
                    .padding(8.dp, 4.dp)
                    .clip(RoundedCornerShape(4.dp))
                    //.background(color = if (selected) colors.primary.copy(alpha = 0.12f) else colors.surface)
                    .background(color = if (selected) LocalContentColor.current.copy(alpha = 0.12f) else colors.surface)
                    .padding(12.dp)
            ) {
                // para evitar la perdida del seteo de medium se crea este otro Composition
                // donde se especifica el ContentAlpha.medium
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.medium
                ) {
                    Icon(
                        imageVector = navItem.icon,
                        // ya no es necesario xq esta en el LocalContentColor
                        // pero se pierde el ContentAlpha.medium
                        /**tint = if (selected) colors.primary else colors.onSurface.copy(
                        alpha = ContentAlpha.medium
                        ), **/
                        contentDescription = navItem.name
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = stringResource(id = navItem.title)
                    // ya no es necesario xq esta en el LocalContentColor
                    /**color = if (selected) colors.primary else colors.onSurface,**/

                    // ya no es necesario especificar el stilo xq toma x defecto
                    // toma lo especificado en el LocalTextStyle
                    /**style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold) **/
                )
            }
        }
    }
}

/**
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DrawerContentPreview() {
    MarvelScreen {
        Column {
            DrawerContent(
                drawerOptions = listOf(NavItem.HOME, NavItem.SETTINGS),
                selectedIndex = 0,
                onOptionClick = {}
            )
        }
    }
} **/