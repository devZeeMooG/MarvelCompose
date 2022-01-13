package com.zeemoog.marvelcompose.data.repositories

import com.zeemoog.marvelcompose.data.entities.MarvelItem

/*
para no tenes que hacer peticiones seguidas
se cachea la peticion en memoria
 */
abstract class Repository<T: MarvelItem> {

    private var cache: List<T> = emptyList()

    internal suspend fun get(getAction: suspend () -> List<T>): List<T> {
        if (cache.isEmpty()) {
            cache = getAction()
        }
        return cache
    }

    internal suspend fun find(
        id: Int,
        findActionRemote: suspend () -> T
    ): T {
        val item = cache.find { it.id == id }
        if (item != null) return item

        return findActionRemote()
    }

}