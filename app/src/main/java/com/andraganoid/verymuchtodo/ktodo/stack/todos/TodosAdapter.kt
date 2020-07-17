package com.andraganoid.verymuchtodo.ktodo.stack.todos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.TodosItemBinding
import com.andraganoid.verymuchtodo.kmodel.Todo

class TodosAdapter(private val fragment: TodosFragment) : RecyclerView.Adapter<TodosAdapter.TodosHolder>() {

    var todosList: List<Todo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosAdapter.TodosHolder {
        val binding = TodosItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodosHolder(binding)
    }

    override fun getItemCount(): Int = todosList.size

    override fun onBindViewHolder(holder: TodosAdapter.TodosHolder, position: Int) = holder.bind(todosList.get(position))

    inner class TodosHolder(private val binding: TodosItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.root.setOnClickListener { fragment.todoClicked(todo) }
            binding.root.setOnLongClickListener { fragment.todoEdit(todo) }
            binding.executePendingBindings()
        }
    }
}