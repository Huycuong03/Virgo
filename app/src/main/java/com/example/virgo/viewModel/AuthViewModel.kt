package com.example.virgo.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.virgo.repository.SharedPreferencesManager
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _email = mutableStateOf("")
    private val _password = mutableStateOf("")

    val email: State<String> get() = _email
    val password: State<String> get() = _password

    fun onEmailChange(text: String) {
        _email.value = text
    }

    fun onPasswordChange(text: String) {
        _password.value = text
    }

    fun onSignup( onSuccess: () -> Unit, onFail: (String) -> Unit ) {
        auth.createUserWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    login()
                    onSuccess()
                } else {
                    onFail(task.exception?.message.toString())
                }
            }
    }

    fun onLogin( onSuccess: () -> Unit, onFail: (String) -> Unit) {
        auth.signInWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    login()
                    onSuccess()
                } else {
                   onFail(task.exception?.message.toString())
                }
            }
    }

    private fun login() {
        auth.currentUser?.let {
            SharedPreferencesManager.saveString("uid", it.uid)
        }
    }
}