package com.zeemoog.marvelcompose.ui.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun AppBottomNavigation(
    bottomNavOptions: List<NavItem>,
    currentRoute: String,
    onNavItemClick: (NavItem) -> Unit
) {
    BottomNavigation {
        bottomNavOptions.forEach { item ->
            val title = stringResource(id = item.title)
            BottomNavigationItem(
                //si estamos en actividad ppal como en el detalle, ambas tendran seleccionado la seccion correspondiente (character - comics - events , etc)
                selected = currentRoute.contains(item.navCommand.feature.route),
                /** onClick = { navController.navigate(item.navCommand.route) }
                 * de esta forma volvemos sobre nuestras navegaciones
                 * no tan recomendado (aunq depende) ruta de navegacion muy larga
                 */
                /** onClick = { navController.navigate(item.navCommand.route) }
                 * de esta forma volvemos sobre nuestras navegaciones
                 * no tan recomendado (aunq depende) ruta de navegacion muy larga
                 */
                onClick = { onNavItemClick(item) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = title
                    )
                },
                /** label
                 * nombre debajo del icon
                 * no es obligatio, pero recomendado
                 * para saber q representan esos iconos
                 */
                /** label
                 * nombre debajo del icon
                 * no es obligatio, pero recomendado
                 * para saber q representan esos iconos
                 */
                label = { Text(text = title) }
            )
        }
    }
}