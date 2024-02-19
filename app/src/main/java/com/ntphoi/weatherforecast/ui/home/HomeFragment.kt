package com.ntphoi.weatherforecast.ui.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ntphoi.weatherforecast.R
import com.ntphoi.weatherforecast.data.local.model.WeatherCity
import com.ntphoi.weatherforecast.data.remote.model.WeatherResponse
import com.ntphoi.weatherforecast.databinding.FragmentHomeBinding
import com.ntphoi.weatherforecast.ui.base.BaseFragment
import com.ntphoi.weatherforecast.ui.customview.CustomView
import com.ntphoi.weatherforecast.ui.home.adapter.SaveWeatherCityAdapter
import com.ntphoi.weatherforecast.ui.widget.ActionBarHeaderCommon
import com.ntphoi.weatherforecast.util.setOnSingClickListener
import java.text.Normalizer

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private var adapter: SaveWeatherCityAdapter? = null
    private var weatherList = ArrayList<WeatherResponse>()
    private var fadeIn: Animation? = null
    private var fadeOut: Animation? = null

    override fun layoutRes(): Int = R.layout.fragment_home

    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun initView() {
        fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_animation)
        fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out_animation)

        adapter = SaveWeatherCityAdapter(weatherList)
        binding?.rcvWeather?.adapter = adapter
        binding?.svWeather?.queryHint = "Tìm tên thành phố"

        binding?.svWeather?.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    findNavController().navigate(
                        R.id.action_homeFragment_to_showWeatherFragment, bundleOf(
                            Pair("query", query)
                        )
                    )

                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text change if needed
                return true
            }
        })

        viewModel?.getWeather(viewModel?.getNameCity()!!)

        viewModel?.weather?.observe(this) { allWeatherList ->
            weatherList.clear()
            weatherList.add(allWeatherList)
            adapter?.notifyDataSetChanged()
        }

        adapter?.onItemClick = { weather ->
            binding?.relativeLayout?.startAnimation(fadeOut)
            startAnimation(weather)
        }

        binding?.relativeLayout?.startAnimation(fadeIn)
    }

    private fun startAnimation(weather: WeatherResponse) {

        fadeOut?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

                findNavController().navigate(
                    R.id.action_homeFragment_to_showWeatherFragment, bundleOf(
                        Pair("query", weather.name)
                    )
                )
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
    }

    override fun onDestroyView() {
        binding?.relativeLayout?.removeAllViews()
        binding?.unbind()
        binding = null
        adapter = null
        fadeIn = null
        fadeOut = null
        super.onDestroyView()
        weatherList.clear()
    }

    override fun onDestroy() {
        binding?.relativeLayout?.removeAllViews()
        binding?.unbind()
        binding = null
        fadeIn = null
        fadeOut = null
        viewModel = null
        super.onDestroy()

    }
}