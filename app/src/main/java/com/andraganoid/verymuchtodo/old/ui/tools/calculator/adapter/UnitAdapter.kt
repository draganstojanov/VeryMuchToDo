package com.andraganoid.verymuchtodo.old.ui.tools.calculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.DropdownItemBinding
import com.andraganoid.verymuchtodo.old.ui.tools.calculator.model.UnitModel

class UnitAdapter(private val unitList: List<UnitModel>, private val onUnitCLick: ((UnitModel) -> Unit)?) :
    RecyclerView.Adapter<UnitAdapter.UnitHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitHolder {
        val binding = DropdownItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UnitHolder(binding)
    }

    override fun getItemCount(): Int = unitList.size

    override fun onBindViewHolder(holder: UnitHolder, position: Int) = holder.bind(unitList[position])

    inner class UnitHolder(private val binding: DropdownItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UnitModel) {
            binding.dropdownText.text = item.code
            binding.root.setOnClickListener { onUnitCLick?.invoke(item) }
        }
    }
}
