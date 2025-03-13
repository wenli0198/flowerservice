package com.test.aoner.fanow.test.v40.listener;

import org.json.JSONObject;

public interface OnVietSimInfoResponseListener {

    void onResponseFail(String msg);

    void onResponseSuccess(JSONObject objJson);

}
