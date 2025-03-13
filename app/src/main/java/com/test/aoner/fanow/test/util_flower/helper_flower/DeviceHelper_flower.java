package com.test.aoner.fanow.test.util_flower.helper_flower;

import android.provider.Settings;
import android.text.TextUtils;

import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;

import org.jetbrains.annotations.NotNull;

public class DeviceHelper_flower {

    private static class InnerDeviceHelper {
        private static final DeviceHelper_flower instance = new DeviceHelper_flower();
    }

    public static DeviceHelper_flower getInstance() {
        return InnerDeviceHelper.instance;
    }

    @NotNull
    public static String getPackageName() {
        String pckName = BaseApplication_flower.getApplication_flower().getPackageName();
        if (TextUtils.isEmpty(pckName)) {
            pckName = Constant_flower.NAME;
        }
        return pckName;
    }

    @NotNull
    public String getAndroidID() {
        String androidID = Settings.System.getString(BaseApplication_flower.getApplication_flower().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(androidID)) {
            return System.currentTimeMillis() + "";
        }
        if (androidID.contains("00000000")) {
            return System.currentTimeMillis() + "";
        }
        return androidID;
    }

}
