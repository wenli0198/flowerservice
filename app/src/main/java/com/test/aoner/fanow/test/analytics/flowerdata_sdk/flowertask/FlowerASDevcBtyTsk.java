package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowertask;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.FlowerASRequestPms;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerhttp.FlowerASReqstManger;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerservices.FlowerASBtryIfo;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.constant_flower.Url_flower;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class FlowerASDevcBtyTsk implements Supplier<String> {

    private final static String TAG = "ASDeviceBatteryTask";

    private final String mTaskId;
    private final Context mContext;

    public FlowerASDevcBtyTsk(Context context, String taskId) {
        this.mContext = context;
        this.mTaskId = taskId;
    }

    @Override
    public String get() {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(1);
        AsyncBtyTsk asyncTask = new AsyncBtyTsk(mContext, latch, mTaskId);
        executorService.submit(asyncTask);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mTaskId + "";
    }

    static class AsyncBtyTsk implements Runnable {
        private final CountDownLatch latch;
        private final Context mContext;
        private final String mTaskId;

        AsyncBtyTsk(Context context, CountDownLatch latch, String taskId) {
            this.mContext = context;
            this.latch = latch;
            this.mTaskId = taskId;
        }

        @Override
        public void run() {
            FlowerASBtryIfo batteryInfo = new FlowerASBtryIfo();
            batteryInfo.getBatteryInfo(mContext, batteryInfoBean -> {
                FlowerASRequestPms netParams = new FlowerASRequestPms();
                netParams.setDBG(batteryInfoBean);
//                String result = new Gson().toJson(netParams).trim();
                String result = netParams.toParams();

                CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                    String url = StaticConfig_flower.getWholeBaseUrl()+Url_flower.PATH_BATTERY;
                    new FlowerASReqstManger.Builder().setReqId(this.mTaskId).setUrl(url).setBody(result).build().doPostRqst();

                    return mTaskId + "";
                });

                future.thenRun(latch::countDown);
            });

        }
    }

    public String getmTaskId() {
        return mTaskId;
    }

    public Context getmContext() {
        return mContext;
    }

}
