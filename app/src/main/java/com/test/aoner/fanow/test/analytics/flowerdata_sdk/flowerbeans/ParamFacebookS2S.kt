package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans

import android.content.res.Resources
import android.os.Build
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.PamBase
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASGeneralUtil
import com.test.aoner.fanow.test.analytics.flowerutil.FlowerAnalyticsUtil
import com.test.aoner.fanow.test.analytics.flowerutil.FlowerFacebookUtil
import com.test.aoner.fanow.test.constant_flower.Constant_flower
import org.json.JSONObject
import java.util.*


class ParamFacebookS2S(
    var advertiser_tracking_enabled: String = FlowerAnalyticsUtil.getAdEnabled().toString(),
    var application_tracking_enabled: String = FlowerAnalyticsUtil.getAdEnabled().toString(),
    var install_referrer: String = "",
    var installer_package: String = Constant_flower.NAME,
    var app_data: String = ""
) : PamBase() {
    init {
        this.install_referrer = FlowerAnalyticsUtil.getInstallReferrer()

        val jsonObject = JSONObject()
        jsonObject.put("advertiser_tracking_enabled", this.advertiser_tracking_enabled)
        jsonObject.put("application_tracking_enabled", this.application_tracking_enabled)
        jsonObject.put("install_referrer", this.install_referrer)
        jsonObject.put("installer_package", this.installer_package)

        val extinfo = mutableListOf<String>()
        //0 extinfo version a2
        extinfo.add("a2")
        //1 app package name
        extinfo.add(this.installer_package)
        //2 short version
        extinfo.add(Constant_flower.APP_VERSION_NAME)
        //3 long version
        extinfo.add("${Constant_flower.APP_VERSION} long")
        //4 os version
        extinfo.add(Build.VERSION.RELEASE + "")
        //5 device model name
        extinfo.add(Build.MODEL)
        //6 locale
        extinfo.add(Locale.getDefault().toString())
        //7 timezone abbr
        extinfo.add(Calendar.getInstance().timeZone.getDisplayName(false, TimeZone.SHORT))
        //8 carrier
        extinfo.add(FlowerASGeneralUtil.getSimOperatorName(BaseApplication_flower.getApplication_flower()))
        //9 screen width
        extinfo.add(Resources.getSystem().displayMetrics.widthPixels.toString())
        //10 screen height
        extinfo.add(Resources.getSystem().displayMetrics.heightPixels.toString())
        //11 screen density
        extinfo.add(Resources.getSystem().displayMetrics.density.toString())
        //12 cpu core
        extinfo.add(Runtime.getRuntime().availableProcessors().toString())
        //13 external storage size in GB
        extinfo.add(FlowerFacebookUtil.getTotalRom())
        //14 free space on external storage in GB
        extinfo.add(FlowerFacebookUtil.getAvalidableRom())
        //15 device timezone
        extinfo.add(Calendar.getInstance().timeZone.displayName)

        jsonObject.put("extinfo", extinfo)

        this.app_data = jsonObject.toString()
    }
}


