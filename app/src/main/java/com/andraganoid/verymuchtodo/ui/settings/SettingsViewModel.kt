package com.andraganoid.verymuchtodo.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andraganoid.verymuchtodo.ui.settings.autocomplete.AutocompleteEditItem
import com.andraganoid.verymuchtodo.util.Prefs

class SettingsViewModel(private val prefs: Prefs) : ViewModel() {

    private val _autocompleteItemList = MutableLiveData<MutableList<String>>()
    val autocompleteItemList: LiveData<MutableList<String>>
        get() = _autocompleteItemList

    var preEditedText: String = ""
    var changedText: String = ""
    var deletionList: ArrayList<AutocompleteEditItem?> = arrayListOf()

    init {
        checkAutocompleteList()
    }

    fun checkAutocompleteList() {
        _autocompleteItemList.value = prefs.getAutocompleteIemList() ?: mutableListOf()
    }


    fun clearAutocompleteList() {
        prefs.saveAutocompleteItemList(mutableListOf())
        _autocompleteItemList.value = mutableListOf()
    }

    fun submitChanges() {
        if (changedText.isNotEmpty()) {
            _autocompleteItemList.value = _autocompleteItemList.value.also {
                it?.remove(preEditedText)
                it?.add(changedText)
            }
        }
        prefs.saveAutocompleteItemList(autocompleteItemList.value)
    }

    fun deleteCheckedItems() {
        if (deletionList.isNotEmpty()) {
            _autocompleteItemList.value = _autocompleteItemList.value.also {
                deletionList.forEach { del ->
                    if (del?.checkForDeletion == true) {
                        it?.remove(del.text)
                    }
                }
            }
        }
        prefs.saveAutocompleteItemList(autocompleteItemList.value)
    }


}