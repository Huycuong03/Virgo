package com.example.virgo.model.ecommerce

import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.serialization.Serializable

@Serializable
@IgnoreExtraProperties
data class Packaging(
    val type: String? = null,
    val quantity: Int? = null,
    val dosageForm: String? = null
)
