package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerservices;

import android.content.Context;

import com.google.gson.Gson;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.FlowerASStoreBn;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASStoreUtil;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASUtil;

public class FlowerASStoeIfo {

    public static FlowerASStoreBn getDatas(final Context paramContext) {
        FlowerASStoreBn bean = new FlowerASStoreBn();
        try {
            bean.setContainSd(FlowerASStoreUtil.getContainSD());
            bean.setRamCanUse(FlowerASStoreUtil.getRamCanUse(paramContext));
            bean.setRamTotal(FlowerASStoreUtil.getRamTotal(paramContext));
            bean.setCashCanUse(FlowerASStoreUtil.getCashCanUse(paramContext));
            bean.setCashTotal(FlowerASStoreUtil.getCashTotal(paramContext));
            bean.setExtraSD(FlowerASStoreUtil.getExtraSD(paramContext, 1));
            bean.setInternalTotal(FlowerASStoreUtil.getTotalInternalStoreSize(paramContext));
            bean.setInternalAvailable(FlowerASStoreUtil.getAvailaInternalStoreSize(paramContext));
            bean.setRamDidUsed(FlowerASStoreUtil.getRamDidUsed(paramContext));
            bean.setCashDidUsed(FlowerASStoreUtil.getCashDidUsed(paramContext));
            bean.setSdCardTotal(FlowerASStoreUtil.getSDCardTotal(paramContext));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static String getZipString(Context paramContext) {
        String zipString = "";
        try {
            FlowerASStoreBn infoBean = getDatas(paramContext);
            String result = new Gson().toJson(infoBean).trim();
            zipString = FlowerASUtil.stringToGZIP(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zipString;
    }

}
