package com.zeemoog.marvelcompose.data.repositories

import com.zeemoog.marvelcompose.data.entities.Comic
import com.zeemoog.marvelcompose.data.entities.Result
import com.zeemoog.marvelcompose.data.entities.tryCall
import com.zeemoog.marvelcompose.data.network.ApiClient

object ComicsRepository {

    /**
     * no se puede tener un get generico
     * para el uso de todos los repository
     * xq este recibe un argumento extra
     */
    // sin el uso de Either para manejo de errores

    /** suspend fun get(format: Comic.Format): List<Comic> = ApiClient
        .comicsService
        .getComics(0, 10, format.toStringFormat())
        .data
        .results
        .map { it.asComic() }


    suspend fun find(id: Int): Comic = ApiClient
        .comicsService
        .findComic(id)
        .data
        .results
        .first()
        .asComic()  **/

    // usando Either para manejo de errores

    suspend fun get(format: Comic.Format? = null): Result<List<Comic>> = tryCall {
        ApiClient
            .comicsService
            .getComics(0, 10, format?.toStringFormat())
            .data
            .results
            .map { it.asComic() }
    }


    suspend fun find(id: Int): Result<Comic> = tryCall {
        ApiClient
            .comicsService
            .findComic(id)
            .data
            .results
            .first()
            .asComic()
    }

}