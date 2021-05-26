package com.example.quizapp.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.green
import com.example.quizapp.R

class AlarmReceiver : BroadcastReceiver() {
    private val CHANNEL_ID = "CHANNEL_ID"
    private val NOTIFICATION_ID = 0
    private val CHANNEL_NAME = "CHANNEL_NAME"

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null) {
            createNotificationChannel(context)

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_question_set)
                .setContentTitle("HELLO!")
                .setContentText("It's time to learn!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    private fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            lightColor.green
            enableLights(true)
        }
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}