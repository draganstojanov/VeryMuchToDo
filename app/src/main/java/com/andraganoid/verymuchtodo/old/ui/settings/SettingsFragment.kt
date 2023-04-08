package com.andraganoid.verymuchtodo.old.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.andraganoid.verymuchtodo.BuildConfig
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.SettingsFragmentBinding
import com.andraganoid.verymuchtodo.old.util.areYouSure
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class SettingsFragment : Fragment() {

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by activityViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = SettingsFragmentBinding.inflate(inflater, container, false)
        setup()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setup() {
        binding.clearAutocompleteBtn.setOnClickListener { areYouSure { viewModel.clearAutocompleteList() } }
        binding.editAutocompleteBtn.setOnClickListener { findNavController().navigate(R.id.autocompleteEditFragment) }
        binding.appVersion.text = BuildConfig.VERSION_NAME
    }

}
