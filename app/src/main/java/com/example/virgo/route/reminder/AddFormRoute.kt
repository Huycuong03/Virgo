package com.example.virgo.route.reminder
import kotlinx.serialization.Serializable

@Serializable
data class AddFormRoute(
    val products : List<String>
)
