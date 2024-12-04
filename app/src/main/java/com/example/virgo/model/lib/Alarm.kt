package com.example.virgo.model.lib

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable
import java.util.Calendar

@Serializable
data class Alarm (
    val hour: Int = 8,
    val min: Int = 35
) {
    @SuppressLint("DefaultLocale")
    override fun toString(): String {
        return "${String.format("%02d", hour)}:${String.format("%02d", min)}"
    }

    fun toCalendar(): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, min)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }
}