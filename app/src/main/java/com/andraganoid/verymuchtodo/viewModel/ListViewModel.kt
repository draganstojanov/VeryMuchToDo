package com.andraganoid.verymuchtodo.viewModel

import androidx.lifecycle.ViewModel
import com.andraganoid.verymuchtodo.model.state.StackState
import com.andraganoid.verymuchtodo.old.util.Prefs
import com.andraganoid.verymuchtodo.repository.AuthRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.repository.ListenersRepository
import kotlinx.coroutines.flow.StateFlow

class ListViewModel (
    private val prefs: Prefs,
    private val authRepository: AuthRepository,
    private val listenersRepository: ListenersRepository,
    private val firestoreRepository: FirestoreRepository,
) : ViewModel() {


    fun getSnapshotState(): StateFlow<StackState?> = listenersRepository.getStackState()


}