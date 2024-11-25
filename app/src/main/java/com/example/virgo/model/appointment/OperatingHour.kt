package com.example.virgo.model.appointment

import com.example.virgo.model.lib.Session
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class OperatingHour(
    val fromDay: String? = null,
    val toDay: String? = null,
    val sessions: List<Session> = emptyList()
)
