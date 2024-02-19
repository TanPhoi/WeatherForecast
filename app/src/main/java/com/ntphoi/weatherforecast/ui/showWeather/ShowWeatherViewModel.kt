package com.ntphoi.weatherforecast.ui.showWeather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ntphoi.weatherforecast.data.local.AppPreferences
import com.ntphoi.weatherforecast.data.local.LocalRepository
import com.ntphoi.weatherforecast.data.local.model.City
import com.ntphoi.weatherforecast.data.remote.RemoteRepository
import com.ntphoi.weatherforecast.data.remote.model.WeatherFiveDay
import com.ntphoi.weatherforecast.data.remote.model.WeatherResponse
import com.ntphoi.weatherforecast.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShowWeatherViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val appPreferences: AppPreferences

) : BaseViewModel() {
    var weather = MutableLiveData<WeatherResponse>()
    var weatherFiveDay = MutableLiveData<WeatherFiveDay>()
    var isLoading = MutableLiveData<Boolean>()

    init {

    }

    fun getWeather(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            try {
                val newWeather = remoteRepository.getWeather(name)
                weather.postValue(newWeather)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            isLoading.postValue(false)
        }
    }

    fun getAllFiveDayDays(lon: Double, lat: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            try {
                val newWeather = remoteRepository.getAllFiveDayDays(lon, lat)
                weatherFiveDay.postValue(newWeather)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            isLoading.postValue(false)
        }
    }

    fun saveNameCity(city: String) {
        appPreferences.saveNameCity(city)
    }

}