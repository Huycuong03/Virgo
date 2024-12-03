package com.example.virgo.model.lib

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Address(
    val name: String? = null,
    val phoneNumber: String? = null,
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
