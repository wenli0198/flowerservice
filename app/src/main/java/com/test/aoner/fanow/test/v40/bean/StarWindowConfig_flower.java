package com.test.aoner.fanow.test.v40.bean;


import org.json.JSONObject;

public class StarWindowConfig_flower {

    public static void parseFlowerData(JSONObject objJson){
        JSONObject activeWindow = objJson.optJSONObject("activeWindow");
        JSONObject normalWindow = objJson.optJSONObject("normalWindow");

        if (activeWindow!=null) ActiveWindowConfig_flower.parseFlowerData(activeWindow);
        if (normalWindow!=null) NormalWindowConfig_flower.parseFlowerData(normalWindow);

    }

}
