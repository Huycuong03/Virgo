package com.example.virgo.model.ecommerce

import kotlinx.serialization.Serializable

@Serializable
enum class OrderStatus (val text: String, val index: Int) {
    TO_PAY(text = "To-pay", index = 0),
    PROCESSING(text = "Processing", index = 1),
    COMPLETED(text = "Completed", index = 2),
    CANCELED(text = "Canceled", index = 3);
}