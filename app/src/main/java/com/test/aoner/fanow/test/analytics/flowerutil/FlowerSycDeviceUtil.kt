package com.test.aoner.fanow.test.analytics.flowerutil

import android.app.Application
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.FlowerASSycManager
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower

object FlowerSycDeviceUtil {


    fun init(context: Application) {
        try {

            FlowerASSycManager.getInstance().init(context)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveS2SInfo() {
        try {
            if (UserInfoHelper_flower.getInstance().didLogin()) {
                FlowerASSycManager.getInstance().sycData {}
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}