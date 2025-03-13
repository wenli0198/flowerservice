package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans

import android.content.pm.PackageManager
import android.os.Build
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.PamBase
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower
import com.test.aoner.fanow.test.analytics.flowerutil.FlowerAnalyticsUtil
import com.test.aoner.fanow.test.analytics.flowerutil.FlowerFirebaseUtil
import com.test.aoner.fanow.test.constant_flower.Constant_flower
import java.util.*

class FlowerParamFirebaseS2S(

    var name: String = Constant_flower.NAME,

    var OSAndVersion: String = "Android ${Build.VERSION.RELEASE}",

    var locale: String = Locale.getDefault().toString(),

    var device: String = Build.MODEL,

    var appVersion: String = Constant_flower.APP_VERSION,

    var sdkVersion: String = Constant_flower.APP_VERSION,

    var build: String = "Build/${Build.ID}",

    var lat: String = FlowerAnalyticsUtil.getAdEnabled().toString(),

    var firebaseAppId: String = FlowerFirebaseUtil.getFirebaseAppId(),

    var ga4AppInstanceId: String = FlowerFirebaseUtil.getGa4InstanceId()
) : PamBase() {
    init {
        val vVersion =
            BaseApplication_flower.getApplication_flower().packageManager.getPackageInfo(
                BaseApplication_flower.getApplication_flower().packageName,
                PackageManager.GET_META_DATA
            ).versionName
        this.appVersion = vVersion
        this.sdkVersion = vVersion
    }
}


