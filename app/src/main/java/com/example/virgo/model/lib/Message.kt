package com.example.virgo.model.lib

import com.google.firebase.Timestamp
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Message(
    val user: Boolean? = null,
    val timestamp: Long? = null,
    val text: String? = null,
    val image: String? = null
)
