package com.perqin.copyshare

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Author: perqin
 * Date  : 8/6/17
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
                val sw = StringWriter()
                val pw = PrintWriter(sw)
                throwable.printStackTrace(pw)
                val st = sw.toString()
                logToText(this, "Crash caught >>>>>>>>>>>")
                logToText(this, st)

                Toast.makeText(this, "Crash caught: ${throwable.message}", Toast.LENGTH_SHORT).show()
            }
        }

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
