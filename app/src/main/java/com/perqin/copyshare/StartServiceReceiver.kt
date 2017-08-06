package com.perqin.copyshare

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager

/**
 * Author: perqin
 * Date  : 6/16/17
 */

class StartServiceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED || intent?.action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            val enable = PreferenceManager
                    .getDefaultSharedPreferences(context.applicationContext)
                    .getBoolean(context.getString(R.string.pk_enable_service), false)
            if (enable) {
                context.startService(Intent(context, CopyListenerService::class.java))
            }
        }
    }
}
