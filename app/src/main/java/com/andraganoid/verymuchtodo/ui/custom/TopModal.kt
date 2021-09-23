package com.andraganoid.verymuchtodo.ui.custom

import android.animation.ValueAnimator
import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import com.andraganoid.verymuchtodo.databinding.TopModalCoreBinding
import com.andraganoid.verymuchtodo.util.ANIMATION_DURATION


class TopModal @JvmOverloads constructor(ctx: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(ctx, attrs, defStyle) {

    private var topModalHeight: Int = 0

    var binding: TopModalCoreBinding = TopModalCoreBinding.inflate(LayoutInflater.from(this.context), this, true)

    init {
        binding.topModal.doOnLayout { initCollapse() }
    }

    fun setupFields(label1: String, label2: String?, cancelClick: (() -> Unit)?, submitClick: () -> Unit) {
        binding.label1.text = label1
        binding.input1.filters = arrayOf(InputFilter.LengthFilter(if (label2 == null) 20 else 50))
        if (label2 != null) {
            binding.label2.text = label2
        }
        binding.cancelBtn.setOnClickListener { cancelClick?.invoke() }
        binding.submitBtn.setOnClickListener { submitClick.invoke() }

        binding.label2.isVisible = label2 != null
        binding.input2.isVisible = label2 != null
        binding.cancelBtn.isVisible = cancelClick != null

    }

    fun setHints(hint1: String, hint2: String?) {
        binding.input1.hint = hint1
        if (hint2 != null) binding.input2.hint = hint2
    }

    fun setInputValues(input1: String, input2: String) {
        binding.input1.setText(input1)
        binding.input2.setText(input2)
    }

    fun getInputValue1(): String = binding.input1.text.toString()

    fun getInputValue2(): String = binding.input2.text.toString()

    fun isOpen(): Boolean = binding.topModalWrapper.isVisible

    fun expand() {
        binding.topModalWrapper.isVisible = true
        binding.overlay.isVisible = true
        (ValueAnimator.ofInt(0, topModalHeight)).apply {
            duration = ANIMATION_DURATION
            addUpdateListener { animation ->
                binding.topModal.apply {
                    layoutParams.height = animation.animatedValue as Int
                    requestLayout()
                }
            }
            doOnEnd {
                binding.topModal.apply {
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    requestLayout()
                }
            }
            start()
        }
    }

    fun collapse() {
        collapseTopModal(ANIMATION_DURATION)
    }

    private fun initCollapse() {
        topModalHeight = binding.topModal.height
        collapseTopModal(1)
    }

    private fun collapseTopModal(animationDuration: Long) {
        (ValueAnimator.ofInt(topModalHeight, 0)).apply {
            duration = animationDuration
            addUpdateListener { animation ->
                binding.topModal.apply {
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