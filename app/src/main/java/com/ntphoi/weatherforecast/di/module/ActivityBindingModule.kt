package com.ntphoi.weatherforecast.di.module

import com.ntphoi.weatherforecast.ui.firstScreen.FirstScreenFragment
import com.ntphoi.weatherforecast.ui.home.HomeFragment
import com.ntphoi.weatherforecast.ui.splash.SplashFragment
import com.ntphoi.weatherforecast.ui.main.MainActivity
import com.ntphoi.weatherforecast.ui.showWeather.ShowWeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun bindFirstScreenFragment(): FirstScreenFragment

    @ContributesAndroidInjector
    abstract fun bindShowWeatherFragment(): ShowWeatherFragment

}