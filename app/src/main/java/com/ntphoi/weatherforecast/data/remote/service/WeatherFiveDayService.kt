package com.ntphoi.weatherforecast.data.remote.service

import com.ntphoi.weatherforecast.data.remote.model.WeatherFiveDay
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherFiveDayService {

    @GET
    suspend fun getAllFiveDayDays(
        @Url url : String,
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("appid") appid: String
    ): WeatherFiveDay
}