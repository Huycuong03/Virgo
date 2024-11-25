package com.example.virgo.model.ecommerce

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Shipper(
    val name: String? = null,
    val phoneNumber: String? = null
)
