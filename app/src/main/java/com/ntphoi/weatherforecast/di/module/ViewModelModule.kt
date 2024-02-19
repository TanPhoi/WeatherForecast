package com.ntphoi.weatherforecast.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ntphoi.weatherforecast.di.viewmodel.ViewModelFactory
import com.ntphoi.weatherforecast.di.viewmodel.ViewModelKey
import com.ntphoi.weatherforecast.ui.firstScreen.FirstScreenViewModel
import com.ntphoi.weatherforecast.ui.home.HomeViewModel
import com.ntphoi.weatherforecast.ui.main.MainViewModel
import com.ntphoi.weatherforecast.ui.showWeather.ShowWeatherViewModel
import com.ntphoi.weatherforecast.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun splashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FirstScreenViewModel::class)
    internal abstract fun firstScreenViewModel(viewModel: FirstScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShowWeatherViewModel::class)
    internal abstract fun showWeatherViewModel(viewModel: ShowWeatherViewModel): ViewModel

}