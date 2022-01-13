package com.zeemoog.marvelcompose.ui.screens.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.zeemoog.marvelcompose.data.entities.MarvelItem



@ExperimentalMaterialApi
@Composable
fun MarvelListItem(marvelItem: MarvelItem, modifier: Modifier = Modifier) {
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
        Box(
            modifier = Modifier.padding(8.dp, 16.dp)
        ) {
            Text(
                text = marvelItem.title,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2
            )
        }
    }
}