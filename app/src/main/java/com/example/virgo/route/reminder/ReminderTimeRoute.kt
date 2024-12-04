package com.example.virgo.route.reminder

import com.example.virgo.model.lib.Reminder
import kotlinx.serialization.Serializable

@Serializable
data class ReminderTimeRoute(
    val reminder: Reminder
)
