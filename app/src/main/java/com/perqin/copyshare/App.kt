package com.perqin.copyshare

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

/**
 * Author: perqin
 * Date  : 8/6/17
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = CHANNEL_ID_DEFAULT
            val name = getString(R.string.channel_name_default)
            val description = getString(R.string.channel_description_default)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.enableLights(false)
            channel.enableVibration(false)
            nm.createNotificationChannel(channel)
        }
    }
}
