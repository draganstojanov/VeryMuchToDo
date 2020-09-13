package com.andraganoid.verymuchtodo.ktodo.profile

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.ProfileFragmentBinding
import com.andraganoid.verymuchtodo.ktodo.TodoBaseFragment
import com.andraganoid.verymuchtodo.util.IMAGE_FROM_CAMERA
import com.andraganoid.verymuchtodo.util.IMAGE_FROM_GALERY
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import kotlinx.android.synthetic.main.profile_fragment.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class ProfileFragment : TodoBaseFragment() {

    private val viewModel: ProfileViewModel by sharedViewModel()
    private lateinit var binding: ProfileFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setObservers()
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }


    private fun showDialog() {
        hideKeyboard()
        val profileDialogFragment = ProfileDialogFragment()
        profileDialogFragment.show(todo.supportFragmentManager, null)
    }

    private fun setObservers() {
        viewModel._editDialog.value = false
        viewModel._getImage.value = 0
        viewModel.editDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                showDialog()
            }
        })
        viewModel.loaderVisibility.observe(viewLifecycleOwner, Observer {
            loaderVisibility(it)
        })
        viewModel.getImage.observe(viewLifecycleOwner, Observer {
            when (it) {
                IMAGE_FROM_CAMERA -> fromCamera()
                IMAGE_FROM_GALERY -> fromGalery()
            }
        })
    }


    fun editButtonClicked(view: View) {
        viewModel.apply {
            dialogYesLabel = R.string.yes
            dialogNoLabel = R.string.no
        }
        when (view.id) {
            R.id.profileImgEdit -> {
                viewModel.apply {
                    dialogMessage = R.string.edit_pic_from
                    dialogType = DialogType.IMAGE_UPDATE
                    dialogYesLabel = R.string.camera
                    dialogNoLabel = R.string.galery
                    dialogCancelLabel = R.string.cancel
                }
            }
            R.id.profileNameEdit -> {
                viewModel.apply {
                    dialogMessage = R.string.edit_name
                    dialogType = DialogType.NAME_UPDATE
                }
            }
            R.id.profileMailEdit -> {
                viewModel.apply {
                    dialogMessage = R.string.edit_mail
                    dialogType = DialogType.EMAIL_UPDATE
                }
            }
            R.id.profilePassEdit -> {
                viewModel.apply {
                    dialogMessage = R.string.edit_pass
                    dialogType = DialogType.PASS_UPDATE
                }
            }
        }
        viewModel._editDialog.value = true
    }


    fun fromCamera() {

        runWithPermissions(Manifest.permission.CAMERA) {
            cameraView.isVisible = true
            profileView.isVisible = false
            camera.apply {
                setLifecycleOwner(viewLifecycleOwner)
                addCameraListener(object : CameraListener() {
                    override fun onPictureTaken(result: PictureResult) {
                        closeCamera()
                        viewModel.uploadImage(result.data, ".jpg")
                    }
                })
            }
        }
    }

    fun takePicture() {
        camera.takePicture()
    }

    fun closeCamera() {
        cameraView.isVisible = false
        profileView.isVisible = true
        camera.close()
    }

    private fun fromGalery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_FROM_GALERY)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_FROM_GALERY) {
                val imgUri: Uri = data?.data!!
                val contentResolver: ContentResolver = todo.getContentResolver()
                val mimeTypeMap = MimeTypeMap.getSingleton()
                val extension = "." + mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imgUri))
                viewModel.uploadImage(imgUri, extension)
            }
        }
    }

    public fun togglePasswordVisibility() {
        viewModel.passIsVisible.set(!viewModel.passIsVisible.get())
    }

}