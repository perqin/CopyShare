package com.perqin.copyshare

import android.content.*
import android.os.Build
import android.os.PersistableBundle
import android.widget.Toast
import androidx.core.content.ContextCompat

class ChatQuoteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (ACTION_CHAT_QUOTE == intent.action) {
            val message = intent.getStringExtra(EXTRA_MESSAGE)
            val quotedMessage = "「$message」\n- - - - - - - - - - - - - - -\n"
            val clip = ClipData.newPlainText(context.getString(R.string.quoted_message), quotedMessage)
                    .apply {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            description.extras = PersistableBundle().apply {
                                putBoolean(EXTRA_IS_CHAT_QUOTE, true)
                            }
                        }
                    }
            ContextCompat.getSystemService(context, ClipboardManager::class.java)?.primaryClip = clip
            Toast.makeText(context, context.getString(R.string.quoted_message_has_been_copied_to_clipboard), Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val ACTION_CHAT_QUOTE = "${BuildConfig.APPLICATION_ID}.action.CHAT_QUOTE"
        const val EXTRA_MESSAGE = "MESSAGE"
        const val EXTRA_IS_CHAT_QUOTE = "IS_CHAT_QUOTE"
    }
}
