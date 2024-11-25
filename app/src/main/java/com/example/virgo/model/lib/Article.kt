package com.example.virgo.model.lib

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.firestore.DocumentId

@IgnoreExtraProperties
data class Article (
    @DocumentId
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val image: String? = null,
    val html: String? = null
)