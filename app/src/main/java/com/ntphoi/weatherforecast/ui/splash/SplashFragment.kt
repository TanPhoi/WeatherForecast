package com.ntphoi.weatherforecast.ui.splash

import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.ntphoi.weatherforecast.R
import com.ntphoi.weatherforecast.databinding.FragmentSplashBinding
import com.ntphoi.weatherforecast.ui.base.BaseFragment

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {

    private var fadeIn: Animation? = null
    private var fadeOut: Animation? = null

    override fun layoutRes(): Int = R.layout.fragment_splash

    override fun viewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun initView() {
        fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_animation)
        fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out_animation)

        fadeIn?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding?.imageView?.startAnimation(fadeOut)
                binding?.textView?.startAnimation(fadeOut)
                binding?.tvForecast?.startAnimation(fadeOut)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })

        fadeOut?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(R.id.action_splashFragment_to_firstScreenFragment)
                }, 3000)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })

        binding?.imageView?.startAnimation(fadeIn)
        binding?.textView?.startAnimation(fadeIn)
        binding?.tvForecast?.startAnimation(fadeIn)
    }

    override fun onDestroyView() {
        binding?.relativeLayout?.removeAllViews()
        binding?.unbind()
        binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        binding?.relativeLayout?.removeAllViews()
        binding?.unbind()
        binding = null
        viewModel = null
        super.onDestroy()
    }
}