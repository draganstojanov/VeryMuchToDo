package com.andraganoid.verymuchtodo.old.util.tm

import android.animation.ValueAnimator
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.andraganoid.verymuchtodo.databinding.TopModalLayoutBinding
import com.andraganoid.verymuchtodo.old.main.MainActivityOld
import com.andraganoid.verymuchtodo.old.util.toDp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopModal(private val parentView: ViewGroup, customView: View) {

    private val binding: TopModalLayoutBinding = TopModalLayoutBinding.inflate(LayoutInflater.from(parentView.context), parentView, true)
    private var isOpen = false
    internal var isCancellable = false
    private var maxHeight = Resources.getSystem().displayMetrics.heightPixels / 2
    internal var requestFocusOnExpand: View? = null

    init {
        binding.root.x = 0f
        binding.root.y = 0f
        binding.topModalWrapper.addView(customView)
        binding.overlay.setOnClickListener {
            if (isCancellable) {
                collapse()
            }
        }
    }

    fun toggleTopModal() {
        if (isOpen) collapse() else expand()
    }

    fun openIfClosed() {
        if (!isOpen) expand()
    }

    fun expand() {
        isOpen = true
        val matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec((parentView as View).width, View.MeasureSpec.EXACTLY)
        val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        binding.topModalWrapper.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
        var targetHeight = binding.topModalWrapper.measuredHeight
        targetHeight = minOf(targetHeight, maxHeight)
        binding.topModalWrapper.isVisible = true
        binding.overlay.isVisible = true
        ValueAnimator.ofInt(0, targetHeight).apply {
            duration = minOf(targetHeight.toDp(), 300).toLong()
            addUpdateListener { animation ->
                binding.topModalWrapper.apply {
                    layoutParams.height = animation.animatedValue as Int
                    requestLayout()
                }
            }
            doOnEnd {
                binding.topModalWrapper.apply {
                    layoutParams.height = targetHeight
                    requestLayout()
                    requestFocusOnExpand.let {
                        requestFocusFromTouch()
                        CoroutineScope(Dispatchers.Main).launch { MainActivityOld.insetController.show(WindowInsetsCompat.Type.ime()) }
                    }

                }
            }
            start()
        }
    }

    fun collapse() {
        isOpen = false
        val initialHeight = binding.topModalWrapper.measuredHeight
        ValueAnimator.ofInt(initialHeight, 0).apply {
            duration = minOf(initialHeight.toDp(), 300).toLong()
            addUpdateListener { animation ->
                binding.topModalWrapper.apply {
                    layoutParams.height = animation.animatedValue as Int
                    requestLayout()
                }
            }
            doOnEnd {
                binding.topModalWrapper.isVisible = false
                binding.overlay.isVisible = false
                MainActivityOld.insetController.hide(WindowInsetsCompat.Type.ime())
            }
            start()
        }
    }

}