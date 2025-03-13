package com.test.aoner.fanow.test.analytics.flowerutil

import android.app.Application
import android.content.Context
import com.test.aoner.fanow.test.analytics.flowerevent.FlowerIAnalyticsEvent
import com.test.aoner.fanow.test.analytics.flowerhttp.FlowerHttpHelper
import com.google.gson.Gson
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.PamHitPoint
import com.test.aoner.fanow.test.util_flower.AppReviewUtil

object FlowerAnalyticsUtil {


    fun init(context: Application) {
        FlowerGoogleInstallUtil.init(context) {
            saveGoogleInstallInfo()
        }

        FlowerAdvertIdUtil.init(context) { _, _ ->
            saveGoogleInstallInfo()
        }

        FlowerSycDeviceUtil.init(context)

        FlowerAdjustUtil.init(context)

        FlowerFacebookUtil.init(context)

        AppReviewUtil.init(context)

    }

    fun initDevice(context: Application) {
        FlowerSycDeviceUtil.init(context)
        saveFirebaseS2SInfo()
    }

    fun saveFirebaseAndFacebookS2SInfo() {
        saveFirebaseS2SInfo()
        saveFacebookS2SInfo()
    }

    fun addEvent(event: FlowerIAnalyticsEvent) {
        FlowerAdjustUtil.addEvent(event)
    }

    fun saveGoogleInstallInfo() {
        FlowerGoogleInstallUtil.saveS2sInfo()
    }

    fun saveDeviceS2SInfo() {
        FlowerSycDeviceUtil.saveS2SInfo()
    }

    fun saveAppsflyerInstall(targetUrlList: List<String>) {
//        AppsflyerUtil.saveInstallInfo(targetUrlList)
    }

    fun saveAdjustInstall() {
        FlowerAdjustUtil.saveInstallInfo()
    }

    fun saveUsageStatsS2SInfo(context: Application, targetUrlList: List<String>) {
//        UsageStatsUtil.saveS2SInfo(context, targetUrlList)
    }

    fun saveFirebaseS2SInfo() {
        FlowerFirebaseUtil.saveS2SInfo()
    }

    fun saveFacebookS2SInfo() {
        FlowerFacebookUtil.saveS2SInfo()
    }

    fun saveHitPointInfo(type: String, remark: String) {
        try {
            val params = PamHitPoint(type = type, remark = remark)
            val body = Gson().toJson(params).trim()
            FlowerHttpHelper.postData("", body)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun checkUsageStatsPermission(
        context: Application,
        permissionGrantListener: (Boolean) -> Unit
    ) {
//        UsageStatsUtil.checkAppUsagePermission(context, permissionGrantListener)
    }

    fun startToUsageStatsPermissionSettingPage(context: Context) {
//        UsageStatsUtil.startToUsageStatsPermissionSettingPage(context)
    }

    fun getInstallReferrer(): String {
        return FlowerGoogleInstallUtil.getInstallReferrer()
    }

    fun getAdId(): String {
        return FlowerAdvertIdUtil.getAdId()
    }

    fun getAdEnabled(): Int {
        return FlowerAdvertIdUtil.getAdEnabled()
    }

    fun getAndroidId(): String {
        return FlowerAdvertIdUtil.getAndroidId()
    }

}