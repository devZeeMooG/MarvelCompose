package com.zeemoog.marvelcompose.data.repositories

import com.zeemoog.marvelcompose.data.entities.Character
import com.zeemoog.marvelcompose.data.entities.Event
import com.zeemoog.marvelcompose.data.entities.Result
import com.zeemoog.marvelcompose.data.network.ApiClient

object EventsRepository: Repository<Event>() {

    suspend fun get(): Result<List<Event>> = super.get {
        ApiClient
            .eventsService
            .getEvents(0, 100)
            .data
            .results
            .map { it.asEvent() }
    }

    suspend fun find(id: Int): Result<Event> = super.find(
        id,
        findActionRemote = {
            ApiClient
                .eventsService
                .findEvent(id)
                .data
                .results
                .first()
                .asEvent()
        }
    )

}