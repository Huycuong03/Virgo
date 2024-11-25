package com.example.virgo.model.ecommerce

import com.google.firebase.Timestamp
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Delivery(
    val shipper: Shipper? = null,
    val fee: Float? = null,
    val timestamp: Timestamp? = null
)
