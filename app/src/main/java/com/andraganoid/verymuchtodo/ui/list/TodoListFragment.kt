package com.andraganoid.verymuchtodo.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.andraganoid.verymuchtodo.databinding.TodoListFragmentBinding
import com.andraganoid.verymuchtodo.main.MainViewModel
import com.andraganoid.verymuchtodo.main.msgDialog.MessageDialogData
import com.andraganoid.verymuchtodo.model.TodoItem
import com.andraganoid.verymuchtodo.model.TodoList
import com.andraganoid.verymuchtodo.state.StackState
import com.andraganoid.verymuchtodo.util.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TodoListFragment : Fragment() {

    private var _binding: TodoListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by sharedViewModel()

    private lateinit var adapter: TodoListAdapter

    private var isNewItem = false
    private var itemForEdit: TodoItem = TodoItem()
    private var listForEdit: TodoList = TodoList()

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
                            val tList = tlState.stack.filter { tl -> tl!!.id == viewModel.selectedListId }
                            if (tList[0] != null) {
                                listForEdit = tList[0]!!
                            }
                            adapter.todoList = listForEdit.todoList
                        }
                        is StackState.Error -> bottomToast(tlState.errorMsg)
                        else -> {
                        }
                    }
                }
            }
        }


        binding.topModal.setupFields(
            "Content",
            "Description",
            this::closeTopModal,
            this::submitChanges
        )

        binding.topModal.setHints("Content", "Description")

        binding.createNewItem.setOnClickListener {
            if (!binding.topModal.isOpen()) {
                openTodoItemEditor(TodoItem(content = "", description = "", id = "id-${System.currentTimeMillis()}"), true)//todo
            }
        }

        binding.clearItems.setOnClickListener {
          if(!listForEdit.todoList.filter { ti-> ti.completed }.isNullOrEmpty()){}//todo
        }
    }

    private fun closeTopModal() {
        hideKeyboard()
        binding.topModal.collapse()
    }

    private fun submitChanges() {
        closeTopModal()

        itemForEdit.apply {
            content = binding.topModal.getInputValue1()
            description = binding.topModal.getInputValue2()
        }


        logA(itemForEdit)
        logB(listForEdit)
        logC(viewModel.stack)

        if (isNewItem) {
            listForEdit.todoList.add(itemForEdit)
        }
        viewModel.updateList(listForEdit)

    }

    fun checkItem(todoItem: TodoItem) {

        showLongToast(todoItem.completed)

        todoItem.completed = !todoItem.completed
        viewModel.updateList(listForEdit)
    }

    fun deleteItem(ti: TodoItem) {
        val data = MessageDialogData(
            title = "Are you sure?",
            //   desc = resources.getText(R.string.set_widget).toString(),
            btnLeftText = "Cancel",
            btnLeft = {},
            //   btnLeft = this::showHelp,
            btnRightText = "OK",
            btnRight = { itemDelete(ti) })
        messageDialog(data)
    }

    private fun itemDelete(ti: TodoItem) {
        listForEdit.todoList.remove(ti)
        viewModel.updateList(listForEdit)
    }

    fun openTodoItemEditor(ti: TodoItem, isNew: Boolean) {
        itemForEdit = ti
        binding.topModal.setInputValues(ti.content.toString(), ti.description.toString())
        binding.topModal.expand()
        isNewItem = isNew
    }


}