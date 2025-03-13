package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerhttp;

import java.util.function.Supplier;

public class FlowerASPostAsyncTsk implements Supplier<FlowerASResp> {

    private final String taskId;
    private final String body;
    private final String url;


    public FlowerASPostAsyncTsk(String taskId, String url, String body) {
        this.taskId = taskId;
        this.url = url;
        this.body = body;
    }

    @Override
    public FlowerASResp get() {
        FlowerASPostSyncTsk postSyncTask = new FlowerASPostSyncTsk(this.taskId, this.url, this.body);
        return postSyncTask.getASResp();
    }

    public String getTaskId() {
        return taskId;
    }

    public String getUrl() {
        return url;
    }


}
