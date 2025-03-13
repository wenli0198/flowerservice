package com.test.aoner.fanow.test.bean_flower.user_info_flower;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;

public class ApplyDetailItem_flower {

    private String title,showName,showDetail;

    private ApplyDetailItem_flower(){}

    public static ApplyDetailItem_flower parseItem(String json){
        if (!TextUtils.isEmpty(json)) return new Gson().fromJson(json, ApplyDetailItem_flower.class);
        return new ApplyDetailItem_flower();
    }

    public ApplyDetailItem_flower(String title, String showName, String showDetail) {
        this.title = title;
        this.showName = showName;
        this.showDetail = showDetail;
    }

    public boolean isTitle(){
        return "YES".equalsIgnoreCase(title);
    }

    public String getShowDetail() {
        return StringUtil_flower.getSafeString(showDetail);
    }

    public String getShowName() {
        return StringUtil_flower.getSafeString(showName);
    }

}
