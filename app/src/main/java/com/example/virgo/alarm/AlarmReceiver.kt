package com.example.virgo.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
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
            val channelId = "alarm_channel"
            val channelName = "Alarm Notifications"
            val channelDescription = "Channel for alarm notifications"

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            // Register the channel with the system
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

            val notification = Notification.Builder(context, "alarm_channel")
                .setContentTitle("Alarm Triggered")
                .setContentText("Your alarm has gone off!")
                .setSmallIcon(android.R.drawable.ic_dialog_alert) // You can use your custom icon here
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

            // Post the notification
            notificationManager.notify(0, notification)

        } else {
            // If permission is not granted, you can choose to show an explanation or handle it accordingly.
            Log.d("AlarmReceiver", "Notification permission not granted.")
        }
    }
}

