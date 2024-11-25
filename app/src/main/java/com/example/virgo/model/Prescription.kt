package com.example.virgo.model

import com.google.firebase.Timestamp
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.firestore.DocumentId

@IgnoreExtraProperties
data class Prescription(
    @DocumentId
    val id: String? = null,
    val facilityName: String? = null,
    val doctor: String? = null,
    val timestamp: Timestamp? = null
)
