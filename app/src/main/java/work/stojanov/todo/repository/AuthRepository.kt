package work.stojanov.todo.repository


import work.stojanov.todo.model.state.AuthState
import work.stojanov.todo.util.LOGIN_EMAIL
import work.stojanov.todo.util.LOGIN_PASS
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthRepository(private val firebaseAuth: FirebaseAuth) {

    private val authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Unchecked)
    fun getAuthState(): StateFlow<AuthState> = authState

    fun loginCheck() {
        if (firebaseAuth.currentUser != null) {
            authState.tryEmit(AuthState.Success)
        } else {
            login()
        }
    }

    private fun login() {
        firebaseAuth.signInWithEmailAndPassword(LOGIN_EMAIL, LOGIN_PASS).addOnCompleteListener { task ->
            when {
                task.isSuccessful -> authState.tryEmit(AuthState.Success)
                task.isCanceled -> authState.tryEmit(AuthState.Cancelled)
                else -> createUser()
            }
        }
    }

    private fun createUser() {
        firebaseAuth.createUserWithEmailAndPassword(LOGIN_EMAIL, LOGIN_PASS).addOnCompleteListener { task ->
            when {
                task.isSuccessful -> authState.tryEmit(AuthState.Success)
                task.isCanceled -> authState.tryEmit(AuthState.Cancelled)
                else -> authState.tryEmit(AuthState.Error(task.exception?.localizedMessage))
            }
        }
    }

}
