package com.example.virgo.repository

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {

    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        }
    }

    fun getString(key: String): String? {
        return sharedPreferences?.getString(key, null)
    }

    fun saveString(key: String, value: String) {
        sharedPreferences?.edit()?.putString(key, value)?.apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences?.getBoolean(key, false)?: false
    }

    fun getUID(): String{
        return sharedPreferences?.getString("uid", null)?:""
    }
}
