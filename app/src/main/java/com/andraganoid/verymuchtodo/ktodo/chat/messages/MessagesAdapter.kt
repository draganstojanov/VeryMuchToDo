package com.andraganoid.verymuchtodo.ktodo.chat.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.MessageItemBinding
import com.andraganoid.verymuchtodo.kmodel.Message
import com.andraganoid.verymuchtodo.util.myUser


class MessagesAdapter(private val fragment: MessagesFragment) : RecyclerView.Adapter<MessagesAdapter.MessageHolder>() {



    var userMap: HashMap<String,String?> = hashMapOf()
        set(value) {
            field = value
        }

    var msgList: List<Message> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

//    fun setMsgList(mList: List<Message>?, uList: List<User>) {
//        msgList=mList
//        msgList?.forEach { message ->
//            message.from = uList.filter { user -> user.uid.equals(message.from.uid) }[0]
//           // message.isMyMsg = myUser.uid.equals(message.from.uid)
//        }
//
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageHolder(binding)
    }

    override fun getItemCount(): Int = msgList.size

    override fun onBindViewHolder(holder: MessageHolder, position: Int) = holder.bind(msgList.get(position))


    inner class MessageHolder(private val binding: MessageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            message.isMyMsg= myUser.uid == message.from.uid
            message.from.imageUrl=userMap.get(message.from.uid)
            binding.message = message
           // binding.isMyMsg = myUser.uid == message.from.uid
        }
    }
}

//message.from = users.filter { user -> user.uid.equals(message.from.uid) }[0]
//message.isMyMsg = myUser.uid.equals(message.from.uid)