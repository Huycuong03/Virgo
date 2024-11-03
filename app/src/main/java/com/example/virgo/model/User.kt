package com.example.virgo.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.util.Date

@IgnoreExtraProperties
data class User(
    var id: Int? = null,
    var username: String? = null,
    var password: String? = null,
    var name: String? = null,
    var dob: Date? = null,
    var gender: Boolean? = null,
    var address: String? = null,
    var phoneNumber: String? = null,
    var email: String? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "username" to username,
            "password" to password,
            "name" to name,
            "dob" to dob?.time,
            "gender" to gender,
            "address" to address,
            "phoneNumber" to phoneNumber,
            "email" to email
        )
    }
}
