package com.example.virgo.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.virgo.model.appointment.Appointment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class AppointmentListViewModel : ViewModel() {
    private val _appointmentList = mutableStateListOf<Appointment>()
    val appointmentList: State<List<Appointment>> get() = derivedStateOf {
        _appointmentList.toList()
    }

    fun init() {
        FirebaseFirestore.getInstance().collection("appointments").get().addOnSuccessListener { documents ->
            for (doc in documents) {
                val appointment = doc.toObject<Appointment>()
                if (appointment.uid == "hUMo6tRvQ6XcLhDkBOV9A1w6Jvr2") {
                    _appointmentList.add(appointment)
                }
            }
        }
    }
    fun cancelAppointment(appointment: Appointment) {
        appointment.id?.let {
            FirebaseFirestore.getInstance()
                .collection("appointments")
                .document(it)
                .update("status", "Đã hủy")
                .addOnFailureListener { exception ->
                    exception.printStackTrace()
                }
        }
    }
}
