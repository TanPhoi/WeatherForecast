package com.ntphoi.weatherforecast.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(private val preferences: SharedPreferences) {

    fun saveFirstScreen(firstScreen: Boolean) {
        preferences.edit().putBoolean("first_screen", firstScreen).apply()
    }

    fun getFirstScreen(): Boolean {
        return preferences.getBoolean("first_screen", false)
    }

    fun saveNameCity(name: String) {
        preferences.edit().putString("name", name).apply()
    }

    fun getNameCity(): String {
        return preferences.getString("name", "") ?: ""
    }
}