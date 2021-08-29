package com.andraganoid.verymuchtodo.ktodo.stack.todos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andraganoid.verymuchtodo.databinding.FragmentTodosBinding
import com.andraganoid.verymuchtodo.kmodel.Todo
import com.andraganoid.verymuchtodo.ktodo.TodoBaseFragment
import com.andraganoid.verymuchtodo.ktodo.stack.StacksViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class TodosFragment : TodoBaseFragment() {


    private lateinit var binding: FragmentTodosBinding
    private val viewModel: StacksViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTodosBinding.inflate(inflater, container, false)
        //  binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TodosAdapter(this);
      //  viewModel.allStacks.observe(viewLifecycleOwner, Observer { stacks -> adapter.stackList = stacks })
    }

    fun todoClicked(todo: Todo) {//TODO go to todos fragment
        toast(todo.content)
    }

    fun todoEdit(todo:Todo): Boolean {//TODO edit stack name
        toast(todo.content)
        return false
    }

    fun createNewTodo() {//TODO new todo
        toast("new todo")
    }

}