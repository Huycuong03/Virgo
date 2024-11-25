package com.example.virgo.model.ecommerce

import com.example.virgo.model.lib.Address
import com.example.virgo.model.lib.Payment
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.firestore.DocumentId

@IgnoreExtraProperties
data class Order(
    @DocumentId
    val id: String? = null,
    val products: List<ProductWithQuantity> = emptyList(),
    val status: String? = null,
    val address: Address? = null,
    val payment: Payment? = null,
    val deliveries: List<Delivery> = emptyList()
)
