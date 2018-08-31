package com.perqin.copyshare

import android.app.Notification
import android.app.PendingIntent
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import androidx.core.app.NotificationCompat
import java.util.concurrent.atomic.AtomicInteger

/**
 * Author: perqin
 * Date  : 6/16/17
 */

const val CHANNEL_ID_NORMAL = "normal"
const val CHANNEL_ID_HEADS_UP = "heads_up"
const val CHANNEL_ID_FOREGROUND_SERVICE = "foreground_service"
const val NOTIFICATION_ID_FOREGROUND_SERVICE = 1
// 1 is reserved for foreground service
val atomicInteger = AtomicInteger(1)

fun getNotification(context: Context, meta: ClipDescription, item: ClipData.Item?) : Notification? {
    if (item == null) return null
    if (!meta.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) return null

    val headsUp = PreferenceManager
            .getDefaultSharedPreferences(context.applicationContext)
            .getBoolean(context.getString(R.string.pk_heads_up_notification), false)

    val urls = pickUrls(item.text)

    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_TEXT, item.text)
    intent.type = "text/plain"
    val pendingIntent = PendingIntent.getActivity(context, 0,
            Intent.createChooser(intent, context.getString(R.string.chooser_title)), PendingIntent.FLAG_ONE_SHOT)
    return NotificationCompat.Builder(context, if (headsUp) CHANNEL_ID_HEADS_UP else CHANNEL_ID_NORMAL)
            .setSmallIcon(R.drawable.ic_stat_foreground_service)
            .setContentTitle(context.getString(R.string.notification_title_new_text_copied))
            .setContentText(item.text)
            .setPriority(if (headsUp) NotificationCompat.PRIORITY_HIGH else NotificationCompat.PRIORITY_DEFAULT)
            .setDefaults(0).setSound(null)
            .setContentIntent(pendingIntent)
            .apply {
                if (urls.isNotEmpty()) {
                    val openUrlIntent = Intent(context, UrlSelectorActivity::class.java).apply {
                        putStringArrayListExtra(UrlSelectorActivity.EXTRA_URLS, urls)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    addAction(R.drawable.ic_link_black_24dp, context.getString(R.string.action_open_url),
                            PendingIntent.getActivity(context, 0, openUrlIntent, PendingIntent.FLAG_ONE_SHOT))
                }
            }
            .build()
}

fun getNotificationId() : Int = atomicInteger.incrementAndGet()

fun getForegroundServiceNotification(context: Context): Notification {
    return NotificationCompat.Builder(context, CHANNEL_ID_FOREGROUND_SERVICE)
            .setSmallIcon(R.drawable.ic_stat_foreground_service)
            .setContentTitle(context.getString(R.string.notification_title_foreground_service))
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .build()
}
