package com.andraganoid.verymuchtodo.old.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.TodoListItemBinding
import com.andraganoid.verymuchtodo.model.TodoItem

class TodoListAdapter(private val fragment: TodoListFragment) : ListAdapter<TodoItem, TodoListAdapter.TodoListHolder>(
   TodoListCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListAdapter.TodoListHolder =
        TodoListHolder(TodoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TodoListAdapter.TodoListHolder, position: Int) = holder.bind(getItem(position))

    inner class TodoListHolder(private val binding: TodoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todoItem: TodoItem) {
            binding.todoItem = todoItem
            binding.listEditIcon.setOnClickListener { fragment.openTodoItemEditor(todoItem, false) }
            binding.listCheckDeleteIcon.setOnClickListener { fragment.deleteItem(todoItem) }
            binding.root.setOnClickListener { fragment.checkItem(todoItem) }
            binding.executePendingBindings()
        }
    }

    class TodoListCallback : DiffUtil.ItemCallback<TodoItem>() {
        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem.id == newItem.id;
        }

        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem == newItem && oldItem.completed != newItem.completed
        }
    }
}
