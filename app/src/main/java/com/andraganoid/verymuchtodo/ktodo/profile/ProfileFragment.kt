package com.andraganoid.verymuchtodo.ktodo.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.andraganoid.verymuchtodo.databinding.ProfileFragmentBinding
import com.andraganoid.verymuchtodo.ktodo.TodoBaseFragment
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

    private fun setObservers() {

        viewModel.editDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                showDialog()
            }
        })


//        viewModel.back.observe(viewLifecycleOwner, Observer { back -> back.let { main.onBackPressed() } })
//        viewModel.loaderState.observe(viewLifecycleOwner, Observer { loaderState(it) })
//        viewModel.message.observe(viewLifecycleOwner, Observer { message ->
//            if (message != null) {
//                showMessage(message)
//            }
//        })
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        registerConfirmPassEt.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                submitRegistration()
//                return@OnEditorActionListener true
//            }
//            false
//        })
//    }

//    fun submitRegistration() {//todo add image
//        val mail = registerMailEt.text.toString()
//        val name = registerNameEt.text.toString()
//        val pass = registerPassEt.text.toString()
//        val confirm = registerConfirmPassEt.text.toString()
//        val messageArray = arrayListOf<String>()
//
//        if (!mail.isValidEmail()) {
//            messageArray.add(getString(R.string.mail_not_valid))
//        }
//        if (!name.isValidDisplayName()) {
//            messageArray.add(getString(R.string.name_not_valid))
//        }
//        if (!pass.isValidPassword()) {
//            messageArray.add(getString(R.string.password_not_valid))
//        }
//        if (!confirm.isValidConfirmedPassword(pass)) {
//            messageArray.add(getString(R.string.confirm_not_valid))
//        }
//
//        if (messageArray.size > 0) {
//            viewModel.showMessage(messageArray)
//        } else {
//            viewModel.register(mail, pass, name)
//        }
//    }

    private fun showDialog() {
        val setttingsDialogFragment = ProfileDialogFragment();
        setttingsDialogFragment.show(todo.supportFragmentManager, null)
    }


//todo prebaci u settings

//    private val IMAGE_GALERY = 101
//    private val IMAGE_CAMERA = 102
//
//    fun profileImage() {
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_GALERY)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == IMAGE_GALERY) {
//                val imgUri: Uri = data?.data!!
//
//                Log.d("IIMMAAGGEE-1", imgUri.toString())
//
//                var imgPath: String = imgUri?.path.toString()
//
//                Log.d("IIMMAAGGEE-2", imgPath.toString())
//
//                var file = Uri.fromFile(File(imgPath))
//
//                Log.d("IIMMAAGGEE-3", file.toString())
//
//
//                //   val  imageFile =  File(getRealPathFromURI(imgUri));
//                //  Log.d("IIMMAAGGEE-4", imageFile.toString())
//
//                uploadImage(imgUri)
//
//            }
//        }
//    }
//
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
//
//    fun uploadImage(file: Uri) {
//        val storageRef = viewModel.storage?.reference
//
//
//        val riversRef = storageRef?.child("profile_images/aaa.jpg")
//        val uploadTask = riversRef?.putFile(file)
//
//// Register observers to listen for when the download is done or if it fails
//        uploadTask?.addOnFailureListener {
//            showMessage(it.message!!)
//        }?.addOnSuccessListener {
//            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
//            // ...
//            showMessage("img upload")
//        }
//   }

//todo prebaci u settings
}