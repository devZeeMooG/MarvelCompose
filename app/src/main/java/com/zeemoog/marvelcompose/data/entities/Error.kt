package com.zeemoog.marvelcompose.data.entities

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import retrofit2.HttpException
import java.io.IOException

// con esto nos evitamos de escribir siempre la expresion "Either<Error, List<T>>"
//typealias Result<T> = Either<Error, List<T>>
// lo generalizamos un poco mas, entonces toma listas o elementos individuales
typealias Result<T> = Either<Error, T>

sealed class Error {
    class Server(val code: Int) : Error()
    class Unknown(val message: String) : Error()
    object Connectivity: Error()
}

            // funciones de extension

// implementacion de la excepcion
fun Exception.toError(): Error = when(this) {
    is IOException -> Error.Connectivity
    is HttpException -> Error.Server(code())
    else -> Error.Unknown(message ?: "") //si es nulo devuelve cadena vacia (para no tratar con nulos)
}


// funcion para abstraer el bloque catch
//  funcion generica
//  dada una accion q devuelve un tipo T, devuelva Result de T
// es inline para sustituir el codigo de la funcion en donde es llamada
//      y asi, tratar al bloque try/catch abstraido (de ese lugar) como uno original
inline fun <T> tryCall(action: () -> T): Result<T> = try {
    action().right()
} catch (e: Exception) {
    e.toError().left()
}
