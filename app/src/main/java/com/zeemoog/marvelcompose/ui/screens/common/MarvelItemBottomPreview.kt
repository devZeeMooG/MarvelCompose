package com.zeemoog.marvelcompose.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.zeemoog.marvelcompose.data.entities.MarvelItem
import com.zeemoog.marvelcompose.R

@Composable
fun <T: MarvelItem> MarvelItemBottomPreview(item: T?, onGoToDetail: (T) -> Unit) {
    if (item != null) {
        Row(
            modifier = Modifier.padding(8.dp),
            //tambien puede ser un Spacer
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(item.thumbnail),
                contentDescription = item.title,
                modifier = Modifier
                    .width(96.dp)
                    // del ancho 1 calculara 1.5 de alto
                    .aspectRatio(1 / 1.5f)
                    .background(Color.LightGray)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = item.title, style = MaterialTheme.typography.h6)
                Text(text = item.description)
                Button(
                    onClick = { onGoToDetail(item) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = stringResource(id = R.string.go_to_detail))
                }
            }
        }
    } else {
        //este else es necesario xq si algun bottomSheet no tiene contenido
            // y no puede calcularse su altura, daria error
                // no se ve nada solo evita el error
        Spacer(modifier = Modifier.height(1.dp))
    }
}