package com.example.virgo.viewModel.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImagePainter
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
    fun updateUser( onSuccess: () -> Unit, onFailure: () -> Unit,onValidationFailure: (String) -> Unit) {
        val currentUser = _user.value

        if (currentUser.name.isNullOrEmpty()) {
            onValidationFailure("Tên không được để trống.")
            return
        }
        if (currentUser.phoneNumber.isNullOrEmpty()) {
            onValidationFailure("Số điện thoại không được để trống.")
            return
        }
        if (currentUser.email.isNullOrEmpty()) {
            onValidationFailure("Email không được để trống.")
            return
        }

        val userRef = FirebaseFirestore.getInstance()
            .collection("users")
            .document(SharedPreferencesManager.getUID()) // UID lấy từ SharedPreferences

        userRef.set(currentUser)
            .addOnSuccessListener {
                onSuccess()
                Log.d("ProfileViewModel", "User updated successfully")
            }
            .addOnFailureListener { exception ->
                onFailure()
                Log.e("ProfileViewModel", "Error updating user: ", exception)
            }
    }

    fun onChangeName(name: String){
        _user.value = _user.value.copy(name = name)
    }
    fun onChangeEmail(email: String){
        _user.value = _user.value.copy(email = email)
    }
    fun onChangeGender(gender: Boolean){
        _user.value = _user.value.copy(gender = gender)
    }
}