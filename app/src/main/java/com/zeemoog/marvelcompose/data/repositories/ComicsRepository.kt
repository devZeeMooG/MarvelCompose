package com.zeemoog.marvelcompose.data.repositories

import com.zeemoog.marvelcompose.data.entities.Comic
import com.zeemoog.marvelcompose.data.network.ApiClient

object ComicsRepository {

    /**
     * no se puede tener un get generico
     * para el uso de todos los repository
     * xq este recibe un argumento extra
     */
    suspend fun get(format: Comic.Format): List<Comic> = ApiClient
        .comicsService
        .getComics(0, 20, format.toStringFormat())
        .data
        .results
        .map { it.asComic() }


    suspend fun find(id: Int): Comic = ApiClient
        .comicsService
        .findComic(id)
        .data
        .results
        .first()
        .asComic()

}