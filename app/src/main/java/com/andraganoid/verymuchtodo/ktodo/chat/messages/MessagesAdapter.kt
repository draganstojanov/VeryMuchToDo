package com.andraganoid.verymuchtodo.ktodo.chat.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.MessageItemBinding
import com.andraganoid.verymuchtodo.kmodel.Message


class MessagesAdapter(private val fragment: MessagesFragment) : RecyclerView.Adapter<MessagesAdapter.MessageHolder>() {

    var msgList: List<Message> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageHolder(binding)
    }

    override fun getItemCount(): Int = msgList.size

    override fun onBindViewHolder(holder: MessageHolder, position: Int) = holder.bind(msgList.get(position))

    inner class MessageHolder(private val binding: MessageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.message = message
        }
    }
}
