package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerservices;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.google.gson.Gson;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.FlowerASIstAppsBn;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASUtil;

import java.util.ArrayList;
import java.util.List;

public class FlowerASInstAppsIfo {

    public static int fetchResult = 0;

    @SuppressLint("QueryPermissionsNeeded")
    public static List<FlowerASIstAppsBn> getDatas(Context paramContext) {
        ArrayList<FlowerASIstAppsBn> arrayList = new ArrayList<>();

        try {
            PackageManager packageManager = paramContext.getPackageManager();
            List<PackageInfo> list = packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
            for (PackageInfo packageInfo : list) {
                FlowerASIstAppsBn bean = new FlowerASIstAppsBn();
                if ((packageInfo.applicationInfo.flags & 0x1) == 0) {
                    bean.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
                    bean.setAppPackageName(packageInfo.packageName);
                    bean.setAppVersionName(packageInfo.versionName);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                        bean.setAppVersionCode(String.valueOf(packageInfo.versionCode));
                    } else {
                        bean.setAppVersionCode(String.valueOf(packageInfo.getLongVersionCode()));
                    }
                    bean.setAppType("0");
                    bean.setFlags(String.valueOf(packageInfo.applicationInfo.flags));
                    bean.setInstallTime(String.valueOf(packageInfo.firstInstallTime));
                    bean.setLastTime(String.valueOf(packageInfo.lastUpdateTime));
                    bean.setDataPath(FlowerASUtil.safeString(packageInfo.applicationInfo.dataDir));
                    bean.setSourcePath(FlowerASUtil.safeString(packageInfo.applicationInfo.sourceDir));
                    arrayList.add(bean);
                }
            }
            list.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (arrayList.size() > 0) {
            fetchResult = 1;
        } else {
            fetchResult = 2;
        }

        return arrayList;
    }

    public static String getZipString(Context paramContext) {
        String zipString = "";
        try {
            List<FlowerASIstAppsBn> beans = getDatas(paramContext);
            String result = new Gson().toJson(beans).trim();
            zipString = FlowerASUtil.stringToGZIP(result);
            beans.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zipString;
    }

    public static void setFetchResult(int fetchResult) {
        FlowerASInstAppsIfo.fetchResult = fetchResult;
    }

    public static int getFetchResult() {
        return fetchResult;
    }

}
