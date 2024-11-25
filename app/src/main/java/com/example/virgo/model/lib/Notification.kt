package com.example.virgo.model.lib

import com.google.firebase.Timestamp
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Notification(
    val content: String? = null,
    val timestamp: Timestamp? = null
)
