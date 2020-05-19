package com.andraganoid.verymuchtodo.auth.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.util.BUNDLE_MSG_DIALOG_LIST
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_message_list_dialog.*


class MessageDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_message_list_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        messageList.adapter = arguments?.getStringArrayList(BUNDLE_MSG_DIALOG_LIST)?.let { MessageDialogAdapter(it) }
        okBtn.setOnClickListener { dismiss() }

    }


}
