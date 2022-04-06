package com.andraganoid.verymuchtodo.ui.tools

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.andraganoid.verymuchtodo.R


private const val DURATION = 250L
private const val EXPANDED = "expanded"
private const val COLLAPSED = "collapsed"

fun View.expand() {
    val targetHeight = resources.getDimensionPixelSize(R.dimen.dropdownItemHeight) * 3
    ValueAnimator.ofInt(0, targetHeight).apply {
        duration = DURATION
        isVisible = true
        addUpdateListener { animation ->
            layoutParams.height = animation.animatedValue as Int
            requestLayout()
        }
        doOnEnd {
            layoutParams.height = targetHeight
            requestLayout()
            tag = EXPANDED
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
            tag = COLLAPSED
        }
        start()
    }
}

fun View.arrowRotationExpand() {
    ObjectAnimator.ofFloat(this, View.ROTATION, 0f, 180f)
        .apply {
            duration = DURATION
            start()
            tag = EXPANDED
        }
}

fun View.arrowRotationCollapse() {
    ObjectAnimator.ofFloat(this, View.ROTATION, 180f, 0f)
        .apply {
            duration = DURATION
            start()
            tag = COLLAPSED
        }
}

fun View.toggleExpand() {
    if (tag == EXPANDED) {
        collapse()
    } else {
        expand()
    }
}

fun View.collapseIfExpanded() {
    if (tag == EXPANDED) {
        collapse()
    }
}

fun View.toggleArrow() {
    if (tag == EXPANDED) {
        arrowRotationCollapse()
    } else {
        arrowRotationExpand()
    }
}

fun View.arrowCollapseIfExpanded() {
    if (tag == EXPANDED) {
        arrowRotationCollapse()
    }
}