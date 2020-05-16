package com.andraganoid.verymuchtodo.ktodo.chat.chats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.ChatItemBinding
import com.andraganoid.verymuchtodo.kmodel.Chat


class ChatsAdapter(private val fragment: ChatsFragment) : RecyclerView.Adapter<ChatsAdapter.ChatsHolder>() {

    var chatList: ArrayList<Chat>? = arrayListOf()
    var allUsers: Int = 0

    fun setChatsList(cList: ArrayList<Chat>?, all: Int) {
        chatList?.clear()
        chatList?.addAll(cList!!)
        allUsers = all
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatsHolder(binding)
    }

    override fun getItemCount(): Int = chatList!!.size

    override fun onBindViewHolder(holder: ChatsHolder, position: Int) = holder.bind(chatList?.get(position)!!)

    inner class ChatsHolder(private val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.chat = chat
            val participants = "${binding.root.context.getString(R.string.participants)}${if (chat.members.size != 1) chat.members.size else allUsers}"
            binding.participants = participants
            val msgs = "${binding.root.context.getString(R.string.messages_no)}${chat.messages.size}"
            binding.msgs=msgs
            binding.root.setOnClickListener { fragment.showMessages(chat) }
            binding.executePendingBindings()
        }
    }
}