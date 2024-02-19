package com.ntphoi.weatherforecast.ui.main

import com.ntphoi.weatherforecast.R
import com.ntphoi.weatherforecast.databinding.ActivityMainBinding
import com.ntphoi.weatherforecast.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun layoutRes(): Int = R.layout.activity_main

    override fun viewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun initView() {

    }

    override fun onDestroy() {
        super.onDestroy()
        this.setSupportActionBar(null)
    }
}