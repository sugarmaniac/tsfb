package com.sugarmaniac

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sugarmaniac.timeSeries.R
import com.sugarmaniac.timeSeries.composeui.CHANNEL_ID
import com.sugarmaniac.timeSeries.composeui.CHANNEL_NAME

class AlertReceiver : BroadcastReceiver() {
    lateinit var notification : Notification
    lateinit var notificationManager : NotificationManagerCompat

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p0 != null) {
            createNotificationChannel(p0)
            initNotification(p0)
            notificationManager.notify(0, notification)
        }
    }

    private fun initNotification(context: Context) {
        notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.resources.getString(R.string.notification_title))
            .setContentText(context.resources.getString(R.string.notification_text))
            .setSmallIcon(R.drawable.ic_baseline_close_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager = NotificationManagerCompat.from(context)
    }

    fun createNotificationChannel(context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.YELLOW
                enableLights(true)
            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}