package com.zeemoog.marvelcompose.data.network.entities

data class ApiReferenceList(
    val available: Int,
    val collectionURI: String,
    val items: List<ApiReference>?,
    val returned: Int
)