package com.andraganoid.verymuchtodo.ui.tools

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.andraganoid.verymuchtodo.R


private const val DURATION = 100L

fun View.expand() {
    val targetHeight = resources.getDimensionPixelSize(R.dimen.dropdownItemHeight)*3
    ValueAnimator.ofInt(0, targetHeight).apply {
        duration = DURATION
        addUpdateListener { animation ->
            layoutParams.height = animation.animatedValue as Int
            requestLayout()
        }
        doOnEnd {
            layoutParams.height = targetHeight
            requestLayout()
        }
        start()
    }
}


fun View.collapse() {
    val initialHeight = measuredHeight
    ValueAnimator.ofInt(initialHeight, 0).apply {
        duration = DURATION
        addUpdateListener { animation ->
            layoutParams.height = animation.animatedValue as Int
            requestLayout()
        }
        doOnEnd {
            isVisible = false
        }
        start()
    }
}

fun View.arrowRotationExpand() {
    ObjectAnimator.ofFloat(this, View.ROTATION, 0f, 180f)
        .apply {
            duration = DURATION
            start()
        }
}

fun View.arrowRotationCollapse() {
    ObjectAnimator.ofFloat(this, View.ROTATION, 180f, 0f)
        .apply {
            duration = DURATION
            start()
        }
}