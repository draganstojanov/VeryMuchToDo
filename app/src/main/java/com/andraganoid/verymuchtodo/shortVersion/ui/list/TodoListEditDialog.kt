package com.andraganoid.verymuchtodo.shortVersion.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andraganoid.verymuchtodo.databinding.TodoListEditDialogBinding
import com.andraganoid.verymuchtodo.shortVersion.main.MainViewModel
import com.andraganoid.verymuchtodo.shortVersion.model.TodoItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TodoListEditDialog(private val todoItem: TodoItem?) : BottomSheetDialogFragment() {

    private lateinit var binding: TodoListEditDialogBinding
    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = TodoListEditDialogBinding.inflate(inflater, container, false)
        setup()
        return binding.root
    }

    private fun setup() {
        binding.todoItem = todoItem
    }


}