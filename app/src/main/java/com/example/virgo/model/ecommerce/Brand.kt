package com.example.virgo.model.ecommerce

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Brand(
    val name: String? = null,
    val origin: String? = null
)
