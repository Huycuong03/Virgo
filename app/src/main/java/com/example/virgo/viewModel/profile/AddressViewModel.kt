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

class AddressViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance().collection("users").document(SharedPreferencesManager.getUID())

    // State variables for each address field
    private val _fullName = mutableStateOf("")
    private val _phoneNumber = mutableStateOf("")
    private val _city = mutableStateOf("")
    private val _district = mutableStateOf("")
    private val _ward = mutableStateOf("")
    private val _street = mutableStateOf("")
    private val _houseNumber = mutableStateOf("")
    private val _note = mutableStateOf("")

    // Public state variables that can be accessed by the composables
    val fullName: State<String> get() = _fullName
    val phoneNumber: State<String> get() = _phoneNumber
    val city: State<String> get() = _city
    val district: State<String> get() = _district
    val ward: State<String> get() = _ward
    val street: State<String> get() = _street
    val houseNumber: State<String> get() = _houseNumber
    val note: State<String> get() = _note

    init{
        db.get().addOnSuccessListener { snapshot ->
            snapshot.toObject<User>()?.let{user ->
                _fullName.value = user.name.toString()
                _phoneNumber.value = user.phoneNumber.toString()
            }
        }
    }

    // Function to update the address in Firestore
    fun addNewAddress() {
        val address = Address(
            name = _fullName.value,
            phoneNumber = _phoneNumber.value,
            city = _city.value,
            district = _district.value,
            ward = _ward.value,
            street = _street.value,
            houseNumber = _houseNumber.value,
            note = _note.value
        )

        // Firestore update logic
        db.update("addresses", FieldValue.arrayUnion(address))
    }
    fun onChangeLabel(label: String, text: String){
        if(label == "Họ và tên") _fullName.value = text
        if(label == "Số điện thoại") _phoneNumber.value = text
        if(label == "Thành phố") _city.value = text
        if(label == "Quận") _district.value = text
        if(label == "Phường") _ward.value = text
        if(label == "Đường") _street.value = text
        if(label == "Số nhà") _houseNumber.value = text

    }
    fun onChangeNote(note: String){
        _note.value = note
    }
}