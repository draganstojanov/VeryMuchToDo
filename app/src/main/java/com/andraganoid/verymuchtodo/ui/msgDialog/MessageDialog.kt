package com.andraganoid.verymuchtodo.ui.msgDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.andraganoid.verymuchtodo.databinding.MessageDialogBinding
import com.andraganoid.verymuchtodo.util.ARGS_DIALOG_DATA
import com.andraganoid.verymuchtodo.util.MESSAGE_DURATION
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import kotlin.concurrent.timerTask

class MessageDialog : BottomSheetDialogFragment() {

    private lateinit var binding: MessageDialogBinding
    private lateinit var message: MessageDialogData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MessageDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARGS_DIALOG_DATA) }?.apply {
            message = getSerializable(ARGS_DIALOG_DATA) as MessageDialogData
        }
        binding.message = message
        binding.msgDialogToast.apply {
            isVisible = message.toast.isNotEmpty()
            text = message.toast
            if (message.toast.isNotEmpty()) {
                Timer().schedule(timerTask { dismiss() }, MESSAGE_DURATION)
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