package com.zeemoog.marvelcompose.data.repositories

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.zeemoog.marvelcompose.data.entities.*
import kotlinx.coroutines.withTimeout

/*
para no tenes que hacer peticiones seguidas
se cachea la peticion en memoria
 */
abstract class Repository<T: MarvelItem> {

    private var cache: List<T> = emptyList()

    /**internal suspend fun get(getAction: suspend () -> List<T>): Either<Error,List<T>> {
        return try {
            if (cache.isEmpty()) {
                cache = getAction()
            }
            cache.right()
        } catch (e: Exception) {
            e.toError().left()
        }
    }**/
    // igual a arriba pero mas resumido
    /**internal suspend fun get(getAction: suspend () -> List<T>): Result<List<T>> = try {
        if (cache.isEmpty()) {
            cache = getAction()
        }
        cache.right()
    } catch (e: Exception) {
        e.toError().left()
    } **/
    // abstraemos el bloque catch (en sealed class Error), ya q siempre es la misma
    // con la funcion inline tryCall
    internal suspend fun get(getAction: suspend () -> List<T>): Result<List<T>> = tryCall {
        if (cache.isEmpty()) {
            cache = getAction()
        }
        cache
    }

    /**internal suspend fun find(
        id: Int,
        findActionRemote: suspend () -> T
    ): T {
        val item = cache.find { it.id == id }
        if (item != null) return item

        return findActionRemote()
    } **/

    // como tryCall es una lambda no es necesario los return
    internal suspend fun find(
        id: Int,
        findActionRemote: suspend () -> T
    ): Result<T> = tryCall {
        val item = cache.find { it.id == id }

        //usamos operador elvis ?:
        // donde si el item es distinto de null devuelve item
        //  sino llama al servidor para recuperar los datos con el "findActionRemote"
        (item ?: findActionRemote())
    }

}