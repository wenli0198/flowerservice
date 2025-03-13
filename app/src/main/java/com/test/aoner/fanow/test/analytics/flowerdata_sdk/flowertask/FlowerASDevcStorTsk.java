package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowertask;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.FlowerASRequestPms;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerhttp.FlowerASReqstManger;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerservices.FlowerASStoeIfo;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.constant_flower.Url_flower;

import java.util.function.Supplier;


public class FlowerASDevcStorTsk implements Supplier<String> {

    private final static String TAG = "ASDeviceStoreTask";

    private final String mTaskId;
    private final Context mContext;

    public FlowerASDevcStorTsk(Context context, String taskId) {
        this.mContext = context;
        this.mTaskId = taskId;
    }

    @Override
    public String get()  {

        String infoBeans = FlowerASStoeIfo.getZipString(mContext);
        if (TextUtils.isEmpty(infoBeans)) {
            return mTaskId + "";
        }

        FlowerASRequestPms netParams = new FlowerASRequestPms();
        netParams.setDSG(infoBeans);
//        String result = new Gson().toJson(netParams).trim();
        String result = netParams.toParams();

        String url = StaticConfig_flower.getWholeBaseUrl()+Url_flower.PATH_STORE;
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
