package com.zeemoog.marvelcompose.ui.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    // Formas redondeadas
    /**small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(16.dp) **/

    // Formas cuadradas
    small = CutCornerShape(4.dp),
    medium = CutCornerShape(16.dp),
    large = CutCornerShape(16.dp)
)