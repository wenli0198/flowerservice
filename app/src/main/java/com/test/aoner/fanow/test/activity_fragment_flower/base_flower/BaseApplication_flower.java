package com.test.aoner.fanow.test.activity_fragment_flower.base_flower;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;

import com.test.aoner.fanow.test.analytics.flowerdata_sdk.FlowerASSycManager;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.DeviceHelper_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;

import org.jetbrains.annotations.NotNull;

public class BaseApplication_flower extends Application {

    private String msg;
    private Handler mHandler;

    @NotNull
    public static BaseApplication_flower getApplication_flower() {
        return baseApplication_flower;
    }


    private void initPieWebView_flower() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = getProcessName_flower(this);
            if (!DeviceHelper_flower.getPackageName().equals(processName)) {
                WebView.setDataDirectorySuffix(processName);
            }
        }
    }

    private String getProcessName_flower(Context context) {
        if (context == null) return null;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
                if (processInfo.pid == android.os.Process.myPid()) {
                    return processInfo.processName;
                }
            }
        }
        return "";
    }

    private static BaseApplication_flower baseApplication_flower;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication_flower = this;
        try {

            mHandler = new Handler();

            initPieWebView_flower();

            UserInfoHelper_flower.getInstance().init();

            initSycDataSDK_flower();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initSycDataSDK_flower() {
        try {
            FlowerASSycManager.getInstance().init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMsg() {
        return StringUtil_flower.getSafeString(msg);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void runOnUiThread(Runnable runnable){
        if (Looper.myLooper() == Looper.getMainLooper()) runnable.run();
        else if (mHandler!=null){
            mHandler.post(runnable);
        }else Log.e("==", "Run On UI Thread FAil!!!" );
    }

}
