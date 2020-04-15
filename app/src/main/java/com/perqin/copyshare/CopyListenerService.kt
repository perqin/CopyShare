package com.perqin.copyshare

import android.app.NotificationManager
import android.app.Service
import android.content.*
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log

/**
 * Author: perqin
 * Date  : 6/16/17
 */
class CopyListenerService : Service() {
    override fun onCreate() {
        super.onCreate()
        startForeground(NOTIFICATION_ID_FOREGROUND_SERVICE, getForegroundServiceNotification(this))
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!listenerAdded) {
            listenerAdded = true
            clipboardManager.addPrimaryClipChangedListener(onPrimaryClipChangedListener)
            Log.d(TAG, "Listener added")
        }
        Log.d(TAG, "Service started")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        listenerAdded = false
        clipboardManager.removePrimaryClipChangedListener(onPrimaryClipChangedListener)
        Log.d(TAG, "Service destroyed")
    }

    private var listenerAdded = false
    private val clipboardManager by lazy { getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
    private val onPrimaryClipChangedListener = ClipboardManager.OnPrimaryClipChangedListener {
        notifyUserOfClipItem()
    }

    companion object {
        const val TAG = "CopyListenerService"
    }
}
