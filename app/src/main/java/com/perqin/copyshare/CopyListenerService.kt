package com.perqin.copyshare

import android.app.Service
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * Author: perqin
 * Date  : 6/16/17
 */
class CopyListenerService : Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!listenerAdded) {
            listenerAdded = true
            clipboardManager.addPrimaryClipChangedListener(onPrimaryClipChangedListener)
        }
        Log.d(TAG, "Service started")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        clipboardManager.removePrimaryClipChangedListener(onPrimaryClipChangedListener)
        Log.d(TAG, "Service destroyed")
    }

    var listenerAdded = false
    val clipboardManager by lazy {
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }
    val onPrimaryClipChangedListener = {
        Log.d(TAG, "Clip Item count: " + clipboardManager.primaryClip.itemCount)
        for (i in 1..clipboardManager.primaryClip.itemCount)
            Log.d(TAG, "Clip Item #" + i + ": " + clipboardManager.primaryClip.getItemAt(i - 1).coerceToText(this))
    }

    companion object {
        val TAG = "CopyListenerService"
    }
}
