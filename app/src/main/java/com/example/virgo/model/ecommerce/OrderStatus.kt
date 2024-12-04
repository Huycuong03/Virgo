package com.example.virgo.model.ecommerce

import kotlinx.serialization.Serializable

@Serializable
enum class OrderStatus (val text: String, val index: Int) {
    PROCESSING(text = "Processing", index = 0),
    ON_DELIVERY(text = "On delivery", index = 1),
    COMPLETED(text = "Completed", index = 2),
    CANCELED(text = "Canceled", index = 3);
}