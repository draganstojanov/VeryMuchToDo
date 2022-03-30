package com.andraganoid.verymuchtodo.ui.custom

import android.animation.ValueAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.andraganoid.verymuchtodo.databinding.TopModalLayoutBinding
import com.andraganoid.verymuchtodo.util.toDp

class TopModalNEW(private val parent: ViewGroup, customView: View) {

    private val binding: TopModalLayoutBinding = TopModalLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, true)
    private var isOpen = false
    internal var isClickable = false

    init {
        binding.root.x = 0f
        binding.root.y = 0f
        binding.topModal.addView(customView)
        binding.overlay.setOnClickListener { if (isClickable) collapse() }
        expand()
    }

    fun setState(state: Boolean) {
        if (state) expand() else collapse()
    }

    fun toggleTopModal() {
        if (isOpen) collapse() else expand()
    }


    fun expand() {
        isOpen = true
        val matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec((parent as View).width, View.MeasureSpec.EXACTLY)
        val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        binding.topModalWrapper.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
        val expandedHeight = binding.topModalWrapper.measuredHeight

        Log.d("TAGG", expandedHeight.toString())

        binding.topModalWrapper.isVisible = true
        binding.overlay.isVisible = true
        ValueAnimator.ofInt(0, expandedHeight).apply {
            val d = expandedHeight.toDp()// Expansion speed of 1dp/ms
            duration = minOf(d, 200).toLong()
            addUpdateListener { animation ->
                binding.topModalWrapper.apply {
                    layoutParams.height = animation.animatedValue as Int
                    requestLayout()
                }
            }
            doOnEnd {
                binding.topModalWrapper.apply {
                     layoutParams.height = expandedHeight
                  //  layoutParams.height - ViewGroup.LayoutParams.WRAP_CONTENT
                    requestLayout()
                }
            }
            start()
        }
    }

    fun collapse() {
        isOpen = false
        val expandedHeight = binding.topModalWrapper.measuredHeight

        ValueAnimator.ofInt(expandedHeight, 0).apply {
            val d = expandedHeight.toDp()// Expansion speed of 1dp/ms
            duration = minOf(d, 200).toLong()
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
