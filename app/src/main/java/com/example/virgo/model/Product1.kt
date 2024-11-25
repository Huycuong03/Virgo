package com.example.virgo.model

data class Product1(
    val id: Int,
    val name: String,
    val price: String,
    val originalPrice: String? = null,
    val quantity: Int,
    val unit: String,
    val imageUrl: Int,
    var isChecked: Boolean = false
)