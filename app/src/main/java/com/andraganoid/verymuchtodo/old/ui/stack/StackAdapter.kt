package com.andraganoid.verymuchtodo.old.ui.stack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.StackItemBinding
import com.andraganoid.verymuchtodo.old.model.TodoList
import com.andraganoid.verymuchtodo.old.model.isCompleted

class StackAdapter(private val fragment: StackFragment) : ListAdapter<TodoList, StackAdapter.StackHolder>(StackCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StackHolder {
        val binding = StackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StackHolder(binding)
    }

    override fun onBindViewHolder(holder: StackHolder, position: Int) = holder.bind(getItem(position))

    inner class StackHolder(private val binding: StackItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todoList: TodoList?) {
            binding.todoList = todoList
            binding.completed = todoList?.isCompleted()
            binding.root.setOnClickListener { fragment.listSelected(todoList!!.id) }
            binding.stackEditIcon.setOnClickListener {
                if (todoList != null) {
                    fragment.openTodoListEditor(todoList, false)
                }
            }
            binding.stackDeleteIcon.setOnClickListener {
                if (todoList != null) {
                    fragment.deleteList(todoList)
                }
            }
            binding.executePendingBindings()
        }
    }

    class StackCallback : DiffUtil.ItemCallback<TodoList>() {

        override fun areItemsTheSame(oldItem: TodoList, newItem: TodoList): Boolean {
            return oldItem.id == newItem.id;
        }

        override fun areContentsTheSame(oldItem: TodoList, newItem: TodoList): Boolean {
            return oldItem == newItem
        }
    }

}