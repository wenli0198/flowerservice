package com.test.aoner.fanow.test.analytics.flowerlogger

import android.util.Log

object FlowerFormatLogger {

    fun log(tag: String = "", message: String) {
        if (message.length > 4000) {
            var i = 0
            while (i < message.length) {
                if (i + 4000 < message.length) {
                    Log.i(
                        tag + i,
                        formatJson(message.substring(i, i + 4000))
                    )
                } else {
                    Log.i(
                        tag + i,
                        formatJson(message.substring(i, message.length))
                    )
                }
                i += 4000
            }
        } else {
            Log.i(tag, formatJson(message))
        }
    }

    private fun formatJson(text: String): String {
        var level = 0
        val jsonForMatStr = StringBuffer()
        for (element in text) {
            val c = element
            if (level > 0 && '\n' == jsonForMatStr[jsonForMatStr.length - 1]) {
                jsonForMatStr.append(
                    getLevelStr(
                        level
                    )
                )
            }
            when (c) {
                '{', '[' -> {
                    jsonForMatStr.append("${c}\n")
                    level++
                }
                ',' -> {
                    jsonForMatStr.append("${c}\n")
                }
                '}', ']' -> {
                    jsonForMatStr.append("\n")
                    level--
                    jsonForMatStr.append(getLevelStr(level))
                    jsonForMatStr.append(c)
                }
                else -> jsonForMatStr.append(c)
            }
        }
        return jsonForMatStr.toString()
    }

    private fun getLevelStr(level: Int): String {
        val levelStr = StringBuffer()
        for (levelI in 0 until level) {
            levelStr.append("\t")
        }
        return levelStr.toString()
    }
}