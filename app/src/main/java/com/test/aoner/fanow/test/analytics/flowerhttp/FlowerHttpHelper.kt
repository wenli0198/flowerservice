package com.test.aoner.fanow.test.analytics.flowerhttp

import android.util.Log
import com.test.aoner.fanow.test.analytics.flowerlogger.FlowerFormatLogger
import com.test.aoner.fanow.test.constant_flower.Constant_flower
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.sql.DriverManager.println
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit

object FlowerHttpHelper {

    private var mHttpPoolExecutorFlower: FlowerHttpPoolExecutor? = null

    private fun getHttpPoolExecutor(): FlowerHttpPoolExecutor? {
        if (mHttpPoolExecutorFlower == null) {
            val availableCoreSize = (Runtime.getRuntime().availableProcessors() / 2)
            val corePoolSize = if (availableCoreSize > 0) availableCoreSize else 1

            mHttpPoolExecutorFlower = FlowerHttpPoolExecutor(
                corePoolSize = corePoolSize,
                maximumPoolSize = corePoolSize,
                keepAliveTime = corePoolSize.toLong(),
                unit = TimeUnit.SECONDS,
                workQueue = LinkedBlockingDeque()
            ).apply {
                allowCoreThreadTimeOut(true)
            }
        }

        return mHttpPoolExecutorFlower
    }

    fun postData(targetUrl: String, body: String) {

        getHttpPoolExecutor()?.execute {
            var httpURLConnection: HttpURLConnection? = null;
            try {
                FlowerFormatLogger.log(message = "targetUrl:$targetUrl")
                FlowerFormatLogger.log(message = body)

                httpURLConnection = (URL(targetUrl).openConnection() as HttpURLConnection).apply {
                    setRequestProperty("Connection", "Keep-Alive")
                    setRequestProperty("Charset", "UTF-8")
                    setRequestProperty("Content-Type", "text/plain")
                    setRequestProperty("product", Constant_flower.PRODUCT)

                    doOutput = true
                    useCaches = false
                    connectTimeout = 120000
                    doInput = true
                    requestMethod = "POST"
                    readTimeout = 180000

                    outputStream.write(body.toByteArray())
                    outputStream.flush()
                    outputStream.close()
                }

                with(httpURLConnection) {
                    val isSuccess = (responseCode == HttpURLConnection.HTTP_OK)
                    if (isSuccess) {

                        try {
                            val reader = BufferedReader(InputStreamReader(inputStream))
                            var line: String?
                            val response = StringBuilder()
                            while (reader.readLine().also { line = it } != null) {
                                response.append(line)
                            }
                            val decContent = response.toString()
                            if (decContent.length > 4000) {
                                var i = 0
                                while (i < decContent.length) {
                                    if (i + 4000 < decContent.length) {
                                        Log.i(
                                            HttpLogger::class.java.simpleName + i,
                                            HttpLogger.formatJson(
                                                decContent.substring(
                                                    i,
                                                    i + 4000
                                                )
                                            )
                                        )
                                    } else {
                                        Log.i(
                                            HttpLogger::class.java.simpleName + i,
                                            HttpLogger.formatJson(
                                                decContent.substring(
                                                    i,
                                                    decContent.length
                                                )
                                            )
                                        )
                                    }
                                    i += 4000
                                }
                            } else {
                                Log.i(
                                    HttpLogger::class.java.simpleName,
                                    HttpLogger.formatJson(decContent)
                                )
                            }

                            reader.close()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        println("${responseCode}--${targetUrl}--post success!")
                        Log.d("", "${responseCode}--${targetUrl}--post success!")
                    } else {
                        println("${responseCode}--${targetUrl}--post fail!")
                        Log.d("", "${responseCode}--${targetUrl}--post fail!")
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace();
                println("post fail! ${e.localizedMessage}")

            } finally {
                httpURLConnection?.let {
                    try {
                        it.inputStream.close();
                        it.disconnect();
                    } catch (e: Exception) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}



