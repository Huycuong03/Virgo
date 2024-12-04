package com.example.virgo.route.profile

import com.example.virgo.model.lib.Address
import kotlinx.serialization.Serializable

@Serializable
data class ChangeAddressRoute(
    val index: Int
)