package com.andraganoid.verymuchtodo.ktodo.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.UserItemBinding
import com.andraganoid.verymuchtodo.kmodel.User

class UsersAdapter(private val userList: ArrayList<User>?, private val fragment: UsersFragment) : RecyclerView.Adapter<UsersAdapter.UserHolder>() {

    fun setUserList(uList: ArrayList<User>?) {
        userList?.clear()
        userList?.addAll(uList!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserHolder(binding)
    }

    override fun getItemCount(): Int = userList!!.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) = holder.bind(userList!!.get(position))

    inner class UserHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            binding.fragment = fragment
        }
    }
}