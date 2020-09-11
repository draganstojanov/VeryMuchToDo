package com.andraganoid.verymuchtodo.ktodo.stack.stacks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.andraganoid.verymuchtodo.databinding.StacksFragmentBinding
import com.andraganoid.verymuchtodo.kmodel.Stack
import com.andraganoid.verymuchtodo.ktodo.TodoBaseFragment
import com.andraganoid.verymuchtodo.ktodo.stack.StacksViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.koin.android.viewmodel.ext.android.sharedViewModel

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class StacksFragment : TodoBaseFragment() {

    private lateinit var binding: StacksFragmentBinding
    private val viewModel: StacksViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = StacksFragmentBinding.inflate(inflater, container, false)
        //  binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = StacksAdapter(this);
        viewModel.allStacks.observe(viewLifecycleOwner, Observer { stacks -> adapter.stackList = stacks })
    }

    fun stackClicked(stack: Stack) {//TODO go to todos fragment
        toast(stack.title)
    }

    fun stackEdit(stack: Stack): Boolean {//TODO edit stack name
        toast(stack.title)
        return false
    }

    fun createNewStack() {//TODO new stack
        toast("new stack")
        val action = StacksFragmentDirections.actionStacksFragmentToStacksEditFragment()
        findNavController().navigate(action)
    }


}
