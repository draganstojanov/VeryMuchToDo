package com.andraganoid.verymuchtodo.ui.custom

import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.andraganoid.verymuchtodo.databinding.TopModalLayoutBinding
import com.andraganoid.verymuchtodo.util.ANIMATION_DURATION
import com.andraganoid.verymuchtodo.util.toDp


class TopModalNEW(private val parent: ViewGroup, cv: View) {


    private val binding: TopModalLayoutBinding = TopModalLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, true)
    private val root = binding.root


    init {
        root.x = 0f
        root.y = 0f


        binding.topModal.addView(cv)

//        Handler(Looper.getMainLooper()).postDelayed({
          expand()
//        }, 3000)

        Handler(Looper.getMainLooper()).postDelayed({
            collapse()
        }, 2000)


    }


    fun expand() {

    val matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec((parent as View).width, View.MeasureSpec.EXACTLY)
    val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    binding. topModalWrapper.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
    val targetHeight = binding.topModalWrapper.measuredHeight

        binding.topModalWrapper.isVisible = true
        binding.overlay.isVisible = true
        (ValueAnimator.ofInt(0, targetHeight)).apply {
            val d = targetHeight.toDp()*2// Expansion speed of 1dp/ms
            duration = minOf(d, 300).toLong()
            addUpdateListener { animation ->
                binding.topModalWrapper.apply {
                    layoutParams.height = animation.animatedValue as Int
                    requestLayout()
                }
            }
            doOnEnd {
                binding.topModalWrapper.apply {
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    requestLayout()
                }
            }
            start()
        }
    }

    fun collapse() {

        val initialHeight = binding.topModalWrapper.measuredHeight

        (ValueAnimator.ofInt(initialHeight, 0)).apply {
            val d = initialHeight.toDp()*2// Expansion speed of 1dp/ms
            duration = minOf(d, 300).toLong()
            addUpdateListener { animation ->
                binding.topModalWrapper.apply {
                    layoutParams.height = animation.animatedValue as Int
                    requestLayout()
                }
            }
            doOnEnd {
                binding.topModalWrapper.isVisible = false
                binding.overlay.isVisible = false
            }
            start()
        }
    }

}