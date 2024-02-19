package com.ntphoi.weatherforecast.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.ntphoi.weatherforecast.R
import com.ntphoi.weatherforecast.databinding.LayoutActionBarHeaderCommonBinding
import com.ntphoi.weatherforecast.util.setOnSingClickListener

class ActionBarHeaderCommon(context: Context?, attrs: AttributeSet?) :
    RelativeLayout(context, attrs) {
    private var binding: LayoutActionBarHeaderCommonBinding

    init {
        binding = LayoutActionBarHeaderCommonBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)

        val typeArray = context?.obtainStyledAttributes(attrs, R.styleable.ActionBarHeaderCommon)

        val srcImage = typeArray?.getResourceId(R.styleable.ActionBarHeaderCommon_srcImage, -1)
        if (srcImage != -1) {
            binding.imgMenu.setImageDrawable(ContextCompat.getDrawable(context!!, srcImage!!))
        }

        val enableSrcImage =
            typeArray.getBoolean(R.styleable.ActionBarHeaderCommon_enableSrcImage, false)
        binding.imgMenu.visibility = if (enableSrcImage) View.VISIBLE else View.GONE

        val tvOne = typeArray.getString(R.styleable.ActionBarHeaderCommon_tvOne)
        binding.textViewOne.text = tvOne
        val enableTvOne =
            typeArray.getBoolean(R.styleable.ActionBarHeaderCommon_enableTvOne, false)
        binding.textViewOne.visibility = if (enableTvOne) View.VISIBLE else View.GONE

        val tvTwo = typeArray.getString(R.styleable.ActionBarHeaderCommon_tvTwo)
        binding.textViewTwo.text = tvTwo
        val enableTvTwo =
            typeArray.getBoolean(R.styleable.ActionBarHeaderCommon_enableTvTwo, false)
        binding.textViewTwo.visibility = if (enableTvTwo) View.VISIBLE else View.GONE

        typeArray.recycle()
    }

    fun setBack(callback: (() -> Unit)) {
        binding.textViewTwo.setOnSingClickListener {
            callback.invoke()
        }
    }
}