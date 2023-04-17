package com.andraganoid.verymuchtodo.old.ui.stack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.StackItemBinding
import com.andraganoid.verymuchtodo.model.TodoStack
import com.andraganoid.verymuchtodo.model.isCompleted

class StackAdapter(private val fragment: StackFragment) : ListAdapter<TodoStack, StackAdapter.StackHolder>(StackCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StackHolder {
        val binding = StackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StackHolder(binding)
    }

    override fun onBindViewHolder(holder: StackHolder, position: Int) = holder.bind(getItem(position))

    inner class StackHolder(private val binding: StackItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todoStack: TodoStack?) {
            binding.todoList = todoStack
            binding.completed = todoStack?.isCompleted()
            binding.root.setOnClickListener { fragment.listSelected(todoStack!!.id) }
            binding.stackEditIcon.setOnClickListener {
                if (todoStack != null) {
                    fragment.openTodoListEditor(todoStack, false)
                }
            }
            binding.stackDeleteIcon.setOnClickListener {
                if (todoStack != null) {
                    fragment.deleteList(todoStack)
                }
            }
            binding.executePendingBindings()
        }
    }

    class StackCallback : DiffUtil.ItemCallback<TodoStack>() {

        override fun areItemsTheSame(oldItem: TodoStack, newItem: TodoStack): Boolean {
            return oldItem.id == newItem.id;
        }

        override fun areContentsTheSame(oldItem: TodoStack, newItem: TodoStack): Boolean {
            return oldItem == newItem
        }
    }

}