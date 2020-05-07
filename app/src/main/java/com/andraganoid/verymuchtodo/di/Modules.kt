package com.andraganoid.verymuchtodo.di

import androidx.room.Room
import com.andraganoid.verymuchtodo.database.TodoDatabase
import com.andraganoid.verymuchtodo.ktodo.profile.ProfileViewModel
import com.andraganoid.verymuchtodo.ktodo.users.UsersViewModel
import com.andraganoid.verymuchtodo.main.auth.AuthViewModel
import com.andraganoid.verymuchtodo.repository.AuthRepository
import com.andraganoid.verymuchtodo.repository.DatabaseRepository
import com.andraganoid.verymuchtodo.repository.ListenersRepository
import com.andraganoid.verymuchtodo.repository.StorageRepository
import com.andraganoid.verymuchtodo.util.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


object Modules {

    private val viewModelModule = module {
        viewModel { AuthViewModel(get(), get()) }
        viewModel { ProfileViewModel(get()) }
        viewModel { UsersViewModel(get()) }
    }

    private val singleModule = module {
        single { Preferences(context = androidContext()) }
        single { AuthRepository(firebaseAuth = FirebaseAuth.getInstance()) }
        single { StorageRepository(firebaseFirestore = FirebaseFirestore.getInstance()) }
        single { ListenersRepository(FirebaseFirestore.getInstance(), get()) }
        single { DatabaseRepository(get()) }

    }

    private val factoryModule = module {
        //  factory { GameInit(androidContext(),get()) }
        // factory { ListenersRepository(FirebaseFirestore.getInstance(), get()) }
    }

    private val databaseModule = module {
        single {
            val db: TodoDatabase = get()
            db.userDao()
        }
//        single {
//            val db: MemoryDatabase = get()
//            db.highScoresDao()
//        }
        single {
            Room.databaseBuilder(get(), TodoDatabase::class.java, "todo_database")
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }

    val appModule =
            listOf(
                    viewModelModule,
                    singleModule,
                    factoryModule,
                    databaseModule
            )


}


