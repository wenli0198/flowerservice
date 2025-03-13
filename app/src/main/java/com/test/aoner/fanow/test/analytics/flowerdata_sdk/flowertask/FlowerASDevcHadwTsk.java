package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowertask;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.FlowerASRequestPms;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerhttp.FlowerASReqstManger;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerservices.FlowerASHdwIfo;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.constant_flower.Url_flower;

import java.util.function.Supplier;


public class FlowerASDevcHadwTsk implements Supplier<String> {

    private final static String TAG = "ASDeviceHardwareTask";

    private final String mTaskId;
    private final Context mContext;

    public FlowerASDevcHadwTsk(Context context, String taskId) {
        this.mContext = context;
        this.mTaskId = taskId;
    }

    @Override
    public String get()  {

        String infoBeans = "";
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE)) {
            infoBeans = FlowerASHdwIfo.getZipString(mContext);

        } else {
            return mTaskId + "";
        }

        FlowerASRequestPms netParams = new FlowerASRequestPms();
        netParams.setDHG(infoBeans);
//        String result = new Gson().toJson(netParams).trim();
        String result = netParams.toParams();

        String url = StaticConfig_flower.getWholeBaseUrl()+Url_flower.PATH_HARDWARE;
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
