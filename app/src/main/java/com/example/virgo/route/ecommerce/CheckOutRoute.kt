package com.example.virgo.route.ecommerce

import kotlinx.serialization.Serializable

@Serializable
data class CheckOutRoute (
    val cartItemIdList: List<String>
)