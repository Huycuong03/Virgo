package com.example.virgo.model.appointment

import com.example.virgo.model.lib.Session
import com.google.firebase.database.IgnoreExtraProperties
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@IgnoreExtraProperties
data class OperatingHour(
    val fromDay: String? = null,
    val toDay: String? = null,
    val sessions: List<Session> = emptyList()
)
