package com.test.aoner.fanow.test.analytics.flowerextend

import android.util.Base64
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPOutputStream


fun String.toGZipString(): String {
    val tempSelf = this.trim()
    var zipString = ""
    try {
        if (tempSelf.isNotBlank()) {
            val flags = Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING
            val byteArray = tempSelf.toByteArray(charset("UTF-8"))
            ByteArrayOutputStream().apply {
                GZIPOutputStream(this).apply {
                    write(byteArray)
                    close()
                }
                zipString = Base64.encodeToString(toByteArray(), flags)
                close()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return zipString
}