package com.andraganoid.verymuchtodo.ui.stack

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.andraganoid.verymuchtodo.databinding.NameLayoutBinding
import com.andraganoid.verymuchtodo.databinding.StackEditorLayoutBinding
import com.andraganoid.verymuchtodo.databinding.StackFragmentBinding
import com.andraganoid.verymuchtodo.main.MainViewModel
import com.andraganoid.verymuchtodo.model.TodoList
import com.andraganoid.verymuchtodo.model.state.StackState
import com.andraganoid.verymuchtodo.ui.custom.TopModalNEW
import com.andraganoid.verymuchtodo.util.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StackFragment : Fragment() {

    private var _binding: StackFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by sharedViewModel()

    private lateinit var stackAdapter: StackAdapter
    private var isNewList = false

    private var nameTopModal: TopModalNEW? = null
    private lateinit var nameBinding: NameLayoutBinding
    private var stackTopModal: TopModalNEW? = null
    private lateinit var stackBinding: StackEditorLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = StackFragmentBinding.inflate(inflater, container, false)
        setup()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setup() {
        viewModel.closeLoader()//TODO
        stackAdapter = StackAdapter(this)
        binding.stacksRecView.adapter = stackAdapter

        binding.createNewList.setOnClickListener {
            if (!binding.topModal.isOpen()) {
                openTodoListEditor(TodoList(), true)
            }
        }

        binding.clearList.setOnClickListener {
            viewModel.deleteMultipleList()
        }
    }

    private fun setObservers() {

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSnapshotState().collect { tlState ->
                    when (tlState) {
                        is StackState.Stack -> {
                            viewModel.stack = tlState.stack
                            stackAdapter.stackList = viewModel.stack
                            binding.clearList.isVisible = viewModel.checkClearVisibilityStack()
                        }
                        is StackState.Error -> main.bottomToast(tlState.errorMsg)
                    }
                }
            }
        }

        viewModel.userName.observe(viewLifecycleOwner) { userName ->
            if (userName == null) {
                Handler(Looper.getMainLooper()).postDelayed({
                    initNameTopModal()
                }, 1000)
            }
        }
    }

    private fun initNameTopModal() {

        invisibleToolbar(true)

        nameBinding = NameLayoutBinding.inflate(layoutInflater).also {
            it.nameInput.hint = getString(R.string.your_name)
            it.saveBtn.setOnClickListener { saveUserName() }
        }

        nameTopModal = TopModalNEW(
            parent = binding.root,
            customView = nameBinding.root
        )

    }

    private fun saveUserName() {
        if (nameBinding.nameInput.text.toString().isNotEmpty()) {
            viewModel.saveUserName(nameBinding.nameInput.text.toString())
            nameTopModal?.collapse()
            nameTopModal = null
            hideKeyboard()
            invisibleToolbar(false)
        } else {
            showMessage(getString(R.string.name_cant_be_empty))
        }
    }

    fun openTodoListEditor(tl: TodoList, isNew: Boolean) {
        viewModel.listForEdit = tl
        isNewList = isNew

        stackBinding = StackEditorLayoutBinding.inflate(layoutInflater).also {
            it.cancelBtn.setOnClickListener { closeTodoListEditor() }
            it.saveBtn.setOnClickListener { submitChanges() }
        }

        stackTopModal = TopModalNEW(
            parent = binding.root,
            customView = stackBinding.root
        )

    }

    private fun submitChanges() {
        val title = stackBinding.titleInput.text.toString()
        val desc = stackBinding.descriptionInput.text.toString()
        if (title.isNotEmpty()) {
            if (title != viewModel.listForEdit.title || desc != viewModel.itemForEdit.description) {
                viewModel.changeList(title, desc, isNewList)
                closeTodoListEditor()
            }
        } else {
            showMessage(getString(R.string.title_cant_be_empty))
        }
    }

    private fun closeTodoListEditor() {
        stackTopModal?.collapse()
        stackTopModal = null
        hideKeyboard()
    }

    fun listSelected(id: String) {
        viewModel.selectedListId = id
        findNavController().navigate(R.id.todoListFragment)
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


}


