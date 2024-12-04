package com.example.virgo.route

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.virgo.model.lib.Reminder
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {
    val ReminderType = object: NavType<Reminder> (
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): Reminder? {
            return Json.decodeFromString(bundle.getString(key)?: return null)
        }

        override fun parseValue(value: String): Reminder {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Reminder): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Reminder) {
            bundle.putString(key, Json.encodeToString(value))
        }

    }
}