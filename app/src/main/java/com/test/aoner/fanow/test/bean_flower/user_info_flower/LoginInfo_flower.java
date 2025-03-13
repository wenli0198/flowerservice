package com.test.aoner.fanow.test.bean_flower.user_info_flower;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.google.gson.Gson;

import org.json.JSONObject;

public class LoginInfo_flower {

    private static class Inner {
        private static LoginInfo_flower instance = new LoginInfo_flower();
    }

    private LoginInfo_flower(){}

    public static LoginInfo_flower getInstance(){
        return Inner.instance;
    }

    private String type,token,userId;

    public static void parse(JSONObject objJson){
        Inner.instance = new Gson().fromJson(objJson.toString(), LoginInfo_flower.class);
    }

    public String getToken() {
        return StringUtil_flower.getSafeString(token);
    }

    public String getUserId() {
        return StringUtil_flower.getSafeString(userId);
    }

    public String getType() {
        return StringUtil_flower.getSafeString(type);
    }

}
