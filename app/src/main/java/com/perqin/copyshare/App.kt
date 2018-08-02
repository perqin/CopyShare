package com.perqin.copyshare

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Author: perqin
 * Date  : 8/6/17
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(NotificationChannel(
                CHANNEL_ID_NORMAL,
                getString(R.string.channel_name_normal),
                NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = getString(R.string.channel_description_normal)
            enableLights(false)
            enableVibration(false)
        })
        nm.createNotificationChannel(NotificationChannel(
                CHANNEL_ID_HEADS_UP,
                getString(R.string.channel_name_heads_up),
                NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = getString(R.string.channel_description_heads_up)
            enableLights(false)
            enableVibration(false)
        })
        nm.createNotificationChannel(NotificationChannel(
                CHANNEL_ID_FOREGROUND_SERVICE,
                getString(R.string.channel_name_foreground_service),
                NotificationManager.IMPORTANCE_MIN
        ).apply {
            description = getString(R.string.channel_description_foreground_service)
        })
    }
}
