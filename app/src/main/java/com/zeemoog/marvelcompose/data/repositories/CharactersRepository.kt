package com.zeemoog.marvelcompose.data.repositories

import com.zeemoog.marvelcompose.data.entities.Character
import com.zeemoog.marvelcompose.data.network.ApiClient


object CharactersRepository: Repository<Character>() {

    suspend fun get(): List<Character> = super.get {
        ApiClient
            .charactersService
            .getCharacters(0, 100)
            .data
            .results
            .map { it.asCharacter() }
    }

    suspend fun find(id: Int): Character = super.find(
        id,
        findActionRemote = {
            ApiClient
                .charactersService
                .findCharacter(id)
                .data
                .results
                .first()
                .asCharacter()
        }
    )

}