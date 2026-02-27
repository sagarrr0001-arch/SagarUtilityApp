package com.example.sagarutilityapp.network

import retrofit2.http.GET
import retrofit2.http.Query

data class GeoResponse(val results: List<GeoPlace>?)
data class GeoPlace(val name: String, val country: String?, val latitude: Double, val longitude: Double)

interface GeoApiService {
    @GET("v1/search")
    suspend fun searchCity(
        @Query("name") name: String,
        @Query("count") count: Int = 5
    ): GeoResponse
}