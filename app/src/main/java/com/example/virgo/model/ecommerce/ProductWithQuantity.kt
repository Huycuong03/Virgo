package com.example.virgo.model.ecommerce

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ProductWithQuantity (
    val product: Product? = null,
    val quantity: Int? = null
)