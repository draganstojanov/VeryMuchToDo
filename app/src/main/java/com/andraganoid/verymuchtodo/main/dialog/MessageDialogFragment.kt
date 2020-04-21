package com.andraganoid.verymuchtodo.main.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.util.MSG_DIALOG_LIST
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_message_list_dialog.*
import java.util.*


class MessageDialogFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_message_list_dialog, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        messageList.adapter = arguments?.getStringArrayList(MSG_DIALOG_LIST)?.let { MessageDialogAdapter(it) }

        okBtn.setOnClickListener { dismiss() }

        Timer().schedule(object : TimerTask() {
            override fun run() {//todo null
                dismiss()
            }
        }, 5000)
    }



}
