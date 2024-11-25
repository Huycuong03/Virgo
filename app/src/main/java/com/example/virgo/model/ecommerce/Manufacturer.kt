package com.example.virgo.model.ecommerce

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Manufacturer(
    val name: String? = null,
    val country: String? = null
)
