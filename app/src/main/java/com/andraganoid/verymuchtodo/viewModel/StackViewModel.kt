package com.andraganoid.verymuchtodo.viewModel

import androidx.lifecycle.ViewModel
import com.andraganoid.verymuchtodo.old.model.state.StackState
import com.andraganoid.verymuchtodo.old.repository.AuthRepository
import com.andraganoid.verymuchtodo.old.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.old.repository.ListenersRepository
import com.andraganoid.verymuchtodo.old.util.Prefs
import kotlinx.coroutines.flow.StateFlow

class StackViewModel(
    private val prefs: Prefs,
    private val authRepository: AuthRepository,
    private val listenersRepository: ListenersRepository,
    private val firestoreRepository: FirestoreRepository,
) : ViewModel() {

    val userName:String?
        get()=prefs.getUserName()

    fun saveUserName(name: String) {
        prefs.saveUserName(name)
    }

    fun getSnapshotState(): StateFlow<StackState?> = listenersRepository.getStackState()




}