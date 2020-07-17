package com.andraganoid.verymuchtodo.ktodo.stack.stacks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.StacksItemBinding
import com.andraganoid.verymuchtodo.kmodel.Stack

class StacksAdapter(private val fragment: StacksFragment) : RecyclerView.Adapter<StacksAdapter.StackHolder>() {


    var stackList: List<Stack> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StackHolder {
        val binding = StacksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StackHolder(binding)
    }

    override fun getItemCount(): Int = stackList.size

    override fun onBindViewHolder(holder: StackHolder, position: Int) = holder.bind(stackList.get(position))

    inner class StackHolder(private val binding: StacksItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stack: Stack) {
            binding.stack = stack
            binding.root.setOnClickListener { fragment.stackClicked(stack) }
            binding.root.setOnLongClickListener { fragment.stackEdit(stack) }
            binding.executePendingBindings()
        }
    }
}




