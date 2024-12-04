package com.example.virgo.viewModel.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.virgo.model.User
import com.example.virgo.model.lib.Address
import com.example.virgo.repository.SharedPreferencesManager
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class ChangeAddressViewModel:ViewModel() {
    private val db = FirebaseFirestore.getInstance().collection("users").document(SharedPreferencesManager.getUID())
    private var _originalAddress = Address()
    private val _address = mutableStateOf(Address())
    val address: State<Address> get() = _address

    fun loadAddress(addressIndex: Int) {
        db.get().addOnSuccessListener { snapshot ->
            snapshot.toObject<User>()?.let {
                _address.value = it.addresses[addressIndex]
                _originalAddress = _address.value
            }
        }
    }

    fun onFieldChange(fieldIndex: Int, text: String) {
        _address.value = when (fieldIndex) {
            0 -> _address.value.copy(name = text)
            1 -> _address.value.copy(phoneNumber = text)
            2 -> _address.value.copy(city = text)
            3 -> _address.value.copy(district = text)
            4 -> _address.value.copy(ward = text)
            5 -> _address.value.copy(street = text)
            6 -> _address.value.copy(houseNumber = text)
            else -> _address.value.copy(note = text)
        }
    }

    fun onSave(callback: () -> Unit ) {
        db.update("addresses", FieldValue.arrayRemove(_originalAddress)).addOnSuccessListener {
            db.update("addresses", FieldValue.arrayUnion(_address.value)).addOnSuccessListener {
                callback()

            }
        }
    }

    fun onDelete(callback: () -> Unit) {
        db.update("addresses", FieldValue.arrayRemove(_originalAddress)).addOnSuccessListener {
            callback()
        }
    }
}