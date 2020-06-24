package com.andraganoid.verymuchtodo.ktodo.chat.chats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.ChatItemBinding
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.util.myUser


class ChatsAdapter(private val chatList: List<Chat>, private val allUsers: HashMap<String, String?>, private val fragment: ChatsFragment) : RecyclerView.Adapter<ChatsAdapter.ChatsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatsHolder(binding)
    }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(holder: ChatsHolder, position: Int) = holder.bind(chatList.get(position))

    inner class ChatsHolder(private val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {

            if (chat.members.size == 2) {
                val other = if (chat.members.get(0).equals(myUser.uid)) chat.members.get(1) else chat.members.get(0)
                chat.iconUrl = allUsers.get(other)
            }

            binding.chat = chat
            val participants = "${binding.root.context.getString(R.string.participants)}${if (chat.members.size != 1) chat.members.size else allUsers.size}"
            binding.participants = participants
            val msgs = "${binding.root.context.getString(R.string.messages_no)}${chat.messages.size}"
            binding.msgs = msgs
            binding.root.setOnClickListener { fragment.showMessages(chat) }
            binding.executePendingBindings()
        }
    }
}