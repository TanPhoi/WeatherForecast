package com.ntphoi.weatherforecast.ui.showWeather

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout
import com.ntphoi.weatherforecast.R
import com.ntphoi.weatherforecast.data.local.model.City
import com.ntphoi.weatherforecast.data.remote.model.WeatherDetailFiveDay
import com.ntphoi.weatherforecast.databinding.FragmentShowWeatherBinding
import com.ntphoi.weatherforecast.ui.base.BaseActivity
import com.ntphoi.weatherforecast.ui.base.BaseFragment
import com.ntphoi.weatherforecast.ui.showWeather.adapter.WeatherFiveDaysAdapter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class ShowWeatherFragment : BaseFragment<FragmentShowWeatherBinding, ShowWeatherViewModel>(),
    OnMapReadyCallback {

//    private val listItem = ArrayList<Item>()
//    private var adapter: DataAdapter? = null

    private var fadeIn: Animation? = null
    private var fadeOut: Animation? = null
    private var myMap: GoogleMap? = null
    private var adapter: WeatherFiveDaysAdapter? = null
    private val weatherList = ArrayList<WeatherDetailFiveDay>()
    private val handler = Handler()

    override fun layoutRes(): Int {
        return R.layout.fragment_show_weather
    }

    override fun viewModelClass(): Class<ShowWeatherViewModel> {
        return ShowWeatherViewModel::class.java
    }

    override fun initView() {
        fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_animation)
        fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out_animation)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val searchQuery = arguments?.getString("query")

        if (searchQuery != null) {
            viewModel?.getWeather(searchQuery)
            viewModel?.saveNameCity(searchQuery)
        }

        viewModel?.isLoading?.observe(this) {
            if (it) {
                (activity as? BaseActivity<*, *>)?.showLoading()
            } else {
                (activity as? BaseActivity<*, *>)?.hiddenLoading()
            }
        }

        binding?.relativeLayout?.visibility = View.GONE
        binding?.appBarLayout?.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val maxScroll = appBarLayout.totalScrollRange
            val percentage = Math.abs(verticalOffset).toFloat() / maxScroll.toFloat()

            val linearLayout2 = binding?.linearLayout2
            val relativeLayout = binding?.relativeLayout

            linearLayout2?.alpha = 1 - percentage
            relativeLayout?.alpha = percentage

            if (percentage == 0f) {
                relativeLayout?.visibility = View.VISIBLE
            }
        })

        handleAdapter()
        handleSetDataWeather()

        binding?.abShowWeather?.setBack {
            binding?.RelativeLayout?.startAnimation(fadeOut)
        }

        fadeOut?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                findNavController().popBackStack()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })

        binding?.RelativeLayout?.startAnimation(fadeIn)
    }

    private fun handleSetDataWeather() {

        viewModel?.weather?.observe(this) {
            binding?.tvNameCity?.text = it.name.toString()

            val dt = Instant.ofEpochSecond(it.dt?.toLong()!!).atZone(ZoneId.systemDefault())
                .toLocalDateTime()
            val formatterTime = DateTimeFormatter.ofPattern("HH:mm")

            val formattedTime = dt.format(formatterTime)
            binding?.tvTime?.text = formattedTime
            updateFormattedTime(dt)

            val temperature = it.main?.temp?.toInt()
            binding?.tvTemperature?.text = "$temperature°"
            binding?.tvTemperature1?.text = "$temperature°"

            binding?.tvContent?.text = "${it.weather?.get(0)?.description?.capitalize()}"
            binding?.tvContent1?.text = " | ${it.weather?.get(0)?.description?.capitalize()}"

            val tempMax = it.main?.tempMax?.toInt()
            val tempMin = it.main?.tempMin?.toInt()
            binding?.tvTempMinMax?.text = "C:${tempMax}°\tT:${tempMin}°"

            binding?.tvAll?.text = "${it.clouds.all}"
            if (it.clouds.all <= 10) {
                binding?.tvSkyMaxMin?.text = "Ít"
            } else {
                binding?.tvSkyMaxMin?.text = "Nhiều"
            }
            binding?.tvDescription?.text =
                "${it.weather[0].description?.capitalize(Locale.getDefault())}"

            val sunRise = Instant.ofEpochSecond(it.sys.sunrise).atZone(ZoneId.systemDefault())
                .toLocalDateTime()
            val formatterTimeSun = DateTimeFormatter.ofPattern("HH:mm")
            val formattedTimeSun = sunRise.format(formatterTimeSun)
            binding?.tvTimeSunRise?.text = formattedTimeSun

            val sunset = Instant.ofEpochSecond(it.sys.sunset).atZone(ZoneId.systemDefault())
                .toLocalDateTime()
            val formatterTimeSunset = DateTimeFormatter.ofPattern("HH:mm")
            val formattedTimeSunset = sunset.format(formatterTimeSunset)
            binding?.tvSunset?.text = formattedTimeSunset

            binding?.customCanvasView?.setDirection(it.wind.deg)
            val speed = it.wind.speed.toInt()
            binding?.customCanvasView?.setSpeed(speed)

            val feelsLike = it.main.feelsLike?.toInt()
            binding?.tvFeelsLike?.text = "$feelsLike°"

            binding?.tvHumidity?.text = "${it.main.humidity}°"
            binding?.tvShowHumidity?.text = "Độ ẩm là: ${it.main.humidity}°"

            binding?.tvVisibility?.text = "${it.visibility / 1000}km"
            if (it.visibility / 1000 >= 10) {
                binding?.tvShowVisibility?.text = "Hoàn toàn rõ ngay lúc này"
            } else if (it.visibility / 1000 < 10 || it.visibility / 1000 > 4) {
                binding?.tvShowVisibility?.text =
                    "Tầm nhìn ở mức trung bình, có thể gặp một số hạn chế nhưng vẫn khá tốt."
            } else {
                binding?.tvShowVisibility?.text =
                    "Có thể gặp phải những điều kiện như sương mù, mưa mù, hoặc khó nhìn xa."
            }

            binding?.tvPressure?.text = "${it.main.pressure} hPa"

            binding?.tvTempTwo?.text = "$temperature°"

            binding?.tvTempMinMaxTwo?.text = "C:${tempMax}°\tT:${tempMin}°"
            // 35.7721, -78.63861
            viewModel?.getAllFiveDayDays(it.coord.lon, it.coord.lat)
        }
    }

    private fun handleAdapter() {
        adapter = WeatherFiveDaysAdapter(weatherList)
        binding?.rcvWeather?.adapter = adapter

        viewModel?.weatherFiveDay?.observe(this) {
            weatherList.addAll(it.list)
            adapter?.notifyDataSetChanged()
        }

    }

    private fun updateFormattedTime(dt: LocalDateTime) {
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
        val formattedTime = dt.format(formatterTime)
        binding?.tvTime?.text = formattedTime

        handler.postDelayed({ updateFormattedTime(LocalDateTime.now()) }, 60 * 1000)
    }

//    private fun handleAdapter() {
//        val presidents = ItemRopo().people
//        val sectionItemDecoration = RecyclerSectionItemDecoration(
//            resources.getDimensionPixelSize(R.dimen.recycler_section_header_height),
//            true,
//            getSectionCallback(presidents)
//        )
//
//        adapter = DataAdapter(presidents)
//        binding?.rcvWeather?.addItemDecoration(sectionItemDecoration)
//        binding?.rcvWeather?.adapter = adapter
//    }

//    private fun getSectionCallback(people: List<Item>): RecyclerSectionItemDecoration.SectionCallback {
//        return object : RecyclerSectionItemDecoration.SectionCallback {
//            override fun isSection(position: Int): Boolean {
//                return (position == 0 || people[position].name!![0] != people[position - 1].name!![0])
//            }
//
//            override fun getSectionHeader(position: Int): CharSequence {
//                return people[position].name?.subSequence(0, 1)!!
//            }
//        }
//    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap

        viewModel?.weather?.observe(this) {
            val saigon = LatLng(it.coord.lat, it.coord.lon)
            myMap!!.addMarker(MarkerOptions().position(saigon).title("Thành phố Hồ Chí Minh"))
            myMap!!.moveCamera(CameraUpdateFactory.newLatLng(saigon))
        }
    }

    override fun onDestroyView() {
        handler.removeCallbacksAndMessages(null)
        binding?.relativeLayout?.removeAllViews()
        binding?.unbind()
        binding = null
        adapter = null
        fadeIn = null
        fadeOut = null
        myMap = null
        super.onDestroyView()
        weatherList.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel = null
        handler.removeCallbacksAndMessages(null)
        binding?.relativeLayout?.removeAllViews()
        binding?.unbind()
        binding = null
        adapter = null
        fadeIn = null
        fadeOut = null
        myMap = null
        weatherList.clear()
    }
}