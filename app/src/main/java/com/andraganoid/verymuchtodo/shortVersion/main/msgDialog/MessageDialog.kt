package com.andraganoid.verymuchtodo.shortVersion.main.msgDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.andraganoid.verymuchtodo.databinding.MessageDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import kotlin.concurrent.timerTask

class MessageDialog(private val message: MessageDialogData) : BottomSheetDialogFragment() {

    private lateinit var binding: MessageDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MessageDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.message = message
        binding.msgDialogToast.apply {
            isVisible = message.toast.isNotEmpty()
            text = message.toast
            if (message.toast.isNotEmpty()) {
                Timer().schedule(timerTask { dismiss() }, 3000)
            }
        }

        binding.msgDialogBtnLeft.setOnClickListener {
            message.btnLeft?.invoke()
            dismiss()
        }
        binding.msgDialogBtnMid.setOnClickListener {
            message.btnMid?.invoke()
            dismiss()
        }
        binding.msgDialogBtnRight.setOnClickListener {
            message.btnRight?.invoke()
            dismiss()
        }
    }

}