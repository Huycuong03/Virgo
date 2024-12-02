package com.example.virgo.model.appointment

import com.example.virgo.model.lib.Address
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Facility(
    val id: String? = null,
    val avatarImage: String? = null,
    val deposit: Int? = null,
    val html: String? = null,
    val name: String? = null,
    val description: String? = null,
    val address: Address? = null,
    val departments: List<Int> = emptyList(),
    val operatingHours: List<OperatingHour> = emptyList()
)
