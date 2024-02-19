package com.ntphoi.weatherforecast.ui.firstScreen

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.ntphoi.weatherforecast.R
import com.ntphoi.weatherforecast.databinding.FragmentFirstScreenBinding
import com.ntphoi.weatherforecast.ui.base.BaseFragment
import com.ntphoi.weatherforecast.util.setOnSingClickListener

class FirstScreenFragment : BaseFragment<FragmentFirstScreenBinding, FirstScreenViewModel>() {
    private var topAnim: Animation? = null
    private var bottomAnim: Animation? = null
    private var leftAnim: Animation? = null
    private var rightAnim: Animation? = null
    private var fadeIn: Animation? = null
    private var fadeOut: Animation? = null
    override fun layoutRes(): Int {
        return R.layout.fragment_first_screen
    }

    override fun viewModelClass(): Class<FirstScreenViewModel> {
        return FirstScreenViewModel::class.java
    }

    override fun initView() {
        fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out_animation)
        topAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.top_animation)
        bottomAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_animtion)
        leftAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.left_animation)
        rightAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.right_animation)
        fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_animation)

        binding?.imageView?.animation = fadeIn
        binding?.textView?.animation = leftAnim
        binding?.tvForecast?.animation = rightAnim
        binding?.btnGetStart?.animation = bottomAnim

        binding?.btnGetStart?.setOnSingClickListener {
            binding?.imageView?.startAnimation(fadeOut)
            binding?.textView?.startAnimation(fadeOut)
            binding?.tvForecast?.startAnimation(fadeOut)
            binding?.btnGetStart?.startAnimation(fadeOut)
        }

        fadeOut?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                findNavController().navigate(R.id.action_firstScreenFragment_to_homeFragment)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
    }


    override fun onDestroyView() {
        binding?.relativeLayout?.removeAllViewsInLayout()
        binding?.unbind()
        binding = null
        topAnim = null
        bottomAnim = null
        leftAnim = null
        rightAnim = null
        fadeIn = null
        fadeOut = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        binding?.relativeLayout?.removeAllViewsInLayout()
        binding?.unbind()
        binding = null
        viewModel = null
        topAnim = null
        bottomAnim = null
        leftAnim = null
        rightAnim = null
        fadeIn = null
        fadeOut = null
        super.onDestroy()
    }

}