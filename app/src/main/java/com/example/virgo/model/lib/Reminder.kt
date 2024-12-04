package com.example.virgo.model.lib

import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.google.firebase.Timestamp
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Reminder (
    val name: String,
    val dateCreated: Long = Timestamp.now().seconds,
    val duration: Int,
    val skip: Int,
    val note: Boolean = true,
    val isActive: Boolean = true,
    val products: List<ProductWithQuantity> = emptyList(),
    val alarms: List<Alarm> = emptyList()
)