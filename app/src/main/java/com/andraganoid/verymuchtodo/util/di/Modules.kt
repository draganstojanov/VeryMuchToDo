package com.andraganoid.verymuchtodo.util.di

import com.andraganoid.verymuchtodo.old.main.MainViewModelOld
import com.andraganoid.verymuchtodo.old.main.TodoViewModel
import com.andraganoid.verymuchtodo.old.repository.AuthRepository
import com.andraganoid.verymuchtodo.old.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.old.repository.ListenersRepository
import com.andraganoid.verymuchtodo.old.ui.settings.SettingsViewModel
import com.andraganoid.verymuchtodo.old.util.Prefs
import com.andraganoid.verymuchtodo.viewModel.MainViewModel
import com.andraganoid.verymuchtodo.viewModel.StackViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object Modules {

    private val viewModelModule = module {
        viewModel { MainViewModelOld(get(), get(), get()) }
        viewModel { TodoViewModel(get(), get(), get()) }
        viewModel { SettingsViewModel(get()) }
    }

    private val singleModule = module {
        single { Prefs(context = androidContext()) }
        single { AuthRepository(firebaseAuth = FirebaseAuth.getInstance()) }
        single { FirestoreRepository(firebaseFirestore = FirebaseFirestore.getInstance()) }
        single { ListenersRepository(firebaseFirestore = FirebaseFirestore.getInstance()) }
    }

    private val composeSingleModule = module {}

    private val composeViewModelModule = module {
        viewModel { StackViewModel(get(), get(), get(), get()) }
        viewModel { MainViewModel(get(), get(), get()) }
    }

    val appModule = listOf(
        viewModelModule,
        singleModule,
        composeSingleModule,
        composeViewModelModule
    )

}


