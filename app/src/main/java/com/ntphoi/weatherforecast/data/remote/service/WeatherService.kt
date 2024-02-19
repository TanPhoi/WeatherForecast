package com.ntphoi.weatherforecast.data.remote.service

import com.ntphoi.weatherforecast.data.remote.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") q: String,
        @Query("lang") lang: String,
        @Query("appid") appid: String,
        @Query("units") units: String
    ): WeatherResponse
}