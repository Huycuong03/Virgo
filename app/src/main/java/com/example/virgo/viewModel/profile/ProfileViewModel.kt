package com.example.virgo.viewModel.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.virgo.model.User
import com.example.virgo.repository.SharedPreferencesManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class ProfileViewModel: ViewModel() {
    private val _user = mutableStateOf(User())
    val user: State<User> get() = _user

    init {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(SharedPreferencesManager.getUID())
            .get().addOnSuccessListener { snapshot ->
                snapshot.toObject<User>()?.let {
                    _user.value = it
                }
            }
    }
}