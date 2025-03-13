package com.test.aoner.fanow.test.analytics.flowerhttp

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject


val LoggerInterceptor: HttpLoggingInterceptor =
    HttpLoggingInterceptor(HttpLogInterceptor()).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }



class HttpLogInterceptor : HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        try {
            val content = message
            if (content.isNotBlank()) {
                val json = JSONObject(content)
                val jsonObject = JsonParser.parseString(json.toString()).asJsonObject
                val gson = GsonBuilder().setPrettyPrinting().create()
                val result = gson.toJson(jsonObject)
                println(result)
            } else {
                println(message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isJsonString(text: String?): Boolean {
        if (text.isNullOrBlank()) return false
        return try {
            JSONObject(text)
            true
        } catch (e: Exception) {
            false
        }
    }
}