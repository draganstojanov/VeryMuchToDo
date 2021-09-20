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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StackFragmentBinding.inflate(inflater, container, false)
        setup()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setup() {
        main.setTitle("TODO")
        viewModel.closeLoader()

        viewModel.userName.observe(viewLifecycleOwner, { userName ->
            viewModel.closeLoader()
            logD(0)
            if (userName == null) {
                lifecycleScope.launch(Dispatchers.Main) {
                    delay(1000)
                    with(binding.topModal) {
                        setupFields(
                            "Your name",
                            null,
                            null,
                            this@StackFragment::saveUsername
                        )
                        setHints("Your name", null)
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
                        else -> {
                        }
                    }
                }
            }
        }

        binding.createNewList.setOnClickListener {
            if (!binding.topModal.isOpen()) {
                openTodoListEditor(
                    TodoList(
                        title = "",
                        description = "",
                        id = "LIST-${System.currentTimeMillis()}"
                    ), true
                )
            }
        }

        binding.clearList.setOnClickListener {
            viewModel.deleteMultipleList()
        }
    }

    private fun closeTopModal() {
        logD(2)
        hideKeyboard()
        binding.topModal.collapse()
    }

    private fun submitChanges() {
        closeTopModal()
        val title = binding.topModal.getInputValue1()
        if (title.isNotEmpty()) {
            viewModel.listForEdit.apply {
                this.title = title
                description = binding.topModal.getInputValue2()
            }
            if (isNewList) {
                viewModel.addList()
            }
            viewModel.updateList()
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
                "Title",
                "Description",
                this@StackFragment::closeTopModal,
                this@StackFragment::submitChanges
            )
            setHints("List title", "List description")
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
            bottomToast("Only poster can delete uncompleted list")
        }
    }


    private fun saveUsername() {
        logD(1)
        closeTopModal()
        viewModel.saveUserName(binding.topModal.getInputValue1())
    }

}


