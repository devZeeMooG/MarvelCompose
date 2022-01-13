package com.zeemoog.marvelcompose.data.network

import com.zeemoog.marvelcompose.data.network.entities.ApiEvent
import com.zeemoog.marvelcompose.data.network.entities.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventsService {
    @GET("/v1/public/events")
    suspend fun getEvents(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): ApiResponse<ApiEvent>

    @GET("/v1/public/events/{eventId}")
    suspend fun findEvent(
        @Path("eventId") eventId: Int,
    ): ApiResponse<ApiEvent>
}