package com.perqin.copyshare

import android.app.NotificationManager
import android.content.ClipboardManager
import android.content.Context
import android.os.Handler
import android.os.Looper

private val uiHandler = Handler(Looper.getMainLooper())

fun Context.notifyUserOfClipItem() {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    if (clipboardManager.primaryClip != null && clipboardManager.primaryClipDescription != null) {
        for (i in 1..clipboardManager.primaryClip!!.itemCount) {
            val meta = clipboardManager.primaryClipDescription!!
            val item = clipboardManager.primaryClip!!.getItemAt(i - 1)
            // Now we just use Notification
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notification = getNotification(this, meta, item)
            if (notification != null) {
                val id = getNotificationId()
                notificationManager.notify(id, notification)
                uiHandler.postDelayed({
                    notificationManager.cancel(id)
                }, 8000)
            }
        }
    }
}