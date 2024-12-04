package com.example.virgo.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.virgo.model.lib.Message
import com.example.virgo.repository.SharedPreferencesManager
import com.google.firebase.Timestamp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class TelemedicineViewModel : ViewModel(){
    private val ref = SharedPreferencesManager.getString("uid")?.let { uid ->
        FirebaseDatabase
            .getInstance(SharedPreferencesManager.getString("firebase_database")?:"")
            .getReference("telemedicine")
            .child(uid)
    }
    private val stg = FirebaseStorage.getInstance().getReference("images")
    private val _messageList = mutableStateListOf<Message>()
    private val _input = mutableStateOf("")

    val messageList: State<List<Message>> get() = derivedStateOf {
        _messageList.toList()
    }
    val input: State<String> get() = _input

    init {
        ref?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val tmp = mutableListOf<Message>()
                snapshot.children.forEach{ msg ->
                    msg.getValue<Message>()?.let {
                        tmp.add(it)
                    }
                }
                _messageList.clear()
                _messageList.addAll(tmp.sortedWith(compareBy { it.timestamp }).reversed())
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun onChangeInput(text: String) {
        _input.value = text
    }

    fun onSendText() {
        if (_input.value.isNotEmpty()) {
            val message = Message(
                user = true,
                timestamp = Timestamp.now().seconds,
                text = _input.value
            )
            _input.value = ""
            ref?.push()?.setValue(message)
        }
    }

    fun onSelectImage(uris: List<Uri>) {
        uris.forEachIndexed { index, uri ->
            val fileName = UUID.randomUUID().toString()
            val task = stg.child(fileName).putFile(uri)
            task.addOnSuccessListener {
                stg.child(fileName).downloadUrl.addOnSuccessListener { url ->
                    val message = Message(
                        user = true,
                        timestamp = Timestamp.now().seconds,
                        image = url.toString()
                    )
                    ref?.push()?.setValue(message)
                }
            }
        }
    }
}