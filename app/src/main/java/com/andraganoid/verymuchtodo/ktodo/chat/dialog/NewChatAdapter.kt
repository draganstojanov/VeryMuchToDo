package com.andraganoid.verymuchtodo.ktodo.chat.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.NewChatItemBinding

class NewChatAdapter(private val userList: ArrayList<NewUser>) : RecyclerView.Adapter<NewChatAdapter.NewUserHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewUserHolder {
        val binding = NewChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewUserHolder(binding)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: NewUserHolder, position: Int) = holder.bind(userList.get(position))

    inner class NewUserHolder(private val binding: NewChatItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(newUser: NewUser) {

            binding.newUser = newUser
            binding.topLine.isVisible = layoutPosition == 0
            binding.executePendingBindings()
        }

    }
}
