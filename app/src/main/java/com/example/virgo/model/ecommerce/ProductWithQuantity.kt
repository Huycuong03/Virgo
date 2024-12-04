package com.example.virgo.model.ecommerce

import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.serialization.Serializable

@Serializable
@IgnoreExtraProperties
data class ProductWithQuantity (
    val product: Product? = null,
    val quantity: Int? = null,
    val selected: Boolean? = false
)