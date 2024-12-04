package com.example.virgo.viewModel.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.virgo.model.User
import com.example.virgo.repository.SharedPreferencesManager
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class ProfileViewModel: ViewModel() {
    private val _user = mutableStateOf(User())
    private val db = FirebaseFirestore.getInstance().collection("users").document(SharedPreferencesManager.getUID())
    val user: State<User> get() = _user

    init {
        db.get().addOnSuccessListener { snapshot ->
                snapshot.toObject<User>()?.let {
                    _user.value = it
                }
            }
    }

    fun onSave(updatedUser: User) {
        db.set(updatedUser)
            .addOnSuccessListener {
                _user.value = updatedUser
            }
    }
}