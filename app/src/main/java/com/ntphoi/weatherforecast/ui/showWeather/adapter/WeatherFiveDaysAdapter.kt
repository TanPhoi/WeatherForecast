package com.ntphoi.weatherforecast.ui.showWeather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ntphoi.weatherforecast.data.remote.model.WeatherDetailFiveDay
import com.ntphoi.weatherforecast.databinding.ItemWeatherNextDaysBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class WeatherFiveDaysAdapter(private val weatherList: List<WeatherDetailFiveDay>) :
    RecyclerView.Adapter<WeatherFiveDaysAdapter.WeatherFiveDaysViewHolder>() {
    inner class WeatherFiveDaysViewHolder(val binding: ItemWeatherNextDaysBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: WeatherDetailFiveDay) {
            binding.weather = weather
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherFiveDaysViewHolder {
        val binding =
            ItemWeatherNextDaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherFiveDaysViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherFiveDaysViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.bind(weather)

        val dt = Instant.ofEpochSecond(weather.dt).atZone(ZoneId.systemDefault()).toLocalDateTime()
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
        val formatterDay = DateTimeFormatter.ofPattern("dd 'tháng' MM 'năm' yyyy", Locale("vi"))

// Format ngày theo định dạng mới
        val formattedDay = dt.format(formatterDay)

// Đặt chuỗi đã format vào TextView
        holder.binding.tvDay.text = "Ngày $formattedDay"

        val formattedTime = dt.format(formatterTime)
        holder.binding.tvTime.text = formattedTime
        val tempMaxKelvin = weather.main.temp_max?.toDouble() ?: 0.0
        val tempMinKelvin = weather.main.temp_min?.toDouble() ?: 0.0

        val tempMaxCelsius = (tempMaxKelvin - 273.15).toInt()
        val tempMinCelsius = (tempMinKelvin - 273.15).toInt()

        holder.binding.tvTempMax.text = "$tempMaxCelsius°C"
        holder.binding.tvTempMin.text = "$tempMinCelsius°C"

        val iconId = weather.weather[0].icon
        val iconUrl = "http://openweathermap.org/img/wn/$iconId.png"

        Glide.with(holder.itemView.context).load(iconUrl).into(holder.binding.imgIcon)
    }


    override fun getItemCount(): Int = if (weatherList.isNotEmpty()) weatherList.size else 0
}