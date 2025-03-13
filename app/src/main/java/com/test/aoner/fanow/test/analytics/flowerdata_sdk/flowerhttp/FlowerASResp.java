package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerhttp;

import org.json.JSONObject;

public class FlowerASResp {

    public String taskId;
    public JSONObject result;

    public FlowerASResp(String taskId, JSONObject result) {
        this.taskId = taskId;

        if (result == null) {
            result = new JSONObject();
        }
        this.result = result;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    public String getTaskId() {
        return taskId;
    }

    public JSONObject getResult() {
        return result;
    }

}
