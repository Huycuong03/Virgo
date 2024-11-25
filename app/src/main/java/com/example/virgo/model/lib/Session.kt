package com.example.virgo.model.lib

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Session(
    val fromHour: Int? = null,
    val toHour: Int? = null

){
    override fun toString(): String {
        return fromHour.toString() + ":00 - " + toHour.toString() +":00"
    }
    fun isNull(): Boolean {
        return fromHour==null || toHour == null
    }
}
