package com.zeemoog.marvelcompose.ui.screens.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.zeemoog.marvelcompose.R
import com.zeemoog.marvelcompose.data.entities.MarvelItem


// lo generalizamos para q funcione mejor con el resto de los componentes
// con esto se logra sacar la info afuera
@ExperimentalMaterialApi
@Composable
fun <T: MarvelItem> MarvelListItem(
    marvelItem: T,
    onItemMore: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Card {
            Image(
                painter = rememberImagePainter(data = marvelItem.thumbnail),
                contentDescription = marvelItem.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .aspectRatio(1f)
            )
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppBarOverflowMenu(urls = marvelItem.urls)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = marvelItem.title,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                modifier = Modifier
                    .padding(8.dp, 16.dp)
                    .weight(1f)
            )
            IconButton(onClick = { onItemMore(marvelItem) }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.more_actions)
                )
            }
        }
    }
}