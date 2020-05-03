package com.andraganoid.verymuchtodo.ktodo.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.FragmentSettingsDialogBinding
import com.andraganoid.verymuchtodo.ktodo.TodoActivity
import com.andraganoid.verymuchtodo.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth


class SettingsDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSettingsDialogBinding
    val isLogoutSelected = ObservableBoolean(false)


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//       setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
//    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsDialogBinding.inflate(inflater, container, false)
        binding.fragment = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {}

    fun myProfile() {
        dismiss()
        val navController = (activity as TodoActivity).todoNavController
        navController.popBackStack(R.id.profileFragment, true)
        navController.navigate(R.id.profileFragment)

        // (activity as TodoActivity).findNavController(R.id.todoFragmentLayout).setGraph(R.navigation.settings_nav)
    }

    fun logout() {
        isLogoutSelected.set(true)
    }

    fun cancelLogout() {
        isLogoutSelected.set(false)
    }

    fun applyLogout() {
        FirebaseAuth.getInstance().signOut()
        val mainIntent = Intent(activity, MainActivity::class.java)
        mainIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(mainIntent)

        // (activity as TodoActivity).startActivity(Intent(context, MainActivity::class.java))
    }


}
