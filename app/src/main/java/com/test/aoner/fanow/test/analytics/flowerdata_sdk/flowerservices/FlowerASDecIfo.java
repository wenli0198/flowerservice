package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerservices;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.FlowerASDecBn;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASGeneralUtil;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASHarewareUtil;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASNetworkUtil;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASStoreUtil;

public class FlowerASDecIfo {

    public static FlowerASDecBn getDatas(Context paramContext) {
        FlowerASDecBn bean = new FlowerASDecBn();
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(paramContext, Manifest.permission.READ_PHONE_STATE)) {
            try {
                bean.setMac(FlowerASNetworkUtil.getMacAddress(paramContext));
                bean.setImei(com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASGeneralUtil.getImei(paramContext));
                bean.setLaguage(FlowerASGeneralUtil.getLanguage(paramContext));
                bean.setArea(FlowerASGeneralUtil.getArea(paramContext));
                bean.setScreenHeight(FlowerASGeneralUtil.getDeviceHeight(paramContext));
                bean.setScreenWidth(FlowerASGeneralUtil.getDeviceWidth(paramContext));
                bean.setNetworkData(FlowerASGeneralUtil.getSimOperatorName(paramContext));
                bean.setFrontCameraPixels(FlowerASHarewareUtil.getFrontCameraPixels(paramContext));
                bean.setRearCameraPixels(FlowerASHarewareUtil.getBackCameraPixels(paramContext));
                bean.setRa(FlowerASStoreUtil.getRamTotal(paramContext));
                bean.setRo(FlowerASStoreUtil.getCashTotal(paramContext));
                bean.setIp(FlowerASNetworkUtil.getIPAddress(paramContext));
                bean.setIp2(FlowerASNetworkUtil.getIPAddress(paramContext));
                bean.setNetworkEnvironment(FlowerASGeneralUtil.getNetworkType(paramContext));
                bean.setCu(FlowerASGeneralUtil.getCpuModel(paramContext));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

}
