package com.ntphoi.weatherforecast.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ntphoi.weatherforecast.data.local.AppPreferences
import com.ntphoi.weatherforecast.data.local.LocalRepository
import com.ntphoi.weatherforecast.data.local.model.City
import com.ntphoi.weatherforecast.data.local.model.WeatherCity
import com.ntphoi.weatherforecast.data.remote.RemoteRepository
import com.ntphoi.weatherforecast.data.remote.model.WeatherResponse
import com.ntphoi.weatherforecast.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val appPreferences: AppPreferences
) : BaseViewModel() {
    var weather = MutableLiveData<WeatherResponse>()

    init {
    }

    fun getWeather(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newWeather = remoteRepository.getWeather(name)
                weather.postValue(newWeather)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getNameCity(): String {
        return appPreferences.getNameCity()
    }

}