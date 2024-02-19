package com.ntphoi.weatherforecast.data.remote

import com.ntphoi.weatherforecast.data.remote.model.WeatherFiveDay
import com.ntphoi.weatherforecast.data.remote.model.WeatherResponse
import com.ntphoi.weatherforecast.data.remote.service.WeatherFiveDayService
import com.ntphoi.weatherforecast.data.remote.service.WeatherService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherFiveDayService: WeatherFiveDayService
) {

    suspend fun getWeather(name: String): WeatherResponse {
        return weatherService.getWeather(name, "vi", "86a6ca76070d9e0a4df68ebc2577fc50", "metric")
    }

    suspend fun getAllFiveDayDays(lon: Double, lat: Double): WeatherFiveDay {
        return weatherFiveDayService.getAllFiveDayDays(
            "https://api.openweathermap.org/data/2.5/forecast",
            lon,
            lat,
            "86a6ca76070d9e0a4df68ebc2577fc50"
        )
    }

}