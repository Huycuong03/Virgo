package com.example.virgo.model.lib

import com.google.firebase.Timestamp
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Payment(
    val method: String? = null,
    val total: Float? = null,
    val timestamp: Timestamp? = null
)
