package com.andraganoid.verymuchtodo.util.di

import com.andraganoid.verymuchtodo.old.main.MainViewModelOld
import com.andraganoid.verymuchtodo.old.main.TodoViewModel
import com.andraganoid.verymuchtodo.old.ui.settings.SettingsViewModel
import com.andraganoid.verymuchtodo.old.util.Prefs
import com.andraganoid.verymuchtodo.repository.AuthRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.repository.ListenersRepository
import com.andraganoid.verymuchtodo.viewModel.ListViewModel
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
        viewModel { MainViewModel(get(), get(), get()) }
        viewModel { StackViewModel(get(), get(), get(), get()) }
        viewModel { ListViewModel(get(), get(), get(), get(), get()) }
    }

    val appModule = listOf(
        viewModelModule,
        singleModule,
        composeSingleModule,
        composeViewModelModule
    )

}


