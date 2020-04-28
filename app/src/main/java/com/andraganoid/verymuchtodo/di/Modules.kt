package com.andraganoid.verymuchtodo.di

import com.andraganoid.verymuchtodo.ktodo.profile.ProfileViewModel
import com.andraganoid.verymuchtodo.main.login.LoginViewModel
import com.andraganoid.verymuchtodo.main.register.RegisterViewModel
import com.andraganoid.verymuchtodo.util.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


object Modules {

    private val viewModelModule = module {
        viewModel { LoginViewModel(get()) }
        viewModel { RegisterViewModel() }
        viewModel { ProfileViewModel() }
    }

    private val singleModule = module {
        single { Preferences(androidContext()) }

    }

    private val factoryModule = module {
        //  factory { GameInit(androidContext(),get()) }
    }

//    private val databaseModule = module {
//        single {
//            val db: MemoryDatabase = get()
//            db.savedGamesDao()
//        }
//        single {
//            val db: MemoryDatabase = get()
//            db.highScoresDao()
//        }
//        single {
//            Room.databaseBuilder(get(), MemoryDatabase::class.java, "memory_fields")
//                    .fallbackToDestructiveMigration()
//                    .build()
//        }
//    }

    val appModule =
            listOf(
                    viewModelModule,
                    singleModule,
                    factoryModule
            )


}


