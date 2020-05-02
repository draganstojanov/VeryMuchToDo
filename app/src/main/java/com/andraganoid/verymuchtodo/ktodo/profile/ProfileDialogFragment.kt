package com.andraganoid.verymuchtodo.ktodo.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.FragmentProfileDialogBinding
import com.andraganoid.verymuchtodo.util.IMAGE_FROM_CAMERA
import com.andraganoid.verymuchtodo.util.IMAGE_FROM_GALERY
import com.andraganoid.verymuchtodo.util.isValidDisplayName
import com.andraganoid.verymuchtodo.util.isValidEmail
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ProfileDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentProfileDialogBinding
    private val viewModel: ProfileViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileDialogBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.fragment = this
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

    fun yesBtn() {
        dismiss()
        when (viewModel.dialogType) {
            DialogType.NAME_UPDATE -> {
                if (viewModel.userName.get()!!.isValidDisplayName() && !viewModel.userName.get().equals(viewModel.user.name)) {
                    viewModel.updateUserName()
                } else {
                    viewModel.setMessage(R.string.name_unchanged)
                }
            }
            DialogType.EMAIL_UPDATE -> {
                if (viewModel.userMail.get()!!.isValidEmail() && !viewModel.userMail.get().equals(viewModel.user.email)) {
                    viewModel.updateEmail()
                } else {
                    viewModel.setMessage(R.string.mail_unchanged)
                }
            }
            DialogType.PASS_UPDATE -> {
                viewModel.updatePassword()
            }
            DialogType.IMAGE_UPDATE -> {
                viewModel._getImage.value = IMAGE_FROM_CAMERA
            }
        }
    }

    fun noBtn() {
        if (viewModel.dialogType == DialogType.IMAGE_UPDATE) {
            viewModel._getImage.value = IMAGE_FROM_GALERY
        }
        dismiss()
    }

    fun cancelBtn() {
        dismiss()
    }

}

