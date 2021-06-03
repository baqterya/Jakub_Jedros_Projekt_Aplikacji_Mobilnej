package com.example.quizapp.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.example.quizapp.view.activity.MainActivity
import com.example.quizapp.R

class NotificationUtil {
    companion object {
        private const val CHANNEL_ID_TIMER = "menu_timer"
        private const val CHANNEL_NAME_TIMER = "timer_app_timer"
        private const val TIMER_ID = 0

        fun showTimerExpired(context: Context) {
            val builder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            builder.setContentTitle("It's time to learn!")
                .setContentIntent(getPendingIntentWithStack(context, MainActivity::class.java))
                .setOngoing(false)

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)
            manager.notify(TIMER_ID, builder.build())
        }

        private fun getBasicNotificationBuilder(context: Context, channelId: String, playSound: Boolean)
                : NotificationCompat.Builder {
            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_question_set)
                .setAutoCancel(true)
                .setDefaults(0)
            if (playSound)
                builder.setSound(notificationSound)
            return builder
        }

        private fun <T> getPendingIntentWithStack(context: Context, javaClass: Class<T>): PendingIntent {
            val resultIntent = Intent(context, javaClass)
            resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(javaClass)
            stackBuilder.addNextIntent(resultIntent)

            return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        private fun NotificationManager.createNotificationChannel(channelId: String, channelName: String, playSound: Boolean) {
            val channelImportance = if (playSound) NotificationManager.IMPORTANCE_DEFAULT
            else NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(channelId, channelName, channelImportance)
            channel.enableLights(true)
            channel.lightColor = Color.BLUE
            this.createNotificationChannel(channel)
        }
    }
}