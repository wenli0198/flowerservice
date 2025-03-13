package com.test.aoner.fanow.test.bean_flower.user_info_flower;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class LoanLimitInfo_flower {

    private static class Inner {
        private static LoanLimitInfo_flower instance = new LoanLimitInfo_flower();
    }

    private LoanLimitInfo_flower(){}

    public static LoanLimitInfo_flower getInstance(){
        return Inner.instance;
    }

    private String newUserFlag;
    private ArrayList<LinkedHashMap<String,String>> limit;

    public static void parse(JSONObject objJson){
        Inner.instance = new Gson().fromJson(objJson.toString(), LoanLimitInfo_flower.class);
    }

    public boolean isNewUSer() {
        return StringUtil_flower.getSafeString(newUserFlag).equalsIgnoreCase("YES");
    }

    public String getLimitAmount(){
        if (limit == null||limit.size()==0) return "";
        return limit.get(0).get("amount");
    }

    public String getLimitIncreasedAmount1(){
        if (limit == null||limit.size()==0) return "";
        return limit.get(0).get("increasedAmount1");
    }

    public String getLimitIncreasedAmount2(){
        if (limit == null||limit.size()==0) return "";
        return limit.get(0).get("increasedAmount2");
    }

}
