package com.example.virgo.model.ecommerce

import com.example.virgo.model.lib.Review
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.Serializable
import java.text.NumberFormat
import java.util.Locale
@Serializable
@IgnoreExtraProperties
data class Product(
    val id: String? = null,
    val name: String? = null,
    val stockQuantity: Int? = null,
    val price: Float? = null,
    val brand: Brand? = null,
    val packaging: Packaging? = null,
    val images: List<String> = emptyList(),
    val category: String? = null,
    val manufacturer: Manufacturer? = null,
    val ingredients: List<String> = emptyList(),
    val description: String? = null,
    val registrationNumber: String? = null,
    val instructions: String? = null,
    val preserveInstruction: String? = null,
    val warning: String? = null,
    val reviews: List<Review> = emptyList()
){
    fun getFormattedPrice(): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US) // Change Locale if needed
        return formatter.format(price?:0)
    }
}