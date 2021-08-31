package com.andraganoid.verymuchtodo.shortVersion.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.andraganoid.verymuchtodo.databinding.TodoListFragmentBinding
import com.andraganoid.verymuchtodo.shortVersion.main.MainViewModel
import com.andraganoid.verymuchtodo.shortVersion.state.StackState
import com.andraganoid.verymuchtodo.shortVersion.util.bottomToast
import com.andraganoid.verymuchtodo.shortVersion.util.logC
import com.andraganoid.verymuchtodo.shortVersion.util.logX
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TodoListFragment : Fragment() {

    private var _binding: TodoListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by sharedViewModel()

    private lateinit var adapter: TodoListAdapter

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
                            logX(3, tlState.stacks)

                            val tList=tlState.stacks.filter { tl->tl!!.id==viewModel.selectedListId }

                            logC(tList[0]!!.todoList)


                            adapter.todoList=tList[0]!!.todoList

//                            testlist = tlState.stacks
//                            adapter.stackList = tlState.stacks
                        }
                        is StackState.Error -> bottomToast(tlState.errorMsg)
                        else -> {
                        }
                    }

                }
            }

        }
    }


//    fun todoClicked(todo: Todo) {//TODO go to todos fragment
//        toast(todo.content)
//    }
//
//    fun todoEdit(todo: Todo): Boolean {//TODO edit stack name
//        toast(todo.content)
//        return false
//    }
//
//    fun createNewTodo() {//TODO new todo
//        toast("new todo")
//    }
}