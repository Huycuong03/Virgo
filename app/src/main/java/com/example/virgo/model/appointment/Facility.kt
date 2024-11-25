package com.example.virgo.model.appointment

import com.example.virgo.model.lib.Address
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

@IgnoreExtraProperties
data class Facility(
    val id: String? = null,
    val coverImage: String? = null,
    val avatarImage: String? = null,
    val name: String? = null,
    val description: String? = null,
    val address: Address? = null,
    val departments: List<Int> = emptyList(),
)
