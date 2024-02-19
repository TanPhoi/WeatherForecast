package com.ntphoi.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ntphoi.weatherforecast.data.local.model.WeatherCity

@Dao
interface WeatherCityDao {
    @Query("select * from city order by id")
    suspend fun getAllWeatherCity() : List<WeatherCity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWeatherCity(weatherCity: WeatherCity)
}