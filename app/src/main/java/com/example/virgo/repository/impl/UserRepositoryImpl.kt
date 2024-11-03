package com.example.virgo.repository.impl

import android.util.Log
import com.example.virgo.model.User
import com.example.virgo.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class UserRepositoryImpl : UserRepository {
    private val ref = FirebaseDatabase.getInstance("https://virgo-238d4-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users")

    override fun save(user: User) {
        ref.child(user.id.toString()).setValue(user.toMap())
    }

    override fun findById(id: Int, callback: (User?) -> Unit) {
        ref.child(id.toString()).get().addOnSuccessListener { dataSnapshot ->
            val user = dataSnapshot.getValue(User::class.java)
            callback(user)
        }.addOnFailureListener { databaseError ->
            Log.d("FirebaseGet", databaseError.toString())
        }
    }

    override fun findByUsername(userName: String, callback: (User?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun findByEmail(email: String, callback: (User?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun update(user: User) {
        TODO("Not yet implemented")
    }

    override fun delete(user: User) {
        TODO("Not yet implemented")
    }
}