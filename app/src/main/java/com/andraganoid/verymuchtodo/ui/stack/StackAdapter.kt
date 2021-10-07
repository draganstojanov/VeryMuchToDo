package com.andraganoid.verymuchtodo.ui.stack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.StackItemBinding
import com.andraganoid.verymuchtodo.model.TodoList

class StackAdapter(private val fragment: StackFragment) : RecyclerView.Adapter<StackAdapter.StackHolder>() {

    var stackList: List<TodoList?> = emptyList()
        set(value) {
            field = value.sortedByDescending { it?.timestamp }
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
            binding.todoList = todoList
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
}