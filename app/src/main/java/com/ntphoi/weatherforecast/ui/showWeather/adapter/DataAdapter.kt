package com.ntphoi.weatherforecast.ui.showWeather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ntphoi.weatherforecast.data.local.model.City
import com.ntphoi.weatherforecast.databinding.ItemWeatherNextDaysBinding

class DataAdapter(private val dataList: List<City>) :
    RecyclerView.Adapter<DataAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(private val binding: ItemWeatherNextDaysBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: City) {
            //binding.listWeather = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ItemWeatherNextDaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = if (dataList.isNotEmpty()) dataList.size else 0
}