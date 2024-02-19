package com.ntphoi.weatherforecast.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
 data class City (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Long? = null,
    @ColumnInfo(name = "name")
    var name : String? = null
)