package com.ntphoi.weatherforecast.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ntphoi.weatherforecast.data.local.dao.CityDao
import com.ntphoi.weatherforecast.data.local.dao.WeatherCityDao
import com.ntphoi.weatherforecast.data.local.model.City
import com.ntphoi.weatherforecast.data.local.model.WeatherCity

@Database(
    entities = [City::class, WeatherCity::class],
    version = 2,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun weatherCityDao(): WeatherCityDao
}