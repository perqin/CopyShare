package com.perqin.copyshare

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by perqin on 2018/08/01.
 */
class PickUrlsTest {
    @Test
    fun pickUrls() {
        val urls = com.perqin.copyshare.pickUrls("begin https://www.google.com/ middle http://cn.bing.com/?q=hello&location=great+china end")
        assertEquals(2, urls.size)
        assertEquals("https://www.google.com/", urls[0])
        assertEquals("http://cn.bing.com/?q=hello&location=great+china", urls[1])
    }
}
