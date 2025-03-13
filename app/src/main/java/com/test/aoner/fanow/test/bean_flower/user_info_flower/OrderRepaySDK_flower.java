package com.test.aoner.fanow.test.bean_flower.user_info_flower;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.google.gson.Gson;

import org.json.JSONObject;

public class OrderRepaySDK_flower {

    private static class Inner {
        private static OrderRepaySDK_flower instance = new OrderRepaySDK_flower();
    }

    private OrderRepaySDK_flower(){}

    public static OrderRepaySDK_flower getInstance(){
        return Inner.instance;
    }

    private String accessCode,pubKey;

    public static void parse(JSONObject objJson){
        Inner.instance = new Gson().fromJson(objJson.toString(), OrderRepaySDK_flower.class);
    }

    public String getAccessCode() {
        return StringUtil_flower.getSafeString(accessCode);
    }

    public String getPubKey() {
        return StringUtil_flower.getSafeString(pubKey);
    }
}
