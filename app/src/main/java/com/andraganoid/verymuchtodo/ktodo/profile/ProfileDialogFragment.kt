package com.andraganoid.verymuchtodo.ktodo.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.andraganoid.verymuchtodo.databinding.FragmentProfileDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ProfileDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentProfileDialogBinding
    private val viewModel: ProfileViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileDialogBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.editDialog.observe(viewLifecycleOwner, Observer {
            if (!it) {
                dismiss()
            }
        })
    }

}

