package com.example.virgo.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.virgo.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // Check if the permission is granted before showing the notification
        if (ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED) {

            // Permission is granted, proceed to show the notification
            val notification = NotificationCompat.Builder(context, "alarm_channel")
                .setContentTitle("Alarm Triggered")
                .setContentText("It's time!")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(1, notification)

        } else {
            // If permission is not granted, you can choose to show an explanation or handle it accordingly.
            Log.d("AlarmReceiver", "Notification permission not granted.")
        }
    }
}

