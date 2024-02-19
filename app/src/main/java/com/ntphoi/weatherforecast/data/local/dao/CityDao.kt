package com.ntphoi.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ntphoi.weatherforecast.data.local.model.City

@Dao
interface CityDao {
    @Query("select * from city order by id")
    suspend fun getAllCity() : List<City>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCity(city: City)
}