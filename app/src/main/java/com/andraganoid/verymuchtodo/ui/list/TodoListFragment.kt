package com.andraganoid.verymuchtodo.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.ItemEditorLayoutBinding
import com.andraganoid.verymuchtodo.databinding.StackEditorLayoutBinding
import com.andraganoid.verymuchtodo.databinding.TodoListFragmentBinding
import com.andraganoid.verymuchtodo.main.MainViewModel
import com.andraganoid.verymuchtodo.model.TodoItem
import com.andraganoid.verymuchtodo.model.state.StackState
import com.andraganoid.verymuchtodo.ui.custom.TopModalNEW
import com.andraganoid.verymuchtodo.util.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TodoListFragment : Fragment() {

    private var _binding: TodoListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by sharedViewModel()

    private lateinit var adapter: TodoListAdapter
    private var isNewItem = false

    private var itemTopModal: TopModalNEW? = null
    private lateinit var itemBinding: ItemEditorLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = TodoListFragmentBinding.inflate(inflater, container, false)
        setup()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
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
        adapter = TodoListAdapter(this)
        binding.todoListRecView.adapter = adapter
        binding.createNewItem.setOnClickListener { setNewItem() }
        binding.clearItems.setOnClickListener { viewModel.clearItemList() }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSnapshotState().collect { tlState ->
                    when (tlState) {
                        is StackState.Stack -> {
                            viewModel.stack = tlState.stack
                            val tList = tlState.stack.firstOrNull { it?.id == viewModel.selectedListId }
                            if (tList != null) {
                                viewModel.listForEdit = tList
                                if (tList.itemList.isNotEmpty()) {
                                    adapter.itemList = viewModel.listForEdit.itemList
                                } else {
                                    if (adapter.itemList.isNullOrEmpty()) {
                                        adapter.itemList = arrayListOf()
                                    } else {
                                        findNavController().popBackStack()
                                    }
                                }
                            }
                            binding.clearItems.isVisible = viewModel.checkClearVisibilityList()
                            main.showTitle(viewModel.listForEdit.title.toString())
                        }
                        is StackState.Error -> main.bottomToast(tlState.errorMsg)
                    }
                }
            }
        }

        viewModel.autocompleteItemList.observe(viewLifecycleOwner) {
            binding.topModal.setAutocompleteAdapter(it)
        }
    }



    fun checkItem(todoItem: TodoItem) {
        todoItem.completed = !todoItem.completed
        viewModel.updateList()
    }

    fun deleteItem(ti: TodoItem) {
        if (!ti.completed) {
            if (ti.userName.equals(viewModel.userName.value)) {
                areYouSure { viewModel.deleteItem(ti) }
            } else {
                main.bottomToast(getString(R.string.only_owner_item))
            }
        }
    }

    private fun setNewItem() {
        openTodoItemEditor(TodoItem(), true)
    }

    fun openTodoItemEditor(ti: TodoItem, isNew: Boolean) {
        viewModel.itemForEdit = ti
        isNewItem = isNew


        itemBinding = ItemEditorLayoutBinding.inflate(layoutInflater).also {
            it.cancelBtn.setOnClickListener { closeItemEditor() }
            it.saveBtn.setOnClickListener { submitChanges() }
        }

        itemTopModal = TopModalNEW(
            parent = binding.root,
            customView = itemBinding.root
        )

    }
    private fun closeItemEditor() {
        itemTopModal?.collapse()
        itemTopModal = null
        hideKeyboard()
    }

    private fun submitChanges() {
        val content = itemBinding.contentInput.text.toString()
        val desc = itemBinding.descriptionInput.text.toString()

        if (content.isNotEmpty()) {
            if (content != viewModel.itemForEdit.content || desc != viewModel.itemForEdit.description) {
                viewModel.updateItem(content, desc, isNewItem)
                if (isNewItem) {
                    setNewItem()
                } else {
                    closeItemEditor()
                }
            }
        }else {
            showMessage(getString(R.string.content_cant_be_empty))
        }
    }



}