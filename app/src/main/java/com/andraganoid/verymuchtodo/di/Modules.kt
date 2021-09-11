package com.andraganoid.verymuchtodo.di

import com.andraganoid.verymuchtodo.main.MainViewModel
import com.andraganoid.verymuchtodo.repository.AuthRepo
import com.andraganoid.verymuchtodo.repository.FirestoreRepo
import com.andraganoid.verymuchtodo.repository.ListenersRepo
import com.andraganoid.verymuchtodo.util.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object Modules {

    private val viewModelModule = module {
        viewModel { MainViewModel(get(), get(), get()) }
    }

    private val singleModule = module {
        single { Prefs(context = androidContext()) }
        single { AuthRepo(firebaseAuth = FirebaseAuth.getInstance()) }
        single { FirestoreRepo(firebaseFirestore = FirebaseFirestore.getInstance()) }
        single { ListenersRepo(firebaseFirestore = FirebaseFirestore.getInstance()) }
    }

    private val factoryModule = module {
        // factory { ListenersRepository(FirebaseFirestore.getInstance(), get()) }
    }


    val appModule =
        listOf(
            viewModelModule,
            singleModule
        )

}


