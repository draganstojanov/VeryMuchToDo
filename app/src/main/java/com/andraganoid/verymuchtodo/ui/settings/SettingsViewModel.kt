package com.andraganoid.verymuchtodo.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andraganoid.verymuchtodo.util.Prefs

class SettingsViewModel(private val prefs: Prefs) : ViewModel() {

    private val _autocompleteItemList = MutableLiveData<MutableList<String>>()
    val autocompleteItemList: LiveData<MutableList<String>>
        get() = _autocompleteItemList

    init {
        _autocompleteItemList.value = prefs.getAutocompleteIemList()
    }

    fun clearAutocompleteList() {
        prefs.saveAutocompleteItemList(mutableListOf())
      //  _autocompleteItemList.value = mutableListOf()
    }


}