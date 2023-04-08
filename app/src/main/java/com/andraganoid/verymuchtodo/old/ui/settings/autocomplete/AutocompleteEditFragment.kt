package com.andraganoid.verymuchtodo.old.ui.settings.autocomplete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.andraganoid.verymuchtodo.databinding.AutocompleteEditFragmentBinding
import com.andraganoid.verymuchtodo.old.ui.settings.SettingsViewModel
import com.andraganoid.verymuchtodo.old.util.areYouSure
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class AutocompleteEditFragment : Fragment() {

    private var _binding: AutocompleteEditFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by activityViewModel()
    private lateinit var editAdapter: AutocompleteEditAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = AutocompleteEditFragmentBinding.inflate(inflater, container, false)
        setup()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkAutocompleteList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setup() {
        editAdapter = AutocompleteEditAdapter(this)
        binding.editRecView.apply {
            adapter = editAdapter
            itemAnimator = null
        }

        viewModel.autocompleteItemList.observe(viewLifecycleOwner) { editAdapter.setList(it) }

        binding.submitEditBtn.setOnClickListener {
            viewModel.submitChanges()
            it.isVisible = false
        }

        binding.deleteBtn.setOnClickListener {
            areYouSure {
                viewModel.deleteCheckedItems()
                it.isVisible = false
            }
        }
    }

    fun openEditor(editText: EditText, editItem: AutocompleteEditItem?) {
        viewModel.preEditedText = editItem?.text.toString()
        viewModel.changedText = ""
        editText.doOnTextChanged { txt, _, _, _ ->
            viewModel.changedText = txt.toString()
            binding.submitEditBtn.isVisible = viewModel.changedText != viewModel.preEditedText
        }
    }

    fun deleteCheckedItems(editList: ArrayList<AutocompleteEditItem?>) {
        var del = false
        for (item in editList){
            if (item?.checkForDeletion == true) {
                del = true
                break
            }
        }

        binding.deleteBtn.isVisible = del
        viewModel.deletionList = if (del) editList else arrayListOf()
    }
}