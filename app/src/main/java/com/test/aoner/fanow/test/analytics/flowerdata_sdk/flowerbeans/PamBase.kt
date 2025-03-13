package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans

import com.google.gson.Gson
import com.test.aoner.fanow.test.analytics.flowerutil.FlowerAnalyticsUtil
import com.test.aoner.fanow.test.constant_flower.Constant_flower
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

open class PamBase(

    var aduid: String = "",
    var androidId: String = "",
    var token: String = "",
    var isLimitAdTrackingEnabled: String = "",
    var apiVersion: String = "v3",
    var userId: String = "",
    var product: String = "",
    var aduidPath: String = "",
    var advertId: String = "",
    var version: String = "",
    var clientType: String = ""

) {
    init {

        this.userId = UserInfoHelper_flower.getInstance().userId
        this.aduidPath = FlowerAnalyticsUtil.getAndroidId()
        this.aduid = FlowerAnalyticsUtil.getAdId()
        this.androidId = FlowerAnalyticsUtil.getAndroidId()
        this.clientType = "ANDROID"
        this.version = Constant_flower.APP_VERSION
        this.advertId = FlowerAnalyticsUtil.getAdId()
        this.isLimitAdTrackingEnabled = FlowerAnalyticsUtil.getAdEnabled().toString()
        this.token = UserInfoHelper_flower.getInstance().token
        this.product = Constant_flower.PRODUCT

    }

    open fun toParams(): String {
        return Gson().toJson(this).trim()
    }

    open fun toRequestBody(path: String): RequestBody {
        val encrypted: String = toParams()
        val mediaType = "text/plain".toMediaTypeOrNull()
        return encrypted.toRequestBody(mediaType)
    }
}