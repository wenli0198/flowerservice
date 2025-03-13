package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerservices;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.FlowerASHdwBn;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASGeneralUtil;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASHarewareUtil;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASNetworkUtil;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASUtil;

public class FlowerASHdwIfo {


    public static FlowerASHdwBn getDatas(final Context paramContext) {
        FlowerASHdwBn bean = new FlowerASHdwBn();
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(paramContext, Manifest.permission.READ_PHONE_STATE)) {
            try {
                bean.setPhoneType(FlowerASGeneralUtil.getPhoneType(paramContext));
                bean.setVersionCode(FlowerASGeneralUtil.getVersionCode(paramContext));
                bean.setVersionName(FlowerASGeneralUtil.getVersionName(paramContext));
                bean.setAoidId(FlowerASGeneralUtil.getAndroidID(paramContext));
                bean.setTelephony(FlowerASGeneralUtil.getSimOperatorName(paramContext));
                bean.setIsVpn(FlowerASNetworkUtil.getVpnState(paramContext));
                bean.setIsPxyPt(FlowerASNetworkUtil.getIsWifiProxy(paramContext));
                bean.setIsDebug(FlowerASGeneralUtil.getIsDebug(paramContext));
                bean.setSState(FlowerASGeneralUtil.getSimCardState(paramContext));
                bean.setRot(FlowerASHarewareUtil.getIsRoot(paramContext));
                bean.setPysSize(FlowerASHarewareUtil.getPhySicalSize(paramContext));
                bean.setDisplayLanguage(FlowerASGeneralUtil.getDisplayLanguage(paramContext));
                bean.setIso3Language(FlowerASGeneralUtil.getIso3Language(paramContext));
                bean.setIso3Country(FlowerASGeneralUtil.getIso3Country(paramContext));
                bean.setNetworkOperatorName(FlowerASGeneralUtil.getSimOperatorName(paramContext));
                bean.setNetworkType(FlowerASGeneralUtil.getNetworkType(paramContext));
                bean.setTimeZoneId(FlowerASGeneralUtil.getTimeZoneId(paramContext));
                bean.setElapsedRealtime(FlowerASGeneralUtil.getElapsedRealtime(paramContext));
                bean.setSensorList(FlowerASHarewareUtil.getSensorList(paramContext));
                bean.setLastBootTime(FlowerASGeneralUtil.getLastBootTime(paramContext));
                bean.setRootJailbreak(FlowerASHarewareUtil.getIsRoot(paramContext));
                bean.setKeyboard(FlowerASHarewareUtil.getKeyboard(paramContext));
                bean.setIsSimulator(FlowerASHarewareUtil.getIsSimulator(paramContext));
                bean.setDbm(FlowerASHarewareUtil.getMobileDbm(paramContext));
                bean.setPhoneNumber(FlowerASGeneralUtil.getPhoneNumber(paramContext));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    public static String getZipString(Context paramContext) {
        String zipString = "";
        try {
            FlowerASHdwBn infoBean = getDatas(paramContext);
            String result = new Gson().toJson(infoBean).trim();
            zipString = FlowerASUtil.stringToGZIP(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zipString;
    }

}
