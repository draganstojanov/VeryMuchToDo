package com.andraganoid.verymuchtodo.main.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.R
import kotlinx.android.synthetic.main.fragment_message_list_dialog_item.view.*
import java.util.*


class MessageDialogAdapter internal constructor(private val msgList: ArrayList<String>) : RecyclerView.Adapter<MessageDialogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = msgList.get(position)
        holder.space.isVisible = position != msgList.size - 1
    }

    override fun getItemCount(): Int = msgList.size


    inner class ViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_message_list_dialog_item, parent, false)) {

        internal val text: TextView = itemView.messageDialogItemText
        internal val space: View = itemView.messageDialogSpace
    }
}