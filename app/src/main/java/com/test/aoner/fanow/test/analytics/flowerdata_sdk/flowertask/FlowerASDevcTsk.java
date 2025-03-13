package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowertask;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.FlowerASDecBn;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerhttp.FlowerASReqstManger;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerservices.FlowerASDecIfo;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.constant_flower.Url_flower;

import java.util.function.Supplier;


public class FlowerASDevcTsk implements Supplier<String> {

    private final static String TAG = "ASDeviceTask";

    private final String mTaskId;
    private final Context mContext;

    public FlowerASDevcTsk(Context context, String taskId) {
        this.mContext = context;
        this.mTaskId = taskId;
    }

    @Override
    public String get() {

        if (TextUtils.isEmpty(Url_flower.PATH_DEVICE)) {
            return mTaskId + "";
        }

        FlowerASDecBn infoBeans;
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE)) {
            infoBeans = FlowerASDecIfo.getDatas(mContext);
        } else {
            return mTaskId + "";
        }

//        String result = new Gson().toJson(infoBeans).trim();
        String result = infoBeans.toParams();

        String url = StaticConfig_flower.getWholeBaseUrl()+Url_flower.PATH_DEVICE;
        new FlowerASReqstManger.Builder().setReqId(this.mTaskId).setUrl(url).setBody(result).build().doPostRqst();

        return mTaskId + "";
    }

    public String getmTaskId() {
        return mTaskId;
    }

    public Context getmContext() {
        return mContext;
    }
}
