package com.zeemoog.marvelcompose.data.network.entities

data class ApiThumbnail(
    val extension: String,
    val path: String
)

/*
funcion de extension
 */
fun ApiThumbnail.asString() = "$path.$extension".replace("http", "https")