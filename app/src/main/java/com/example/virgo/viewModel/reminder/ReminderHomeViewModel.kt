package com.example.virgo.viewModel.reminder


import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgo.model.lib.Reminder
import com.example.virgo.sqlite.ReminderDatabaseHelper
import kotlinx.coroutines.launch

class ReminderHomeViewModel() : ViewModel() {

    private val _activeReminders = mutableStateListOf<Reminder>()
    val activeReminders: State<List<Reminder>> get() = derivedStateOf {
        _activeReminders.toList()
    }

    private val _inactiveReminders = mutableStateListOf<Reminder>()
    val inactiveReminders: State<List<Reminder>> get() = derivedStateOf {
        _inactiveReminders.toList()
    }

    fun loadReminders(context: Context) {
        viewModelScope.launch {
            val dbHelper = ReminderDatabaseHelper(context)
            val allReminders = dbHelper.getAllReminders(dbHelper.readableDatabase)
            _activeReminders.addAll(allReminders.filter { it.isActive })
            _inactiveReminders.addAll(allReminders.filter { !it.isActive })
        }
    }

    fun deleteReminder(reminder: Reminder, context: Context) {
        // Remove the reminder from the active or inactive list
        if (reminder.isActive) {
            _activeReminders.remove(reminder)
        } else {
            _inactiveReminders.remove(reminder)
        }

        // Delete from the database
        val dbHelper = ReminderDatabaseHelper(context)
        dbHelper.deleteReminder(reminder)  // Assuming you have a deleteReminder method in your DBHelper
    }

}
