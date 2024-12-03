package com.example.virgo.model

import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.example.virgo.model.lib.Address
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.firestore.DocumentId

@IgnoreExtraProperties
data class User(
    @DocumentId
    val id: String? = null,
    val name: String? = null,
    val gender: Boolean? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val avatarImage: String? = null,
    val addresses: List<Address> = emptyList(),
    val cart : List<ProductWithQuantity> = emptyList()
)
