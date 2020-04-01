package com.andraganoid.verymuchtodo.di

import org.koin.dsl.module


object Modules {

    private val viewModelModule = module {
//        viewModel { GameViewModel(get(), get(), get(), get()) }
//        viewModel { TypeMenuViewModel(get()) }
//        viewModel { MainMenuViewModel() }
//        viewModel { FieldMenuViewModel(get()) }
//        viewModel { GameOverViewModel(get()) }
//        viewModel { SavedGamesViewModel(get()) }
//        viewModel { HighScoresViewModel(get(), get()) }
    }

    private val singleModule = module {
//        single { ItemImagesBuilder(get()) }
//        single { Preferences(androidContext()) }
//        single { SavedRepository(get()) }
//        single { HighScoreRepository(get()) }
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


