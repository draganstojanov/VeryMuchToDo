package com.andraganoid.verymuchtodo.ktodo.profile

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
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
    //  private lateinit var profileDialogFragment: ProfileDialogFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setObservers()
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // profileUserImageIv.load("https://ignette.wikia.nocookie.net/disney/images/d/d5/Donaldstar_1600.jpg/revision/latest/scale-to-width-down/516?cb=20100629001545") {
//        profileUserImageIv.load(viewModel.user.photoUrlString) {
//            placeholder(R.drawable.ic_profile_img_placeholder)
//            error(R.drawable.ic_profile_img_placeholder)
//            transformations(CircleCropTransformation())
//        }
    }

    override fun onPause() {
        super.onPause()

        viewModel._editDialog.value = false
        viewModel._getImage.value = 0


    }

    private fun showDialog() {
        hideKeyboard()
        val profileDialogFragment = ProfileDialogFragment()
        profileDialogFragment.show(todo.supportFragmentManager, null)
    }

    private fun setObservers() {
        viewModel.editDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                showDialog()
            }
        })
        viewModel.loaderState.observe(viewLifecycleOwner, Observer { loaderState(it) })
        viewModel.getImage.observe(viewLifecycleOwner, Observer {
            when (it) {
                IMAGE_FROM_CAMERA -> fromCamera()
                IMAGE_FROM_GALERY -> fromGalery()
            }
        })
    }


    fun editButtonClicked(view: View) {

        viewModel.dialogYesLabel = R.string.yes
        viewModel.dialogNoLabel = R.string.no

        when (view.id) {
            R.id.profileImgEdit -> {
                viewModel.dialogMessage = R.string.edit_pic_from
                viewModel.dialogType = DialogType.IMAGE_UPDATE
                viewModel.dialogYesLabel = R.string.camera
                viewModel.dialogNoLabel = R.string.galery
                viewModel.dialogCancelLabel = R.string.cancel
            }
            R.id.profileNameEdit -> {
                viewModel.dialogMessage = R.string.edit_name
                viewModel.dialogType = DialogType.NAME_UPDATE
            }
            R.id.profileMailEdit -> {
                viewModel.dialogMessage = R.string.edit_mail
                viewModel.dialogType = DialogType.EMAIL_UPDATE
            }
            R.id.profilePassEdit -> {
                viewModel.dialogMessage = R.string.edit_pass
                viewModel.dialogType = DialogType.PASS_UPDATE
            }
        }
        showDialog()
    }


//todo prebaci u settings


    fun fromCamera() {

        runWithPermissions(Manifest.permission.CAMERA) {
            cameraView.isVisible=true
            profileView.isVisible=false
            camera.apply {
                setLifecycleOwner(viewLifecycleOwner)

                addCameraListener(object : CameraListener() {
                    override fun onPictureTaken(result: PictureResult) {
                        cameraView.isVisible=false
                        profileView.isVisible=true
                        Toast.makeText(activity, "CAMERA", Toast.LENGTH_LONG).show()

                        // Picture was taken!
                        // If planning to show a Bitmap, we will take care of
                        // EXIF rotation and background threading for you...
                        //  result.toBitmap(maxWidth, maxHeight, callback)

                        // If planning to save a file on a background thread,
                        // just use toFile. Ensure you have permissions.
                        //    result.toFile(file, callback)

                        // Access the raw data if needed.
                        val data = result.data

                        viewModel.uploadImage(result.data, ".jpg")

                    }
                })
            }
        }

    }

    fun takePicture() {
        camera.takePicture()
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

                // Return file Extension

                // Return file Extension
                val extension = "." + mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imgUri))

                Log.d("IIMMAAGGEE-1", imgUri.toString())
                Log.d("IIMMAAGGEE-2", extension)

//                Log.d("IIMMAAGGEE-1", imgUri.toString())
//                var imgPath: String = imgUri?.path.toString()
//                Log.d("IIMMAAGGEE-2", imgPath.toString())
//                var file = Uri.fromFile(File(imgPath))
//                Log.d("IIMMAAGGEE-3", file.toString())
                //   val  imageFile =  File(getRealPathFromURI(imgUri));
                //  Log.d("IIMMAAGGEE-4", imageFile.toString())

                viewModel.uploadImage(imgUri, extension)
            }
        }
    }

//    private fun getRealPathFromURI(contentURI: Uri): String? {
//        val cursor: Cursor? = main.getContentResolver().query(contentURI, null, null, null, null)
//        return if (cursor == null) { // Source is Dropbox or other similar local file path
//            contentURI.path
//        } else {
//            cursor.moveToFirst()
//            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
//            cursor.getString(idx)
//        }
//    }


//todo prebaci u settings
}