package com.example.virgo.viewModel

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.virgo.model.appointment.Facility
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class FacilityDetailViewModel : ViewModel() {
    fun loadByFacilityId(id: String, callback: (Facility) -> Unit) {
        FirebaseFirestore.getInstance().collection("facilities").document(id).get().addOnSuccessListener {document ->
            document.toObject<Facility>()?.let {
                callback(it)
            }
        }
    }
}