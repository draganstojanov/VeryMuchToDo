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
import com.andraganoid.verymuchtodo.model.state.StackState
import com.andraganoid.verymuchtodo.util.areYouSure
import com.andraganoid.verymuchtodo.util.keyboardState
import com.andraganoid.verymuchtodo.util.main
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

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
        viewModel.closeLoader()
        viewModel.userName.observe(viewLifecycleOwner) { userName ->
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
        }

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
                        is StackState.Error -> main.bottomToast(tlState.errorMsg)
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
        keyboardState.tryEmit(false)
        binding.topModal.collapse()
    }

    private fun submitChanges() {
        val title = binding.topModal.getInputValue1()
        val desc = binding.topModal.getInputValue2()
        if (title.isNotEmpty()) {
            if (title != viewModel.listForEdit.title || desc != viewModel.itemForEdit.description) {
                viewModel.changeList(title, desc, isNewList)
                closeTopModal()
            }
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
            main.bottomToast(getString(R.string.only_owner_list))
        }
    }

    private fun saveUsername() {
        closeTopModal()
        viewModel.saveUserName(binding.topModal.getInputValue1())
    }

}


