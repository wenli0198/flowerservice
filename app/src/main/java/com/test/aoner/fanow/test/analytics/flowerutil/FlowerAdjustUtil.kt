package com.test.aoner.fanow.test.analytics.flowerutil

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustAttribution
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.AdjustEvent
import com.adjust.sdk.LogLevel
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.PamAdjustInst
import com.test.aoner.fanow.test.analytics.flowerevent.FlowerIAnalyticsEvent
import com.test.aoner.fanow.test.analytics.flowerhttp.FlowerHttpHelper
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower
import com.test.aoner.fanow.test.constant_flower.Constant_flower
import com.test.aoner.fanow.test.constant_flower.Constant_flower.AJ_EVENT_APPLY
import com.test.aoner.fanow.test.constant_flower.Constant_flower.AJ_EVENT_REGISTER
import com.test.aoner.fanow.test.constant_flower.Constant_flower.AJ_TOKEN
import com.test.aoner.fanow.test.constant_flower.Url_flower

object FlowerAdjustUtil {

    var adjsutAttribution: PamAdjustInst? = null

    fun init(context: Application) {
        if (AJ_TOKEN.isBlank()) throw Exception("Error: Adjust Token must no be null!")
        if (AJ_EVENT_REGISTER.isBlank()) throw Exception("Error: Adjust Register code must no be null!")
        if (AJ_EVENT_APPLY.isBlank()) throw Exception("Error: Adjust Apply code must no be null!")

        val config = AdjustConfig(context, AJ_TOKEN, Constant_flower.Environment)

        config.setLogLevel(LogLevel.INFO)

        config.setOnAttributionChangedListener { covertAndSaveAttributtion(it) }

        Adjust.onCreate(config)

        registerAdjustActivityLifecycle(context)

        val attribution = Adjust.getAttribution()
        covertAndSaveAttributtion(attribution)
    }


    private fun covertAndSaveAttributtion(attribution: AdjustAttribution?) {
        attribution?.let { result ->
            adjsutAttribution = PamAdjustInst()

            adjsutAttribution?.let { params ->
                params.adid = result.adid.orEmpty()
                params.network = result.network.orEmpty()
                params.adgroup = result.adgroup.orEmpty()
                params.creative = result.creative.orEmpty()
                params.costType = result.costType.orEmpty()
                params.campaign = result.campaign.orEmpty()
                params.clickLabel = result.clickLabel.orEmpty()
                params.trackerName = result.trackerName.orEmpty()
                params.trackerToken = result.trackerToken.orEmpty()
                params.costCurrency = result.costCurrency.orEmpty()
                params.fbInstallReferrer = result.fbInstallReferrer.orEmpty()
                result.costAmount?.let { costAmount ->
                    params.costAmount = costAmount.toString()
                }

                println("=====adjust attribution:")
                println(result)

                saveInstallInfo()
            }
        }
    }

    fun registerAdjustActivityLifecycle(context: Application) {
        context.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityResumed(p0: Activity) {
                Adjust.onResume()
            }

            override fun onActivityPaused(p0: Activity) {
                Adjust.onPause()
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityDestroyed(p0: Activity) {
            }
        })
    }

    fun addEvent(event: FlowerIAnalyticsEvent) {
        try {
            when (event) {
                FlowerIAnalyticsEvent.AT_REGISTER -> {
                    Adjust.trackEvent(AdjustEvent(AJ_EVENT_REGISTER))
                }
                FlowerIAnalyticsEvent.AT_LOAN_APPLY -> {
                    Adjust.trackEvent(AdjustEvent(AJ_EVENT_APPLY))
                }
                else -> {}
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun saveInstallInfo() {
        adjsutAttribution?.let { data ->
            try {

                val body = data.toParams()

                if (StaticConfig_flower.hasCountryWord()){
                    val path = StaticConfig_flower.getWholeBaseUrl()+Url_flower.PATH_ADJUST_INSTALL

                    if (TextUtils.isEmpty(path)) {
                        if (Constant_flower.DebugFlag) Log.d("---saveAdjustS2sInfo---","path is empty")
                    }else{
                        if (Constant_flower.DebugFlag) Log.d("---saveAdjustS2sInfo---","path=$path")
                        FlowerHttpHelper.postData(path, body)
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}