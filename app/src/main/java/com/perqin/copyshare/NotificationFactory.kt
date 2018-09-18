package com.perqin.copyshare

import android.app.Notification
import android.app.PendingIntent
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.os.Build
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
val requestCodeInteger = AtomicInteger()

fun getNotification(context: Context, meta: ClipDescription, item: ClipData.Item?) : Notification? {
    if (item == null) return null
    logClip(context, meta, item)
    if (!meta.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) && !meta.hasMimeType(ClipDescription.MIMETYPE_TEXT_HTML)) {
        return null
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && meta.extras?.getBoolean(ChatQuoteReceiver.EXTRA_IS_CHAT_QUOTE, false) == true) {
        return null
    }

    val sp = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
    val headsUp = sp.getBoolean(context.getString(R.string.pk_heads_up_notification), false)
    val chatQuote = sp.getBoolean(context.getString(R.string.pk_chat_quote), false)

    val urls = pickUrls(item.text)

    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_TEXT, item.text)
    intent.type = "text/plain"
    val pendingIntent = PendingIntent.getActivity(context, requestCodeInteger.incrementAndGet(),
            Intent.createChooser(intent, context.getString(R.string.chooser_title)), PendingIntent.FLAG_UPDATE_CURRENT)
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
                    addAction(R.drawable.ic_link_black_24dp, context.getString(R.string.action_open_url), PendingIntent.getActivity(
                            context, requestCodeInteger.incrementAndGet(), openUrlIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                }
                if (chatQuote) {
                    val chatQuoteIntent = Intent(context, ChatQuoteReceiver::class.java).apply {
                        action = ChatQuoteReceiver.ACTION_CHAT_QUOTE
                        putExtra(ChatQuoteReceiver.EXTRA_MESSAGE, item.text)
                    }
                    addAction(R.drawable.ic_format_quote_black_24dp, context.getString(R.string.quote), PendingIntent.getBroadcast(
                            context, requestCodeInteger.incrementAndGet(), chatQuoteIntent, PendingIntent.FLAG_UPDATE_CURRENT))
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
