package com.ntphoi.weatherforecast.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class WeatherCity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Long? = null
    @ColumnInfo(name = "name")
    var name : String? = null
    @ColumnInfo(name = "time")
    var time : Long? = null
    @ColumnInfo(name = "description")
    var description : String? = null
    @ColumnInfo(name = "temp")
    var temp : Double? = null
    @ColumnInfo(name = "tempMax")
    var tempMax : Double? = null
    @ColumnInfo(name = "tempMin")
    var tempMin : Double? = null
}