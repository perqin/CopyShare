package com.perqin.copyshare

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class DetectDialogActivity : AppCompatActivity() {
    private val uiHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        val dialog = ProgressDialog(this)
        dialog.setMessage(getString(R.string.detecting_clipboard_content))
        dialog.setOnCancelListener {
            if (!isFinishing) {
                finish()
            }
        }
        dialog.setOnDismissListener {
            if (!isFinishing) {
                finish()
            }
        }
        dialog.setOnShowListener {
            // Wait for the notification area collapsed
            uiHandler.postDelayed({
                notifyUserOfClipItem()
                dialog.dismiss()
            }, 1000)
        }
        dialog.show()
    }
}
