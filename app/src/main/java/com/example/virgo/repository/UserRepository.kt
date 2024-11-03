package com.example.virgo.repository

import com.example.virgo.model.User

interface UserRepository {
    fun save(user: User)
    fun findById(id: Int, callback: (User?) -> Unit)
    fun findByUsername(userName: String, callback: (User?) -> Unit)
    fun findByEmail(email: String, callback: (User?) -> Unit)
    fun update(user: User)
    fun delete(user: User)
}