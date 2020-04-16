package com.andraganoid.verymuchtodo.kauth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andraganoid.verymuchtodo.databinding.FragmentRegisterBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel


class RegisterFragment : BaseAuthFragment() {

    private val viewModel: AuthViewModel by sharedViewModel()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)//tod mozda izbaciti viewbinding
        binding.fragment = this
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }

}
