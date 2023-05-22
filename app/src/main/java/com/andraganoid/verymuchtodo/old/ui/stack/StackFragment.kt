package com.andraganoid.verymuchtodo.old.ui.stack

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.NameLayoutBinding
import com.andraganoid.verymuchtodo.databinding.StackEditorLayoutBinding
import com.andraganoid.verymuchtodo.databinding.StackFragmentBinding
import com.andraganoid.verymuchtodo.model.TodoStack
import com.andraganoid.verymuchtodo.model.isCompleted
import com.andraganoid.verymuchtodo.old.main.TodoViewModel
import com.andraganoid.verymuchtodo.old.util.areYouSure
import com.andraganoid.verymuchtodo.old.util.hideKeyboard
import com.andraganoid.verymuchtodo.old.util.invisibleToolbar
import com.andraganoid.verymuchtodo.old.util.main
import com.andraganoid.verymuchtodo.old.util.showKeyboard
import com.andraganoid.verymuchtodo.old.util.showMessage
import com.andraganoid.verymuchtodo.old.util.tm.TopModal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class StackFragment : Fragment() {

    private var _binding: StackFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoViewModel by activityViewModel()

    private lateinit var stackAdapter: StackAdapter
    private var isNewList = false

    private var nameTopModal: TopModal? = null
    private lateinit var nameBinding: NameLayoutBinding
    private lateinit var stackTopModal: TopModal
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

        stackBinding = StackEditorLayoutBinding.inflate(layoutInflater).also {
            it.cancelBtn.setOnClickListener { closeTodoListEditor() }
            it.saveBtn.setOnClickListener { submitChanges() }
        }
        stackTopModal = TopModal(parentView = binding.root, customView = stackBinding.root)

        stackAdapter = StackAdapter(this)
        binding.stacksRecView.apply {
            adapter = stackAdapter
            itemAnimator = null
        }
        binding.createNewList.setOnClickListener { openTodoListEditor(TodoStack(), true) }
        binding.clearList.setOnClickListener { viewModel.deleteMultipleList() }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.getSnapshotState().collect { tlState ->
//                    when (tlState) {
//                        is StackState.Stack -> {
//                            viewModel.stack = tlState.stack as ArrayList<TodoStack?>
//                            stackAdapter.submitList(viewModel.stack.sortedByDescending { it?.timestamp })
//                            binding.clearList.isVisible = viewModel.checkClearVisibilityStack()
//                        }
//                        is StackState.Error -> main.bottomToast(tlState.errorMsg)
//                        else -> {}
//                    }
//                }
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
        nameTopModal = TopModal(parentView = binding.root, customView = nameBinding.root)
        nameTopModal?.expand()
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

    fun openTodoListEditor(tl: TodoStack, isNew: Boolean) {
        viewModel.listForEdit = tl
        isNewList = isNew
        with(stackBinding) {
            titleInput.setText(tl.title.toString())
            descriptionInput.setText(tl.description.toString())
        }
        setFocus(tl.title.toString())
        stackTopModal.openIfClosed()
    }

    private fun setFocus(txt: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            showKeyboard()
            delay(200)
            stackBinding.titleInput.apply {
                requestFocusFromTouch()
                setSelection(txt.length)
            }
        }
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
        stackTopModal.collapse()
        hideKeyboard()
    }

    fun listSelected(id: String) {
        viewModel.selectedListId = id
        findNavController().navigate(R.id.todoListFragment)
    }

    fun deleteList(todoStack: TodoStack) {
        if (todoStack.isCompleted()) {
            areYouSure { viewModel.deleteList(todoStack) }
        } else {
            main.bottomToast(getString(R.string.only_completed_list))
        }
    }

}


