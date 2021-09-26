package com.andraganoid.verymuchtodo.ui.stack

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
import com.andraganoid.verymuchtodo.databinding.StackFragmentBinding
import com.andraganoid.verymuchtodo.main.MainViewModel
import com.andraganoid.verymuchtodo.model.TodoList
import com.andraganoid.verymuchtodo.state.StackState
import com.andraganoid.verymuchtodo.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class StackFragment : Fragment() {

    private var _binding: StackFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by sharedViewModel()

    private var isNewList = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = StackFragmentBinding.inflate(inflater, container, false)
        setup()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setup() {
        main.showTitle(getString(R.string.todo))
        viewModel.closeLoader()

        viewModel.userName.observe(viewLifecycleOwner, { userName ->
            viewModel.closeLoader()
            if (userName == null) {
                lifecycleScope.launch(Dispatchers.Main) {
                    delay(1000)
                    with(binding.topModal) {
                        setupFields(
                            getString(R.string.your_name),
                            null,
                            null,
                            this@StackFragment::saveUsername
                        )
                        setHints(getString(R.string.your_name), null)
                        expand()
                    }
                }
            }
        })

        val adapter = StackAdapter(this)
        binding.stacksRecView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSnapshotState().collect { tlState ->
                    when (tlState) {
                        is StackState.Stack -> {
                            viewModel.stack = tlState.stack
                            adapter.stackList = viewModel.stack
                            binding.clearList.isVisible = viewModel.checkClearVisibilityStack()
                        }
                        is StackState.Error -> bottomToast(tlState.errorMsg)
                    }
                }
            }
        }

        binding.createNewList.setOnClickListener {
            if (!binding.topModal.isOpen()) {
                openTodoListEditor(TodoList(), true)
            }
        }

        binding.clearList.setOnClickListener {
            viewModel.deleteMultipleList()
        }
    }

    private fun closeTopModal() {
        hideKeyboard()
        binding.topModal.collapse()
    }

    private fun submitChanges() {
        val title = binding.topModal.getInputValue1()
        if (title.isNotEmpty() && title != viewModel.listForEdit.title) {
            viewModel.changeList(title, binding.topModal.getInputValue2(), isNewList)
            closeTopModal()
        }
    }

    fun listSelected(id: String) {
        viewModel.selectedListId = id
        findNavController().navigate(R.id.todoListFragment)
    }

    fun openTodoListEditor(tl: TodoList, isNew: Boolean) {
        viewModel.listForEdit = tl
        isNewList = isNew

        with(binding.topModal) {
            setupFields(
                getString(R.string.title),
                getString(R.string.description),
                this@StackFragment::closeTopModal,
                this@StackFragment::submitChanges
            )
            setHints(getString(R.string.title), getString(R.string.description))
            setInputValues(tl.title.toString(), tl.description.toString())
            isVisible = true
            expand()
        }
    }

    fun deleteList(todoList: TodoList) {
        if (todoList.completed) {
            areYouSure { viewModel.deleteList(todoList) }
        } else if (todoList.userName.equals(viewModel.userName.value)) {
            areYouSure { viewModel.deleteList(todoList) }
        } else {
            bottomToast(getString(R.string.only_owner))
        }
    }

    private fun saveUsername() {
        closeTopModal()
        viewModel.saveUserName(binding.topModal.getInputValue1())
    }

}


