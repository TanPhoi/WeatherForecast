package com.ntphoi.weatherforecast.ui.home.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.ntphoi.weatherforecast.data.remote.model.WeatherResponse
import com.ntphoi.weatherforecast.databinding.ItemSaveWeatherCityBinding
import com.ntphoi.weatherforecast.util.setOnSingClickListener
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class SaveWeatherCityAdapter(private val weatherList: List<WeatherResponse>) :
    RecyclerView.Adapter<SaveWeatherCityAdapter.SaveWeatherCityViewHolder>() {
    inner class SaveWeatherCityViewHolder(val binding: ItemSaveWeatherCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: WeatherResponse) {
            binding.weather = weather
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveWeatherCityViewHolder {
        val binding =
            ItemSaveWeatherCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SaveWeatherCityViewHolder(binding)
    }

    var onItemClick: ((WeatherResponse) -> Unit)? = null
    override fun onBindViewHolder(holder: SaveWeatherCityViewHolder, position: Int) {
        val city = weatherList[position]
        holder.bind(city)
        val temperature = city.main.temp?.toInt()
        holder.binding.tvTemperature.text = "$temperature°"

        val tempMax = city.main.tempMax?.toInt()
        val tempMin = city.main.tempMin?.toInt()
        holder.binding.tvTempMinMax.text = "C:${tempMax}°\tT:${tempMin}°"

        val dt = Instant.ofEpochSecond(city.dt).atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")

        val formattedTime = dt.format(formatterTime)
        holder.binding.tvTime.text = formattedTime

        holder.binding.tvDescription.text =
            "${city.weather[0].description?.capitalize(Locale.getDefault())}"

        holder.itemView.setOnSingClickListener {
            onItemClick?.invoke(city)
        }

    }

    override fun getItemCount(): Int = if (weatherList.isNotEmpty()) weatherList.size else 0
}