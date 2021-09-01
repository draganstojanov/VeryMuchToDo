package com.andraganoid.verymuchtodo.shortVersion.ui.stack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andraganoid.verymuchtodo.databinding.StackEditDialogBinding
import com.andraganoid.verymuchtodo.shortVersion.main.MainViewModel
import com.andraganoid.verymuchtodo.shortVersion.model.TodoList
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StackEditDialog(private val todoList: TodoList?) : BottomSheetDialogFragment() {

    private lateinit var binding: StackEditDialogBinding
    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = StackEditDialogBinding.inflate(inflater, container, false)
        setup()
        return binding.root
    }

    private fun setup() {
        binding.todoList=todoList
    }


}