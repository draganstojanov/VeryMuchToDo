package com.andraganoid.verymuchtodo.repository

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.util.ADMIN_EMAIL
import com.andraganoid.verymuchtodo.util.ERROR_PLACEHOLDER
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepository(val context: Context) {

    fun login(mail: String, pass: String): Task<AuthResult> = (FirebaseAuth.getInstance().signInWithEmailAndPassword(mail, pass))

    suspend fun verifyEmail(): Flow<String> = flow {
        FirebaseAuth.getInstance().currentUser?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                flow { emit(R.string.check_mail_for_verification) }
            } else {
                flow { emit(ERROR_PLACEHOLDER + task.exception) }
            }
        }
    }

    fun resetPassword(mail: String): Flow<String> = flow {
        FirebaseAuth.getInstance().sendPasswordResetEmail(mail).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                flow { emit(R.string.check_mail_for_pass_reset) }
            } else {
                flow { emit(ERROR_PLACEHOLDER + task.exception) }
            }
        }
    }


    fun sendEmailToAdmin() {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(ADMIN_EMAIL))
            putExtra(Intent.EXTRA_SUBJECT, "VeryMuchToDo new User - " + FirebaseAuth.getInstance().currentUser?.displayName)
            putExtra(Intent.EXTRA_TEXT, FirebaseAuth.getInstance().currentUser?.displayName + "\n" + FirebaseAuth.getInstance().currentUser?.email)
            type = "message/rfc822"
        }
        try {
            context.startActivity(Intent.createChooser(emailIntent,
                    "Send email using..."));
        } catch (ex: ActivityNotFoundException) {
            Log.d("EMAIL", "ERROR")
        }
    }


}