package com.andraganoid.verymuchtodo.old.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.ItemEditorLayoutBinding
import com.andraganoid.verymuchtodo.databinding.TodoListFragmentBinding
import com.andraganoid.verymuchtodo.model.TodoList
import com.andraganoid.verymuchtodo.model.state.StackState
import com.andraganoid.verymuchtodo.old.main.TodoViewModel
import com.andraganoid.verymuchtodo.old.util.areYouSure
import com.andraganoid.verymuchtodo.old.util.hideKeyboard
import com.andraganoid.verymuchtodo.old.util.main
import com.andraganoid.verymuchtodo.old.util.showKeyboard
import com.andraganoid.verymuchtodo.old.util.showMessage
import com.andraganoid.verymuchtodo.old.util.tm.TopModal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class TodoListFragment : Fragment() {

    private var _binding: TodoListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoViewModel by activityViewModel()

    private lateinit var todoAdapter: TodoListAdapter
    private var isNewItem = false

    private lateinit var itemTopModal: TopModal
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
        itemBinding = ItemEditorLayoutBinding.inflate(layoutInflater).also {
            it.cancelBtn.setOnClickListener { closeItemEditor() }
            it.saveBtn.setOnClickListener { submitChanges() }
        }
        itemTopModal = TopModal(parentView = binding.root, customView = itemBinding.root)
        todoAdapter = TodoListAdapter(this)
        binding.todoListRecView.apply {
            adapter = todoAdapter
            itemAnimator = null
        }
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
                                todoAdapter.submitList(viewModel.getSortedItemList())
                            }
                            binding.clearItems.isVisible = viewModel.checkClearVisibilityList()
                            main.showTitle(viewModel.listForEdit.title.toString())
                        }
                        is StackState.Error -> main.bottomToast(tlState.errorMsg)
                        else -> {}
                    }
                }
            }
        }

        viewModel.autocompleteItemList.observe(viewLifecycleOwner) { setAutocompleteAdapter(it) }
    }

    private fun setAutocompleteAdapter(autocompleteItemList: MutableList<String>) {
        itemBinding.let {
            val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.autocomplete_item, R.id.autocompleteTv, autocompleteItemList)
            itemBinding.contentInput.apply {
                threshold = 1
                setAdapter(adapter)
            }
        }
    }

    fun checkItem(todoList: TodoList) {
        todoList.completed = !todoList.completed
        viewModel.updateList()
    }

    fun deleteItem(ti: TodoList) {
        if (!ti.completed) {
            if (ti.userName.equals(viewModel.userName.value)) {
                areYouSure { viewModel.deleteItem(ti) }
            } else {
                main.bottomToast(getString(R.string.only_owner_item))
            }
        }
    }

    private fun setNewItem() {
        openTodoItemEditor(TodoList(), true)
    }

    fun openTodoItemEditor(ti: TodoList, isNew: Boolean) {
        viewModel.itemForEdit = ti
        isNewItem = isNew

        with(itemBinding) {
            contentInput.setText(ti.content.toString())
            descriptionInput.setText(ti.description.toString())
        }
        setFocus(ti.content.toString())

        itemTopModal.openIfClosed()

    }

    private fun setFocus(txt: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            showKeyboard()
            delay(200)
            itemBinding.contentInput.apply {
                requestFocusFromTouch()
                setSelection(txt.length)
            }
        }
    }

    private fun closeItemEditor() {
        itemTopModal.collapse()
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
        } else {
            showMessage(getString(R.string.content_cant_be_empty))
        }
    }

}