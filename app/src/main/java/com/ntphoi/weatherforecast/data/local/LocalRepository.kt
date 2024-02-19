package com.ntphoi.weatherforecast.data.local

import com.ntphoi.weatherforecast.data.local.dao.CityDao
import com.ntphoi.weatherforecast.data.local.dao.WeatherCityDao
import com.ntphoi.weatherforecast.data.local.model.City
import com.ntphoi.weatherforecast.data.local.model.WeatherCity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val cityDao: CityDao,
    private val weatherCityDao: WeatherCityDao
) {
    suspend fun getAllCity() : List<City>{
        return cityDao.getAllCity()
    }

    suspend fun insertCity(city: City){
        return cityDao.insertCity(city)
    }

    suspend fun getAllWeatherCity() : List<WeatherCity>{
        return weatherCityDao.getAllWeatherCity()
    }

    suspend fun insertWeatherCity(weatherCity: WeatherCity){
        return weatherCityDao.insertWeatherCity(weatherCity)
    }
}