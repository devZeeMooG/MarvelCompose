package com.zeemoog.marvelcompose.data.network.entities

data class ApiEventsList(
    val available: Int,
    val collectionURI: String,
    val items: List<ApiEvent>,
    val returned: Int
)