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
        val normalChannel = NotificationChannel(CHANNEL_ID_NORMAL, getString(R.string.channel_name_normal), NotificationManager.IMPORTANCE_LOW)
        normalChannel.description = getString(R.string.channel_description_normal)
        normalChannel.enableLights(false)
        normalChannel.enableVibration(false)
        nm.createNotificationChannel(normalChannel)
        val headsUpChannel = NotificationChannel(CHANNEL_ID_HEADS_UP, getString(R.string.channel_name_heads_up), NotificationManager.IMPORTANCE_HIGH)
        headsUpChannel.description = getString(R.string.channel_description_heads_up)
        headsUpChannel.enableLights(false)
        headsUpChannel.enableVibration(false)
        nm.createNotificationChannel(headsUpChannel)
    }
}
