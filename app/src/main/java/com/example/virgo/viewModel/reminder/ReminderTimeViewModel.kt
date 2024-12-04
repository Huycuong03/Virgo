package com.example.virgo.viewModel.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.virgo.alarm.AlarmReceiver
import com.example.virgo.model.lib.Alarm
import com.example.virgo.model.lib.Reminder
import com.example.virgo.sqlite.ReminderDatabaseHelper

class ReminderTimeViewModel() : ViewModel() {
    private lateinit var sqlHelper: ReminderDatabaseHelper
    private lateinit var alarmManager: AlarmManager
    private val _reminder = mutableStateOf(Reminder(name="", duration = 0, skip = 0))
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
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
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

    fun onUpdateAlarm(originalAlarm: Alarm, updatedAlarm: Alarm) {
        val index = _alarms.indexOfFirst { it.hour == originalAlarm.hour && it.min == originalAlarm.min }
        if (index != -1) {
            _alarms[index] = updatedAlarm
        }
    }

    fun onSave(context: Context, callback: () -> Unit) {
        sqlHelper.createReminder(_reminder.value.copy(
            alarms = _alarms.toList()
        ))
        _alarms.forEach{alarm ->
            setAlarm(context, alarm)
        }
        callback()
    }

    fun setAlarm(context: Context, alarm: Alarm) {
        val intent = Intent(context, AlarmReceiver::class.java)

        val calendar = alarm.toCalendar()

        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Set the alarm to trigger at the specified time
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        // Optional: Show a toast for feedback
        Toast.makeText(context, "Alarm set for ${alarm.toString()}", Toast.LENGTH_SHORT).show()
    }

}