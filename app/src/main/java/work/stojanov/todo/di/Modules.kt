package work.stojanov.todo.di

import work.stojanov.todo.main.MainViewModel
import work.stojanov.todo.main.TodoViewModel
import work.stojanov.todo.repository.AuthRepository
import work.stojanov.todo.repository.FirestoreRepository
import work.stojanov.todo.repository.ListenersRepository
import work.stojanov.todo.ui.settings.SettingsViewModel
import work.stojanov.todo.ui.tools.calculator.CalculatorTool
import work.stojanov.todo.util.Prefs
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


