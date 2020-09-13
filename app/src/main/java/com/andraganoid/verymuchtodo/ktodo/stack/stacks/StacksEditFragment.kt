package com.andraganoid.verymuchtodo.ktodo.stack.stacks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.ktodo.TodoBaseFragment

class StacksEditFragment :TodoBaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {



        hideBottomBar()
        return inflater.inflate(R.layout.fragment_stacks_edit, container, false)
    }


}