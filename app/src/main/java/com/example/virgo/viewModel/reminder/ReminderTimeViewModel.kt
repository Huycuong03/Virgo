package com.example.virgo.viewModel.reminder

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.virgo.model.lib.Alarm
import com.example.virgo.model.lib.Reminder
import com.example.virgo.sqlite.ReminderDatabaseHelper

class ReminderTimeViewModel() : ViewModel() {
    private lateinit var sqlHelper: ReminderDatabaseHelper
    private val _reminder = mutableStateOf(Reminder("", duration = 0, skip = 0))
    private val _alarms = mutableStateListOf(Alarm())
    private val _selectedAlarm = mutableStateOf<Alarm?>(null)

    val reminder: State<Reminder> get() = _reminder
    val alarms: State<List<Alarm>>
        get() = derivedStateOf {
        _alarms.toList()
    }

    fun init(reminder: Reminder, context: Context) {
        _reminder.value = reminder
        sqlHelper = ReminderDatabaseHelper(context)
    }

    fun addNewAlarm() {
        _alarms.add(Alarm())
    }

    fun removeAlarm(alarm: Alarm) {
        _alarms.remove(alarm)
    }

    fun onSelectAlarm(alarm: Alarm) {
        _selectedAlarm.value = alarm
    }

    fun onSwitchActive(isActive: Boolean) {
        _reminder.value = _reminder.value.copy(isActive = isActive)
    }

    fun onSwitchNote(note: Boolean) {
        _reminder.value = _reminder.value.copy(note = note)
    }

    fun onUpdateAlarm(alarm: Alarm) {
        _alarms.remove(_selectedAlarm.value)
        _alarms.add(alarm)
    }

    fun onSave(callback: () -> Unit) {
        sqlHelper.createReminder(_reminder.value.copy(
            alarms = _alarms.toList()
        ))
        callback()
    }

}