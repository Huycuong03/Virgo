package com.example.virgo.model

import com.google.firebase.Timestamp

data class Message(
    val isUser: Boolean = true,
    val timestamp: Timestamp,
    val text: String? = null,
    val imageUrl: String? = null
)
