package com.ntphoi.weatherforecast.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherFiveDay(
    @SerializedName("cod") val cod: String,
    @SerializedName("message") val message: Int,
    @SerializedName("cnt") val cnt: Int,
    @SerializedName("list") val list: List<WeatherDetailFiveDay>,
    @SerializedName("city") val city: City
)

data class WeatherDetailFiveDay(
    @SerializedName("dt") val dt: Long,
    @SerializedName("main") val main: MainFiveDay,
    @SerializedName("weather") val weather: List<WeatherList>,
    @SerializedName("clouds") val clouds: CloudsFiveDay,
    @SerializedName("wind") val wind: WindFiveDay,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("pop") val pop: Int,
    @SerializedName("sys") val sys: SysFiveDay,
    @SerializedName("dt_txt") val dt_txt: String
)

data class MainFiveDay(
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feels_like: Double,
    @SerializedName("temp_min") val temp_min: Double? = null,
    @SerializedName("temp_max") val temp_max: Double? = null,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("sea_level") val sea_level: Int,
    @SerializedName("grnd_level") val grnd_level: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("temp_kf") val temp_kf: Double
)

data class WeatherList(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class CloudsFiveDay(
    @SerializedName("all") val all: Int
)

data class WindFiveDay(
    @SerializedName("speed") val speed: Double,
    @SerializedName("deg") val deg: Int,
    @SerializedName("gust") val gust: Double
)

data class SysFiveDay(
    @SerializedName("pod") val pod: String
)

data class City(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("coord") val coord: CoordFiveDay,
    @SerializedName("country") val country: String,
    @SerializedName("population") val population: Int,
    @SerializedName("timezone") val timezone: Int,
    @SerializedName("sunrise") val sunrise: Int,
    @SerializedName("sunset") val sunset: Int
)

data class CoordFiveDay(
    @SerializedName("lon") val lon: Double,
    @SerializedName("lat") val lat: Double
)