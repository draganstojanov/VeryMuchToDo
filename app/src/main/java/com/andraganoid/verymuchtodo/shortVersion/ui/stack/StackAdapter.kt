package com.andraganoid.verymuchtodo.shortVersion.ui.stack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.StackItemBinding
import com.andraganoid.verymuchtodo.shortVersion.model.TodoList

class StackAdapter(private val fragment: StackFragment) : RecyclerView.Adapter<StackAdapter.StackHolder>() {


    var stackList: ArrayList<TodoList?> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StackHolder {
        val binding = StackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StackHolder(binding)
    }

    override fun getItemCount(): Int = stackList.size

    override fun onBindViewHolder(holder: StackHolder, position: Int) = holder.bind(stackList[position])

    inner class StackHolder(private val binding: StackItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todoList: TodoList?) {
//            binding.stack = stack
//            binding.root.setOnClickListener { fragment.stackClicked(stack) }
//            binding.root.setOnLongClickListener { fragment.stackEdit(stack) }
            binding.executePendingBindings()
        }
    }
}