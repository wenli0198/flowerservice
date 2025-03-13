package com.test.aoner.fanow.test.v40.bean;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ActiveWindowConfig_flower {

    private static ActiveWindowConfig_flower instance;

    private ActiveWindowConfig_flower(){}

    public static ActiveWindowConfig_flower getInstance_flower(){
        if (instance==null) instance = new ActiveWindowConfig_flower();
        return instance;
    }

    public static void parseFlowerData(JSONObject jsonObject){
        instance = new Gson().fromJson(jsonObject.toString(), ActiveWindowConfig_flower.class);
        instance.switch1 = jsonObject.optString("switch");
    }

    private int num;
    private String content;
    private String switch1;

    public int getNum_flower() {
        return num;
    }

    public String getContent_flower() {
        return StringUtil_flower.getSafeString(content);
    }

    public boolean isSwitch_flower(){
        return StringUtil_flower.getSafeString(switch1).equalsIgnoreCase("YES");
    }

}
