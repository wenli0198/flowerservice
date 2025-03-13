package com.test.aoner.fanow.test.analytics.flowerdata_sdk;

import android.annotation.SuppressLint;
import android.content.Context;

import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowertask.FlowerASDevcAppsTsk;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowertask.FlowerASDevcBtyTsk;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowertask.FlowerASDevcHadwTsk;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowertask.FlowerASDevcSsTsk;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowertask.FlowerASDevcStorTsk;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowertask.FlowerASDevcTsk;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowertask.FlowerASDevcWfiTsk;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowertask.FlowerOnDevcTskCmple;

import java.util.concurrent.CompletableFuture;

public class FlowerASSycManager {


    private final static String TAG = "ASSycManager";

    private FlowerASSycManager() {
    }

    @SuppressLint("StaticFieldLeak")
    private static FlowerASSycManager instance;

    public static FlowerASSycManager getInstance() {
        if (instance == null) {
            synchronized (FlowerASSycManager.class) {
                instance = new FlowerASSycManager();
            }
        }
        return instance;
    }

    public Context mContext;

    private FlowerOnDevcTskCmple mOnDevcTskCmpleFlower;

    public void init(Context applicationContext) {
        mContext = applicationContext;
    }

    public void sycData(FlowerOnDevcTskCmple completeListener) {
        try {
            this.mOnDevcTskCmpleFlower = completeListener;

            FlowerASDevcSsTsk Flower = new FlowerASDevcSsTsk(mContext, FlowerASBuilder.T_MAG);


            CompletableFuture<String> device1Task = CompletableFuture.supplyAsync(new FlowerASDevcBtyTsk(mContext, FlowerASBuilder.T_BTY));
            CompletableFuture<String> device2Task = CompletableFuture.supplyAsync(new FlowerASDevcStorTsk(mContext, FlowerASBuilder.T_STO));
            CompletableFuture<String> device3Task = CompletableFuture.supplyAsync(new FlowerASDevcAppsTsk(mContext, FlowerASBuilder.T_APL));
            CompletableFuture<String> device4Task = CompletableFuture.supplyAsync(new FlowerASDevcWfiTsk(mContext, FlowerASBuilder.T_NTW));
            CompletableFuture<String> device5Task = CompletableFuture.supplyAsync(new FlowerASDevcHadwTsk(mContext, FlowerASBuilder.T_HDW));
            CompletableFuture<String> device6Task = CompletableFuture.supplyAsync(new FlowerASDevcTsk(mContext, FlowerASBuilder.T_DIE));
            CompletableFuture<String> device7Task = CompletableFuture.supplyAsync(Flower);

            CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(
                    device1Task,
                    device2Task,
                    device3Task,
                    device4Task,
                    device5Task,
                    device6Task,
                    device7Task);


            allOfFuture.thenRun(() -> {

                BaseApplication_flower.getApplication_flower().runOnUiThread(() -> {
                    if (mOnDevcTskCmpleFlower != null) {
                        mOnDevcTskCmpleFlower.onAllCompleted();
                    }
                });

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setInstance(FlowerASSycManager instance) {
        FlowerASSycManager.instance = instance;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}
