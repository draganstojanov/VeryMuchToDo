package com.andraganoid.verymuchtodo.ktodo.stack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andraganoid.verymuchtodo.databinding.StacksFragmentBinding
import com.andraganoid.verymuchtodo.ktodo.TodoBaseFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel


class StacksFragment : TodoBaseFragment() {

    private lateinit var binding: StacksFragmentBinding
    private val viewModel: StacksViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=StacksFragmentBinding.inflate(inflater,container,false)
        binding.viewModel=viewModel
        binding.fragment=this
        return binding.root
    }


}
