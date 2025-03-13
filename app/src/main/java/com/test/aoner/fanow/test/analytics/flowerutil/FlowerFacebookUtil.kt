package com.test.aoner.fanow.test.analytics.flowerutil

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.gson.Gson
import com.test.aoner.fanow.test.analytics.flowerhttp.FlowerHttpHelper
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.ParamFacebookS2S
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASStoreUtil
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower
import com.test.aoner.fanow.test.constant_flower.Url_flower
import java.math.BigDecimal
import java.math.RoundingMode

object FlowerFacebookUtil {


    fun init(context: Application) {
        try {
            FacebookSdk.sdkInitialize(context)
            AppEventsLogger.activateApp(context)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun saveS2SInfo() {
        val params = ParamFacebookS2S()
        val body = Gson().toJson(params).trim()

        if (StaticConfig_flower.hasCountryWord()){
            val pathFormat = StaticConfig_flower.getWholeBaseUrl()+Url_flower.PATH_FIREBASE_S2S
            FlowerHttpHelper.postData(pathFormat, body)
        }
    }

    fun getTotalRom(): String {
        var totalRom = FlowerASStoreUtil.getCashTotal(BaseApplication_flower.getApplication_flower())
        if (totalRom.endsWith("TB")) {
            totalRom = totalRom.replace("TB".toRegex(), "")
            totalRom =
                BigDecimal(totalRom).multiply(BigDecimal(1024)).stripTrailingZeros().toPlainString()
        } else if (totalRom.endsWith("GB")) {
            totalRom = totalRom.replace("GB".toRegex(), "")
        } else if (totalRom.endsWith("MB")) {
            totalRom = totalRom.replace("MB".toRegex(), "")
            totalRom = BigDecimal(totalRom).divide(BigDecimal(1024), RoundingMode.HALF_UP)
                .stripTrailingZeros().toPlainString()
        }
        return totalRom
    }

    fun getAvalidableRom(): String {
        var totalRom =
            FlowerASStoreUtil.getCashCanUse(BaseApplication_flower.getApplication_flower())
        if (totalRom.endsWith("TB")) {
            totalRom = totalRom.replace("TB".toRegex(), "")
            totalRom =
                BigDecimal(totalRom).multiply(BigDecimal(1024)).stripTrailingZeros().toPlainString()
        } else if (totalRom.endsWith("GB")) {
            totalRom = totalRom.replace("GB".toRegex(), "")
        } else if (totalRom.endsWith("MB")) {
            totalRom = totalRom.replace("MB".toRegex(), "")
            totalRom = BigDecimal(totalRom).divide(BigDecimal(1024), RoundingMode.HALF_UP)
                .stripTrailingZeros().toPlainString()
        }
        return totalRom
    }


}