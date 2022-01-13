package com.zeemoog.marvelcompose.data.network.entities

data class ApiCharacter(
    val comics: ApiReferenceList,
    val description: String,
    val events: ApiReferenceList,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: ApiReferenceList,
    val stories: ApiReferenceList,
    val thumbnail: ApiThumbnail,
    val urls: List<ApiUrl>
)