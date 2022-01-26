package com.zeemoog.marvelcompose.ui.screens.common

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ShareCompat
import com.zeemoog.marvelcompose.R
import com.zeemoog.marvelcompose.data.entities.MarvelItem
import com.zeemoog.marvelcompose.data.entities.Url
import com.zeemoog.marvelcompose.ui.navigation.AppBarIcon

@ExperimentalMaterialApi
@Composable
fun MarvelItemDetailScaffold(
    marvelItem: MarvelItem,
    content: @Composable (PaddingValues) -> Unit
) {
    //obtiene el contexto actual
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            //si hay urls muestra btn share
            if (marvelItem.urls.isNotEmpty()) {
                FloatingActionButton(
                    onClick = {
                        shareCharacter(
                            context,
                            marvelItem.title,
                            marvelItem.urls.first()
                        )
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = stringResource(id = R.string.share_character)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true, //habilita la adicion del button al bottombar
        bottomBar = {
            BottomAppBar(
                /**cutoutShape = CircleShape**/ //hace un corte redondeado en el bottombar para el floatingbutton
                cutoutShape = MaterialTheme.shapes.small //tomara la misma forma del floatingbtn
            ) {
                AppBarIcon(imageVector = Icons.Default.Menu, onClick = { /*TODO*/ })
                Spacer(modifier = Modifier.weight(1f))
                AppBarIcon(imageVector = Icons.Default.Favorite, onClick = { /*TODO*/ })
            }
        },
        content = content
    )
}

/*
permite el envio de datos atraves de diferentes medios
de los q dispone el dispositivo (automaticamente)
x ej whatsapp, gmail, etc etc
 */
fun shareCharacter(context: Context, name: String, url: Url) {
    /**val intent = ShareCompat
        .IntentBuilder(context)
        .setType("text/plain")
        .setSubject(character.name)
        .setText(character.urls.first().url)
        .intent
    context.startActivity(intent)**/
    /*
    si queremos usar funcionalidades de kotlin
    es equivalente a como esta arriba
    "also" funcion scope
     */
    ShareCompat
        .IntentBuilder(context)
        .setType("text/plain")
        .setSubject(name)
        .setText(url.destination)
        .intent
        .also(context::startActivity)
}
