package com.andraganoid.verymuchtodo.old.ui.settings.autocomplete

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andraganoid.verymuchtodo.databinding.AutocompleteEditItemBinding

class AutocompleteEditAdapter(private val fragment: AutocompleteEditFragment) : RecyclerView.Adapter<AutocompleteEditAdapter.EditHolder>() {
    private lateinit var editList: ArrayList<AutocompleteEditItem?>

    fun setList(eList: MutableList<String>) {
        editList = arrayListOf()
        eList.sortedBy { it }.forEach { text ->
            editList.add(AutocompleteEditItem(text = text))
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditHolder {
        val binding = AutocompleteEditItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EditHolder(binding)
    }

    override fun getItemCount(): Int = editList.size

    override fun onBindViewHolder(holder: EditHolder, position: Int) = holder.bind(editList[position])

    inner class EditHolder(private val binding: AutocompleteEditItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AutocompleteEditItem?) {

            binding.editItem = item
            binding.editText.setText(item?.text)
            if (item?.editState == true) {
                fragment.openEditor(binding.editText, item)
                binding.editText.apply {
                    requestFocusFromTouch()
                    setSelection(item.text.length)
                }

            }
            binding.editIcon.setOnClickListener {
                item?.editState = !item?.editState!!
                notifyItemChanged(adapterPosition)
            }

            binding.checkDeleteIcon.setOnClickListener {
                item?.checkForDeletion = !item?.checkForDeletion!!
                notifyItemChanged(adapterPosition)
                fragment.deleteCheckedItems(editList)
            }

            binding.executePendingBindings()
        }
    }
}
