package com.andraganoid.verymuchtodo.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.TodoListItemBinding
import com.andraganoid.verymuchtodo.model.TodoItem

class TodoListAdapter(private val fragment: TodoListFragment) : RecyclerView.Adapter<TodoListAdapter.TodoListHolder>() {

    var itemList: List<TodoItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListAdapter.TodoListHolder {
        val binding = TodoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoListHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: TodoListHolder, position: Int) = holder.bind(itemList[position])

    inner class TodoListHolder(private val binding: TodoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todoItem: TodoItem) {
            binding.todoItem = todoItem
            binding.todoItemEdit.setOnClickListener { fragment.openTodoItemEditor(todoItem, false) }
            binding.todoItemDelete.setOnClickListener { fragment.deleteItem(todoItem) }
            binding.checkItem.setOnClickListener { fragment.checkItem(todoItem) }
            binding.executePendingBindings()
        }
    }
}