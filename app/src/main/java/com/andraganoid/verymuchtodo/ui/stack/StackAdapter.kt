package com.andraganoid.verymuchtodo.ui.stack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.StackItemBinding
import com.andraganoid.verymuchtodo.model.TodoList

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
            binding.todoList = todoList
            binding.root.setOnClickListener { fragment.listSelected(todoList!!.id) }
            binding.stackEdit.setOnClickListener {
                if (todoList != null) {
                    fragment.openTodoListEditor(todoList,false)
                }
            }
            binding.stackDelete.setOnClickListener {
                if (todoList != null) {
                    fragment.deleteList(todoList)
                }
            }
            binding.executePendingBindings()
        }
    }
}