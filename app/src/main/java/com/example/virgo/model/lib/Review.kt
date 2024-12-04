package com.example.virgo.model.lib

import com.example.virgo.model.User
import com.google.firebase.Timestamp
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.serialization.Serializable

@Serializable
@IgnoreExtraProperties
data class Review(
    val user: User? = null,
    val rating: Float? = null,
    val comment: String? = null,
    val timestamp: Long? = null
)

