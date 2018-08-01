package com.perqin.copyshare

import java.util.regex.Pattern

/**
 * Created by perqin on 2018/08/01.
 */

val pattern: Pattern = Pattern.compile("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")

fun pickUrls(text: CharSequence): ArrayList<String> {
    val list = arrayListOf<String>()
    pattern.matcher(text).run {
        while (find()) {
            list.add(group())
        }
    }
    return list
}
