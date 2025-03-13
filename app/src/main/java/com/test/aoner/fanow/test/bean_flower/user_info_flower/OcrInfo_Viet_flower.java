package com.test.aoner.fanow.test.bean_flower.user_info_flower;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.google.gson.Gson;

import org.json.JSONObject;

public class OcrInfo_Viet_flower {

    private static class Inner {
        private static OcrInfo_Viet_flower instance = new OcrInfo_Viet_flower();
    }

    private OcrInfo_Viet_flower(){}

    public static OcrInfo_Viet_flower getInstance(){
        return Inner.instance;
    }

    public static void parse(JSONObject objJson){
        Inner.instance = new Gson().fromJson(objJson.toString(), OcrInfo_Viet_flower.class);
    }

    private String idcard_number,name,birthday,hometown,address,gender,expire_date;

    public String getIdcard_number() {
        return StringUtil_flower.getSafeString(idcard_number);
    }

    public String getName() {
        return StringUtil_flower.getSafeString(name);
    }

    public String getBirthday() {
        return StringUtil_flower.getSafeString(birthday);
    }

    public String getHometown() {
        return StringUtil_flower.getSafeString(hometown);
    }

    public String getAddress() {
        return StringUtil_flower.getSafeString(address);
    }

    public String getGender() {
        return StringUtil_flower.getSafeString(gender);
    }

    public String getExpire_date() {
        return StringUtil_flower.getSafeString(expire_date);
    }

}
