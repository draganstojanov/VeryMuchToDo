package com.andraganoid.verymuchtodo.di

import com.andraganoid.verymuchtodo.main.MainViewModel
import com.andraganoid.verymuchtodo.main.TodoViewModel
import com.andraganoid.verymuchtodo.repository.AuthRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.repository.ListenersRepository
import com.andraganoid.verymuchtodo.ui.settings.SettingsViewModel
import com.andraganoid.verymuchtodo.ui.tools.calculator.CalculatorTool
import com.andraganoid.verymuchtodo.util.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object Modules {

    private val viewModelModule = module {
        viewModel { MainViewModel(get(), get(), get()) }
        viewModel { TodoViewModel(get(), get(), get()) }
        viewModel { SettingsViewModel(get()) }
    }

    private val singleModule = module {
        single { Prefs(context = androidContext()) }
        single { AuthRepository(firebaseAuth = FirebaseAuth.getInstance()) }
        single { FirestoreRepository(firebaseFirestore = FirebaseFirestore.getInstance()) }
        single { ListenersRepository(firebaseFirestore = FirebaseFirestore.getInstance()) }
    }

    val appModule = listOf(viewModelModule, singleModule)

}


