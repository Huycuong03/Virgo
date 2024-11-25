package com.example.virgo.model.lib

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Session(
    val fromHour: Int? = null,
    val toHour: Int? = null
)
