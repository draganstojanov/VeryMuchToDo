package com.andraganoid.verymuchtodo.ui.tools

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.DropdownItemBinding


class CurrencyAdapter(private val currencyList: List<CurrencyModel>, private val onCurrencyCLick: ((CurrencyModel) -> Unit)?) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyAdapter.CurrencyHolder {
        val binding = DropdownItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyHolder(binding)
    }

    override fun getItemCount(): Int = currencyList.size

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) = holder.bind(currencyList[position])

    inner class CurrencyHolder(private val binding: DropdownItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CurrencyModel) {
            binding.dropdownText.text = item.code
            binding.root.setOnClickListener { onCurrencyCLick?.invoke(item) }
        }
    }
}

