package com.andraganoid.verymuchtodo.ktodo.stack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.ktodo.TodoBaseFragment

class StacksFragment : TodoBaseFragment() {


    private lateinit var viewModel: StacksViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.stacks_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StacksViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
