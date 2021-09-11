package com.andraganoid.verymuchtodo.shortVersion.ui.stack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.StackFragmentBinding
import com.andraganoid.verymuchtodo.shortVersion.main.MainViewModel
import com.andraganoid.verymuchtodo.shortVersion.main.msgDialog.MessageDialogData
import com.andraganoid.verymuchtodo.shortVersion.model.TodoList
import com.andraganoid.verymuchtodo.shortVersion.state.StackState
import com.andraganoid.verymuchtodo.shortVersion.util.bottomToast
import com.andraganoid.verymuchtodo.shortVersion.util.hideKeyboard
import com.andraganoid.verymuchtodo.shortVersion.util.messageDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StackFragment : Fragment() {

    private var _binding: StackFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by sharedViewModel()

    private var isNewList = false
    private var listForEdit: TodoList = TodoList()

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
        val adapter = StackAdapter(this)
        binding.stacksRecView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSnapshotState().collect { tlState ->
                    when (tlState) {
                        is StackState.Stack -> {
                            viewModel.stack = tlState.stack
                            adapter.stackList = viewModel.stack
                        }
                        is StackState.Error -> bottomToast(tlState.errorMsg)
                        else -> {
                        }
                    }
                }
            }
        }

        binding.topModal.setupFields(
            "Title",
            "Description",
            this::closeTopModal,
            this::submitChanges
        )

        binding.createNewList.setOnClickListener {
            if (!binding.topModal.isOpen()) {
                openTodoListEditor(TodoList(title = "", description = "", id = "id-${System.currentTimeMillis()}"), true)//todo
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
        closeTopModal()

        listForEdit.apply {
            title = binding.topModal.getInputValue1()
            description = binding.topModal.getInputValue2()
        }

        if (isNewList) {
            viewModel.addList(listForEdit)
        } else {
            viewModel.updateList(listForEdit)
        }

    }

    fun listSelected(id: String) {
        viewModel.selectedListId = id
        findNavController().navigate(R.id.todoListFragment)
    }

    fun openTodoListEditor(tl: TodoList, isNew: Boolean) {
        listForEdit = tl
        binding.topModal.setInputValues(tl.title.toString(), tl.description.toString())
        binding.topModal.expand()
        isNewList = isNew
    }

    fun deleteList(todoList: TodoList) {
        val data = MessageDialogData(
            title = "Are you sure?",
            //   desc = resources.getText(R.string.set_widget).toString(),
            btnLeftText = "Cancel",
            btnLeft = {},
            //   btnLeft = this::showHelp,
            btnRightText = "OK",
            btnRight = { viewModel.deleteList(todoList) })
        messageDialog(data)
    }

}


