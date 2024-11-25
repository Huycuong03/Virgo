package com.example.virgo.model.lib

import com.google.firebase.Timestamp
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Message(
    val isUser: Boolean? = null,
    val timestamp: Timestamp? = null,
    val text: String? = null,
    val image: String? = null
)
