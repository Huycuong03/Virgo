package com.example.virgo.model.lib

import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.serialization.Serializable

@Serializable
@IgnoreExtraProperties
data class Address(
    val city: String? = null,
    val district: String? = null,
    val ward: String? = null,
    val street: String? = null,
    val houseNumber: String? = null,
    val note: String? = null
) {
    override fun toString(): String {
        return "$houseNumber $street, $ward, $district, $city"
    }
}
