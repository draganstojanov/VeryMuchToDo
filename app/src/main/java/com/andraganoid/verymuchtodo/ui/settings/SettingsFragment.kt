package com.andraganoid.verymuchtodo.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.SettingsFragmentBinding
import com.andraganoid.verymuchtodo.util.areYouSure
import com.andraganoid.verymuchtodo.util.main
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.BuiltInAnnotationDescriptor

class SettingsFragment : Fragment() {

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModel()


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
        with(main) {
            showTitle(getString(R.string.settings))
            showArrow(true)
            showSettings(false)
        }
        binding.clearAutocompleteBtn.setOnClickListener { areYouSure { viewModel.clearAutocompleteList()} }
        binding.editAutocompleteBtn.setOnClickListener {editAutocompleteList() }
    }


    private fun editAutocompleteList() {
        TODO("Not yet implemented")
    }
}
