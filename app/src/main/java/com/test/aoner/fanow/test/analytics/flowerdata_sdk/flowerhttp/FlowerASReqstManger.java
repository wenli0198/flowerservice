package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerhttp;

import android.util.Log;

import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;

import java.util.concurrent.CompletableFuture;

public class FlowerASReqstManger {

    private final static String TAG = "ASRequestManager";
    private final String url;
    private final String body;
    private final String reqId;

    private final OnResponseListener onResponseListener;

    private FlowerASReqstManger(Builder builder) {
        this.url = builder.url;
        this.body = builder.body;
        this.reqId = builder.reqId;
        this.onResponseListener = builder.onResponseListener;
    }

    public void doPostRqst() {
        CompletableFuture<FlowerASResp> ntwkTaskSupplier = CompletableFuture.supplyAsync(new FlowerASPostAsyncTsk(this.reqId, this.url, this.body));
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(ntwkTaskSupplier);

        allOfFuture.thenRunAsync(() -> {
            FlowerASResp resultObj = ntwkTaskSupplier.join();
            String code = resultObj.result.optString("code");

            StringBuilder stringBuffer = new StringBuilder();
            stringBuffer.append("result url: ").append(this.url);
            stringBuffer.append("\n");
            stringBuffer.append("result task id: ").append(resultObj.taskId);
            stringBuffer.append("\n");
            stringBuffer.append("result obj: ").append(resultObj.result);
            stringBuffer.append("\n");
            stringBuffer.append("result code: ").append(code);
            stringBuffer.append("\n");
            if (Integer.parseInt(code) == 0) {
                stringBuffer.append("result success");
            } else {
                stringBuffer.append("result error");
            }
            stringBuffer.append("\n");
            stringBuffer.append("==========");

            if (Constant_flower.DebugFlag) Log.d(TAG, stringBuffer.toString());

            BaseApplication_flower.getApplication_flower().runOnUiThread(() -> {
                if (this.onResponseListener != null) {
                    this.onResponseListener.onResponse(resultObj);
                }
            });
        });
    }

    public static class Builder {

        private String reqId;
        private String url;
        private String body;

        private OnResponseListener onResponseListener;

        public Builder setReqId(String reqId) {
            this.reqId = reqId;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setOnResponseListener(OnResponseListener listener) {
            this.onResponseListener = listener;
            return this;
        }

        public FlowerASReqstManger build() {
            return new FlowerASReqstManger(this);
        }
    }

    public interface OnResponseListener {
        void onResponse(FlowerASResp result);
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }

    public String getReqId() {
        return reqId;
    }

    public OnResponseListener getOnResponseListener() {
        return onResponseListener;
    }

}
