package com.example.virgo.viewModel.profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.virgo.model.User
import com.example.virgo.model.lib.Message
import com.example.virgo.repository.SharedPreferencesManager
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class ProfileViewModel: ViewModel() {
    private val stg = FirebaseStorage.getInstance().getReference("images")
    private val db = FirebaseFirestore.getInstance().collection("users").document(SharedPreferencesManager.getUID())
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

    fun uploadAvatarImage(image: Uri?) {
        image?.let {
            val fileName = UUID.randomUUID().toString()
            val task = stg.child(fileName).putFile(image)
            task.addOnSuccessListener {
                stg.child(fileName).downloadUrl.addOnSuccessListener { url ->
                    _user.value = _user.value.copy(avatarImage = url.toString())
                }
            }
        }
    }

    fun updateUser(onSuccess: () -> Unit, onFailure: () -> Unit, onValidationFailure: (String) -> Unit) {
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

        db.set(currentUser)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure()
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