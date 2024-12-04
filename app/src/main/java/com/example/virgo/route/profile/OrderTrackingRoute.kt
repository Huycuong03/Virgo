package com.example.virgo.route.profile

import com.example.virgo.model.ecommerce.OrderStatus
import kotlinx.serialization.Serializable

@Serializable
data class OrderTrackingRoute (
    val status: OrderStatus
)