package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerservices;

import android.content.Context;

import com.google.gson.Gson;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.FlowerASNtwkBn;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASNetworkUtil;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASUtil;

public class FlowerASNtwkIfo {

    public static FlowerASNtwkBn getDatas(final Context paramContext) {
        FlowerASNtwkBn bean = new FlowerASNtwkBn();
        try {
            bean.setCurrentWifi(FlowerASNetworkUtil.getCurrentWifi(paramContext));
            bean.setConfiguredWifi(FlowerASNetworkUtil.getConfiguredWifi(paramContext));
            bean.setWifiCount(FlowerASNetworkUtil.getWifiCount(paramContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static String getZipString(Context paramContext) {
        String zipString = "";
        try {
            FlowerASNtwkBn infoBean = getDatas(paramContext);
            String result = new Gson().toJson(infoBean).trim();
            zipString = FlowerASUtil.stringToGZIP(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return zipString;
    }

}
