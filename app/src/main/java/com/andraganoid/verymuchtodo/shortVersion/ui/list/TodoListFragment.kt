package com.andraganoid.verymuchtodo.shortVersion.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andraganoid.verymuchtodo.databinding.FragmentTodosBinding
import com.andraganoid.verymuchtodo.databinding.StackFragmentBinding
import com.andraganoid.verymuchtodo.databinding.TodoListFragmentBinding
import com.andraganoid.verymuchtodo.kmodel.Todo
import com.andraganoid.verymuchtodo.ktodo.stack.StacksViewModel
import com.andraganoid.verymuchtodo.ktodo.stack.todos.TodosAdapter
import com.andraganoid.verymuchtodo.shortVersion.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TodoListFragment: Fragment() {

    private var _binding: TodoListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by sharedViewModel()

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
        TODO("Not yet implemented")
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