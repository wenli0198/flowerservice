package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans

import com.test.aoner.fanow.test.analytics.flowerutil.FlowerAnalyticsUtil

class PamAdjustInst(

    var trackerName: String = "",
    var deviceId: String = "",
    var trackerToken: String = "",
    var fbInstallReferrer: String = "",
    var costAmount: String = "",
    var costType: String = "",
    var adgroup: String = "",
    var adid: String = "",
    var campaign: String = "",
    var creative: String = "",
    var costCurrency: String = "",
    var clickLabel: String = "",
    var network: String = ""

) : PamBase() {
    init {
        this.deviceId = FlowerAnalyticsUtil.getAdId()
    }
}
