package com.andraganoid.verymuchtodo.di

import androidx.room.Room
import com.andraganoid.verymuchtodo.auth.AuthViewModel
import com.andraganoid.verymuchtodo.database.TodoDatabase
import com.andraganoid.verymuchtodo.ktodo.chat.ChatsViewModel
import com.andraganoid.verymuchtodo.ktodo.profile.ProfileViewModel
import com.andraganoid.verymuchtodo.ktodo.users.UsersViewModel
import com.andraganoid.verymuchtodo.repository.AuthRepository
import com.andraganoid.verymuchtodo.repository.DatabaseRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.repository.ListenersRepository
import com.andraganoid.verymuchtodo.util.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


object Modules {

    private val viewModelModule = module {
        viewModel { AuthViewModel(get(), get(), get()) }
        viewModel { ProfileViewModel(get(), get(), get()) }
        viewModel { UsersViewModel(get()) }
        viewModel { ChatsViewModel(get(), get()) }
    }

    private val singleModule = module {
        single { Preferences(context = androidContext()) }
        single { AuthRepository(firebaseAuth = FirebaseAuth.getInstance()) }
        single { FirestoreRepository(firebaseFirestore = FirebaseFirestore.getInstance()) }
        single { ListenersRepository(FirebaseFirestore.getInstance(), get(), get()) }
        single { DatabaseRepository(get(), get()) }
    }

    private val factoryModule = module {
        // factory { ListenersRepository(FirebaseFirestore.getInstance(), get()) }
    }

    private val databaseModule = module {

        single { Room.databaseBuilder(androidContext(), TodoDatabase::class.java, "todo_database").fallbackToDestructiveMigration().build() }

       single { get<TodoDatabase>().userDao() }

        single { get<TodoDatabase>().chatDao() }
    }

    val appModule =
            listOf(
                    viewModelModule,
                    singleModule,
                    factoryModule,
                    databaseModule
            )

}


