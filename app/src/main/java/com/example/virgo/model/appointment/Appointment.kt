package com.example.virgo.model.appointment

import com.example.virgo.model.lib.Payment
import com.example.virgo.model.lib.Session
import com.google.firebase.Timestamp
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.firestore.DocumentId
import java.time.LocalDate
import java.time.ZoneId

@IgnoreExtraProperties
data class Appointment(
    @DocumentId
    val id: String? = null,
    val uid: String? = null,
    val facility: Facility? = null,
    val date: Timestamp? = null,
    val session: Session? = null,
    val orderNumber: Int? = null,
    val status: String? = null,
    val reason: String? = null,
    val payment: Payment? = null
)
{
    fun getFormatedDate(): LocalDate? {
        val instant = date?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
        return instant
    }
}
