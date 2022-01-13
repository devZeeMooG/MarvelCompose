package com.zeemoog.marvelcompose.data.entities

/*
aqui van todos los datos q se repiten en los entities de marvel
con esto se logra un codigo mas generico
 */
interface MarvelItem {
    val id: Int
    val title: String
    val description: String
    val thumbnail: String
    val references: List<ReferenceList>
    val urls: List<Url>
}