package com.perqin.copyshare

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import java.io.File

/**
 * Created by perqin on 2018/09/14.
 */
fun logToText(context: Context, text: String) {
    if (BuildConfig.DEBUG) {
        File(context.getExternalFilesDir(null), "log.txt").appendText("$text\n")
    }
}

fun logClip(context: Context, meta: ClipDescription, item: ClipData.Item) {
    logToText(context, "desc.label = ${meta.label}")
    logToText(context, "desc.mimeType list = ${(0 until meta.mimeTypeCount).joinToString { meta.getMimeType(it) }}")
    logToText(context, "item.coerceToHtmlText = ${item.coerceToHtmlText(context)}")
    logToText(context, "item.coerceToStyledText = ${item.coerceToStyledText(context)}")
    logToText(context, "item.coerceToText = ${item.coerceToText(context)}")
    logToText(context, "item.htmlText = ${item.htmlText}")
    logToText(context, "item.intent = ${item.intent}")
    logToText(context, "item.text = ${item.text}")
    logToText(context, "item.uri = ${item.uri}")
    logToText(context, "")
}
