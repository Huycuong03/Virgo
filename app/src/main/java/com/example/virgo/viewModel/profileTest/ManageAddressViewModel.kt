package com.example.virgo.viewModel.profileTest

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.virgo.model.User
import com.example.virgo.model.lib.Address
import com.example.virgo.repository.SharedPreferencesManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class ManageAddressViewModel : ViewModel() {
    private val _addresses = mutableStateListOf<Address>()
    private val db = FirebaseFirestore.getInstance().collection("users").document(SharedPreferencesManager.getUID())
    private val _selectedAddress = mutableStateOf<Address?>(null)
    val addresses: State<List<Address>> get() = derivedStateOf {
        _addresses.toList()
    }
    val selectedAddress: State<Address?> get() = _selectedAddress

    init {
        db.get().addOnSuccessListener { snapshot ->
                snapshot.toObject<User>()?.let{
                    _addresses.clear()
                    _addresses.addAll(it.addresses)
                }
            }
    }
}