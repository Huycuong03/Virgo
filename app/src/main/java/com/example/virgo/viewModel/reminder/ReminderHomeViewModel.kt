package com.example.virgo.viewModel.reminder


import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgo.model.lib.Reminder
import com.example.virgo.sqlite.ReminderDatabaseHelper
import kotlinx.coroutines.launch

class ReminderHomeViewModel() : ViewModel() {
    private lateinit var dbHelper : ReminderDatabaseHelper
    private val db = dbHelper.readableDatabase

    private val _activeReminders = mutableListOf<Reminder>()
    val activeReminders: State<List<Reminder>> get() = derivedStateOf {
        _activeReminders.toList()
    }

    private val _inactiveReminders = mutableListOf<Reminder>()
    val inactiveReminders: State<List<Reminder>> get() = derivedStateOf {
        _inactiveReminders.toList()
    }

    fun loadReminders() {
        viewModelScope.launch {
            val allReminders = dbHelper.getAllReminders(db)
            _activeReminders.addAll(allReminders.filter { it.isActive })
            _inactiveReminders.addAll(allReminders.filter { !it.isActive })
        }
    }

}
