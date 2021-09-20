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
import com.andraganoid.verymuchtodo.databinding.TodoListFragmentBinding
import com.andraganoid.verymuchtodo.main.MainViewModel
import com.andraganoid.verymuchtodo.model.TodoItem
import com.andraganoid.verymuchtodo.state.StackState
import com.andraganoid.verymuchtodo.util.areYouSure
import com.andraganoid.verymuchtodo.util.bottomToast
import com.andraganoid.verymuchtodo.util.hideKeyboard
import com.andraganoid.verymuchtodo.util.logB
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TodoListFragment : Fragment() {

    private var _binding: TodoListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by sharedViewModel()

    private lateinit var adapter: TodoListAdapter

    private var isNewItem = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TodoListFragmentBinding.inflate(inflater, container, false)
        setup()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setup() {

        val ssset = mutableSetOf<String>()

        ssset.add("BABA")
        ssset.add("deda")


        logB(ssset.filter { name -> name.contains("ab") })

        adapter = TodoListAdapter(this)
        binding.todoListRecView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSnapshotState().collect { tlState ->
                    when (tlState) {
                        is StackState.Stack -> {
                            viewModel.stack = tlState.stack
                            val tList =
                                tlState.stack.filter { tl -> tl!!.id == viewModel.selectedListId }
                            if (tList[0] != null) {
                                viewModel.listForEdit = tList[0]!!
                            }
                            adapter.itemList = viewModel.listForEdit.itemList
                            binding.clearItems.isVisible = viewModel.checkClearVisibilityList()
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
                "Content",
                "Description",
                this@TodoListFragment::closeTopModal,
                this@TodoListFragment::submitChanges
            )
            setHints("Content", "Description")
        }


        binding.createNewItem.setOnClickListener {
            if (!binding.topModal.isOpen()) {
                openTodoItemEditor(
                    TodoItem(
                        content = "",
                        description = "",
                        id = "ITEM-${System.currentTimeMillis()}"
                    ), true
                )
            }
        }

        binding.clearItems.setOnClickListener {
            viewModel.clearItemList()
        }
    }

    private fun closeTopModal() {
        hideKeyboard()
        binding.topModal.collapse()
    }

    private fun submitChanges() {
        closeTopModal()
        val content = binding.topModal.getInputValue1()
        if (content.isNotEmpty()) {
            viewModel.itemForEdit.apply {
                this.content = content
                description = binding.topModal.getInputValue2()
            }
            if (isNewItem) {
                viewModel.listForEdit.itemList.add(viewModel.itemForEdit)
            }
            viewModel.updateList()
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
                bottomToast("Only poster can delete uncompleted list")
            }
        }
    }

    fun openTodoItemEditor(ti: TodoItem, isNew: Boolean) {
        viewModel.itemForEdit = ti
        isNewItem = isNew
        with(binding.topModal) {
            setInputValues(ti.content.toString(), ti.description.toString())
            expand()
        }
    }


}