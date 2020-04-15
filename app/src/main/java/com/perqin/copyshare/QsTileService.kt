package com.perqin.copyshare

import android.app.ProgressDialog
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class QsTileService : TileService() {
    private val uiHandler = Handler(Looper.getMainLooper())

    override fun onClick() {
        @Suppress("DEPRECATION")
        val dialog = ProgressDialog(this)
        dialog.setMessage(getString(R.string.detecting_clipboard_content))
        dialog.setOnShowListener {
            // Wait for the notification area collapsed
            uiHandler.postDelayed({
                notifyUserOfClipItem()
                dialog.dismiss()
            }, 1000)
        }
        showDialog(dialog)
    }
}
