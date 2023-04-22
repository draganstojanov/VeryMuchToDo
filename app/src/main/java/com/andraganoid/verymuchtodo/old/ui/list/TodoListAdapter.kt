package com.andraganoid.verymuchtodo.old.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.TodoListItemBinding
import com.andraganoid.verymuchtodo.model.TodoList

class TodoListAdapter(private val fragment: TodoListFragment) : ListAdapter<TodoList, TodoListAdapter.TodoListHolder>(
   TodoListCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListAdapter.TodoListHolder =
        TodoListHolder(TodoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TodoListAdapter.TodoListHolder, position: Int) = holder.bind(getItem(position))

    inner class TodoListHolder(private val binding: TodoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todoList: TodoList) {
            binding.todoItem = todoList
            binding.listEditIcon.setOnClickListener { fragment.openTodoItemEditor(todoList, false) }
            binding.listCheckDeleteIcon.setOnClickListener { fragment.deleteItem(todoList) }
            binding.root.setOnClickListener { fragment.checkItem(todoList) }
            binding.executePendingBindings()
        }
    }

    class TodoListCallback : DiffUtil.ItemCallback<TodoList>() {
        override fun areItemsTheSame(oldItem: TodoList, newItem: TodoList): Boolean {
            return oldItem.id == newItem.id;
        }

        override fun areContentsTheSame(oldItem: TodoList, newItem: TodoList): Boolean {
            return oldItem == newItem && oldItem.completed != newItem.completed
        }
    }
}
