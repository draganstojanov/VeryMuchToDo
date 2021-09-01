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
import com.andraganoid.verymuchtodo.shortVersion.model.TodoList
import com.andraganoid.verymuchtodo.shortVersion.state.StackState
import com.andraganoid.verymuchtodo.shortVersion.util.bottomToast
import com.andraganoid.verymuchtodo.shortVersion.util.logX
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StackFragment : Fragment() {

    private var _binding: StackFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by sharedViewModel()

    private var stack: ArrayList<TodoList?> = arrayListOf()

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
                            logX(2, tlState.stacks)
                            stack = tlState.stacks
                            adapter.stackList = stack
                        }
                        is StackState.Error -> bottomToast(tlState.errorMsg)
                        else -> {
                        }
                    }

                }
            }
        }

        //  binding.createNewList.setOnClickListener { testlist[0]?.let { it1 -> viewModel.updateListTest(it1) } }//TODO TEST

        binding.createNewList.setOnClickListener { openTodoListEditor(TodoList()) }

    }

    fun listSelected(id: String) {
        viewModel.selectedListId = id
        findNavController().navigate(R.id.todoListFragment)
    }

     fun openTodoListEditor(tl: TodoList) {
        StackEditDialog(tl).show(requireActivity().supportFragmentManager, StackEditDialog::class.simpleName)
    }
}


