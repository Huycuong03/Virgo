package com.example.virgo.viewModel.appointment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
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
    private val db = FirebaseFirestore.getInstance()

    val reason : State<String> get() = _reason
    val selectedDate : State<LocalDate> get() = _selectedDate
    val selectedSession : State<Session> get() = _selectedSession
    val user : State<User> get() = _user
    val facility : State<Facility> get() = _facility

    fun init(facilityId: String){
        db.collection("facilities").document(facilityId).get().addOnSuccessListener { doc ->
            doc.toObject<Facility>()?.let {
                _facility.value = it.copy(id = doc.id)
            }
        }
        db.collection("users").document("hUMo6tRvQ6XcLhDkBOV9A1w6Jvr2").get().addOnSuccessListener { doc ->
            _user.value = doc.toObject<User>()!!
        }
    }
    fun onDateTimeSelected(date: LocalDate, session: Session) {
        _selectedDate.value = date
        _selectedSession.value = session
    }

    fun createAppointment(onSuccess: () -> Unit, onFailure: () -> Unit, onValidationFailure: (String) -> Unit){
        if (_reason.value.isBlank()) {
            onValidationFailure("Lý do không được để trống.")
            return
        }
        if (_selectedDate.value == LocalDate.now().minusDays(1)) {
            onValidationFailure("Ngày hẹn không hợp lệ.")
            return
}
        if (_selectedSession.value == Session()) {
            onValidationFailure("Vui lòng chọn giờ hẹn.")
            return
        }
        db.collection("appointments").get().addOnSuccessListener { documents ->
            var count : Int = 0
            for (doc in documents) {
                val appointment = doc.toObject<Appointment>()
                if (appointment.session == _selectedSession.value &&
                    appointment.facility == _facility.value &&
                    appointment.getFormatedDate() == _selectedDate.value &&
                    appointment.facility == _facility.value) {
                    count += 1
                }
            }
            val appointment = Appointment(
                uid = "hUMo6tRvQ6XcLhDkBOV9A1w6Jvr2",
                facility = _facility.value,
                date = Timestamp(_selectedDate.value.toEpochDay() * 24*3600, 0),
                session = _selectedSession.value,
                orderNumber =  count + 1,
                status = "coming",
                reason = _reason.value
            )
            db
                .collection("appointments")
                .add(appointment)
                .addOnSuccessListener { documentReference ->
                    println("Appointment added with ID: ${documentReference.id}")
                    onSuccess()
                }
                .addOnFailureListener { exception ->
                    exception.printStackTrace()
                    onFailure()
                }
        }
    }
    fun onChangeReason(newReason: String){
        _reason.value = newReason
    }
}
