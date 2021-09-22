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
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.TodoListFragmentBinding
import com.andraganoid.verymuchtodo.main.MainViewModel
import com.andraganoid.verymuchtodo.model.TodoItem
import com.andraganoid.verymuchtodo.state.StackState
import com.andraganoid.verymuchtodo.util.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TodoListFragment : Fragment() {

    private var _binding: TodoListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by sharedViewModel()

    private lateinit var adapter: TodoListAdapter
    private var isNewItem = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = TodoListFragmentBinding.inflate(inflater, container, false)
        setup()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setup() {
        adapter = TodoListAdapter(this)
        binding.todoListRecView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSnapshotState().collect { tlState ->
                    when (tlState) {
                        is StackState.Stack -> {
                            viewModel.stack = tlState.stack
                            val tList = tlState.stack.firstOrNull { it?.id == viewModel.selectedListId }
                            if (tList != null) {
                                viewModel.listForEdit = tList
                            }
                            adapter.itemList = viewModel.listForEdit.itemList
                            binding.clearItems.isVisible = viewModel.checkClearVisibilityList()
                            main.showTitle(viewModel.listForEdit.title.toString())
                        }
                        is StackState.Error -> bottomToast(tlState.errorMsg)
                        else -> {
                        }
                    }
                }
            }
        }

        with(binding.topModal) {
            setupFields(
                getString(R.string.content),
                getString(R.string.description),
                this@TodoListFragment::closeTopModal,
                this@TodoListFragment::submitChanges
            )
            setHints(getString(R.string.content), getString(R.string.description))
        }

        binding.createNewItem.setOnClickListener { setNewItem() }

        binding.clearItems.setOnClickListener { viewModel.clearItemList() }
    }

    private fun closeTopModal() {
        hideKeyboard()
        binding.topModal.collapse()
    }

    private fun submitChanges() {
        val content = binding.topModal.getInputValue1()
        if (content.isNotEmpty()) {
            viewModel.updateItem(content, binding.topModal.getInputValue2(), isNewItem)
        }
        setNewItem()
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
                bottomToast(getString(R.string.only_owner))
            }
        }
    }

    private fun setNewItem() {
        openTodoItemEditor(TodoItem(), true)
    }

    fun openTodoItemEditor(ti: TodoItem, isNew: Boolean) {
        viewModel.itemForEdit = ti
        isNewItem = isNew
        with(binding.topModal) {
            setInputValues(ti.content.toString(), ti.description.toString())
            if (!isOpen()) {
                expand()
            }
            binding.input1.requestFocus()
            showKeyboard()
        }
    }


}