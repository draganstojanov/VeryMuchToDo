package com.andraganoid.verymuchtodo.shortVersion.ui.stack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.andraganoid.verymuchtodo.databinding.StackFragmentBinding
import com.andraganoid.verymuchtodo.ktodo.stack.stacks.StacksAdapter
import com.andraganoid.verymuchtodo.shortVersion.main.MainViewModel
import com.andraganoid.verymuchtodo.shortVersion.state.StackState
import com.andraganoid.verymuchtodo.shortVersion.util.bottomToast
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StackFragment : Fragment() {

    private var _binding: StackFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by sharedViewModel()

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
        binding.stacksRecView.adapter=adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSnapshotState().collect { tlState ->
                    when (tlState) {
                        is StackState.Stack -> adapter.stackList=tlState.stacks
                        is StackState.Error -> bottomToast(tlState.errorMsg)
                        else -> {
                        }
                    }

                }
            }
        }
    }
}