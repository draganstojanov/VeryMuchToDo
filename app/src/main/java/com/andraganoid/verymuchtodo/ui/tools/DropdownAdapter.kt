package com.andraganoid.verymuchtodo.ui.tools

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.DropdownItemBinding


class DropdownAdapter(private val itemList: List<String>, private val onItemCLick: (String) -> Unit) :
    RecyclerView.Adapter<DropdownAdapter.DropdownHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DropdownAdapter.DropdownHolder {
        val binding = DropdownItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DropdownHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: DropdownHolder, position: Int) = holder.bind(itemList[position])

    inner class DropdownHolder(private val binding: DropdownItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.dropdownText.text = item
            binding.root.setOnClickListener { onItemCLick.invoke(item) }
            binding.
        }
    }
}

