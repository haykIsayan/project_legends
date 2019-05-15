package com.example.android_bleed.utilities

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.android_bleed.authentication.view.AuthActivity
import com.example.android_bleed.data.models.Reminder
import android.app.NotificationChannel
import com.example.android_bleed.data.repositories.ReminderRepository


class ReminderAlarmReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "ALD.Notification.Channel.Id"
        const val NOTIFICATION_CHANNEL_NAME = "ALD.Notification.Channel.Name"
        const val NOTIFICATION_CHANNEL_DESCRIPTION = "ALD.Reminder.Notification.Channel"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        context?:return
        intent?.apply {

            val thread = Thread {

                val reminderDate = this.getStringExtra(Reminder.EXTRA_REMINDER)?:return@Thread
                val reminder = ReminderRepository(context).getReminderByDate(reminderDate)?:return@Thread

                val mainIntent = Intent(context, AuthActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, 0)

                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                createNotificationChannel(notificationManager)
                val builder = Notification.Builder(context)
                builder.setSmallIcon(android.R.drawable.ic_popup_reminder)
                    .setContentIntent(pendingIntent)
                    .setContentText(reminder.reminderMessage)
                    .setContentTitle("Hey ${reminder.authorName}!")
                    .setAutoCancel(true)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    builder.setChannelId(NOTIFICATION_CHANNEL_ID)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    notificationManager.notify(0, builder.build())
                }
            }
            thread.start()
        }
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = NOTIFICATION_CHANNEL_NAME
            val description = NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            channel.description = description
            notificationManager.createNotificationChannel(channel)
        }
    }
}