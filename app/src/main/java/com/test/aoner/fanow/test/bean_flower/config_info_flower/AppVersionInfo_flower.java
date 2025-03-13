package com.test.aoner.fanow.test.bean_flower.config_info_flower;

import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.google.gson.Gson;

import org.json.JSONObject;

public class AppVersionInfo_flower {

    private static class Inner {
        private static AppVersionInfo_flower instance = new AppVersionInfo_flower();
    }

    private AppVersionInfo_flower(){}

    public static AppVersionInfo_flower getInstance() {
        return Inner.instance;
    }

    public static AppVersionInfo_flower parse(JSONObject objJson) {
        Inner.instance = new Gson().fromJson(objJson.toString(), AppVersionInfo_flower.class);
        return Inner.instance;
    }

    public boolean isNeedUpdate() {
        try {
            int serversionInt = Integer.parseInt(getVersion());
            int localversionInt = Integer.parseInt(Constant_flower.APP_VERSION);
            return serversionInt > localversionInt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String searchValue;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private String remark;
    private String id;
    private String clientType;
    private String name;
    private String product;
    private String version;
    private String gpVersion;
    private String isFocus;
    private String linkUrl;
    private String isBan;
    private String banVersion;
    private String banUrl;

    public String getVersion() {
        return StringUtil_flower.getSafeString(version);
    }

    public String getLinkUrl() {
        return StringUtil_flower.getSafeString(linkUrl);
    }
}
