package com.zeemoog.marvelcompose.data.network.entities

data class ApiSeriesList(
    val available: Int,
    val collectionURI: String,
    val items: List<ApiSerie>,
    val returned: Int
)