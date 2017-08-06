package com.perqin.copyshare

import android.app.Notification
import android.app.PendingIntent
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import java.util.concurrent.atomic.AtomicInteger

/**
 * Author: perqin
 * Date  : 6/16/17
 */

val CHANNEL_ID_DEFAULT = "default"
val atomicInteger = AtomicInteger()

fun getNotification(context: Context, meta: ClipDescription, item: ClipData.Item?) : Notification? {
    if (item == null) return null
    if (!meta.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) return null

    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_TEXT, item.text)
    intent.type = "text/plain"
    val pendingIntent = PendingIntent.getActivity(context, 0, Intent.createChooser(intent, context.getString(R.string.chooser_title)), PendingIntent.FLAG_UPDATE_CURRENT)
    return NotificationCompat.Builder(context, CHANNEL_ID_DEFAULT)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(context.getString(R.string.notification_title_new_text_copied))
            .setContentText(item.text)
            .setContentIntent(pendingIntent)
            .build()
}

fun getNotificationId() : Int = atomicInteger.incrementAndGet()
