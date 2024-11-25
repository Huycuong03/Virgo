package com.example.virgo.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import com.example.virgo.model.User
import com.example.virgo.model.appointment.Appointment
import com.example.virgo.model.appointment.Facility
import com.example.virgo.model.lib.Session
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import java.time.LocalDate

class AppointmentBookingViewModel : ViewModel() {
    private  val _reason =   mutableStateOf("")
    private val _selectedDate =   mutableStateOf(LocalDate.now())
    private val _selectedSession =   mutableStateOf(Session())
    private val _user = mutableStateOf(User())
    private val _facility = mutableStateOf(Facility())

    val reason : State<String> get() = _reason
    val selectedDate : State<LocalDate> get() = _selectedDate
    val selectedSession : State<Session> get() = _selectedSession
    val user : State<User> get() = _user
    val facility : State<Facility> get() = _facility

    fun init(facilityId: String){
        FirebaseFirestore.getInstance().collection("facilities").document(facilityId).get().addOnSuccessListener { doc ->
            doc.toObject<Facility>()?.let {
                _facility.value = it.copy(id = doc.id)
            }
        }
        FirebaseFirestore.getInstance().collection("users").document("hUMo6tRvQ6XcLhDkBOV9A1w6Jvr2").get().addOnSuccessListener { doc ->
            _user.value = doc.toObject<User>()!!
        }
    }
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun createAppointment(){
        val appointment = Appointment(
            uid = "hUMo6tRvQ6XcLhDkBOV9A1w6Jvr2",
            facility = _facility.value,
            date = Timestamp(_selectedDate.value.toEpochDay() * 24*3600, 0),
            session = _selectedSession.value,
            orderNumber = 1,
            status = "Chưa hoàn thành",
            reason = _reason.value
            )
        FirebaseFirestore.getInstance()
            .collection("appointments")
            .add(appointment)
            .addOnSuccessListener { documentReference ->
                println("Appointment added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }
    fun onChangeReason(newReason: String){
        _reason.value = newReason
    }
}
