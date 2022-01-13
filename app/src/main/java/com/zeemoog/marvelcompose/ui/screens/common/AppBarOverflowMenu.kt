package com.zeemoog.marvelcompose.ui.screens.common

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalUriHandler
import com.zeemoog.marvelcompose.data.entities.Url

@ExperimentalMaterialApi
@Composable
fun AppBarOverflowMenu(urls: List<Url>) {

    //si la url es vacia el resto del codigo no se ejecutara
    if (urls.isEmpty()) return

    //definira si el menu se mostrara o no
    var showMenu by remember { mutableStateOf(false) }
    //permite la navegacion a urls
    val uriHandler = LocalUriHandler.current

    //aca actualizamos el valor del booleano
    IconButton(onClick = { showMenu = !showMenu }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More Actions"
        )
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            urls.forEach {
                //aca definimos q hace cada item, este caso cierra el menu
                DropdownMenuItem(onClick = {
                    uriHandler.openUri(it.destination)
                    showMenu = false
                }) {
                    ListItem(text = { Text(text = it.type) })
                }
            }
        }
    }
}