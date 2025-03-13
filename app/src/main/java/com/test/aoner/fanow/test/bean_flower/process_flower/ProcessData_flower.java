package com.test.aoner.fanow.test.bean_flower.process_flower;

import android.text.TextUtils;
import android.util.Log;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.google.gson.JsonArray;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProcessData_flower {

    private ProcessData_flower(){}

    private String title;
    private String title_tanz;
    private String hint;
    private String hint_tanz;
    private String paramName;
    private String action;
    private String isMust;
    private JsonArray values;
    private String paramNameState;
    private String paramNameCity;
    private String paramNameArea;
    private String paramNameBankName;
    private String paramNameBankCode;

    private ArrayList<String> relationships;
    private String[] bankNames;

    private ArrayList<JSONObject> valueJSONs;

    public String getTitle() {
        if (TextUtils.isEmpty(title_tanz)) return StringUtil_flower.getSafeString(title);
        return StringUtil_flower.getSafeString(UserInfoHelper_flower.getInstance().isLanguageEn() ? title:title_tanz);
    }

    public String getHint() {
        if (TextUtils.isEmpty(hint_tanz)) return StringUtil_flower.getSafeString(hint);
        return StringUtil_flower.getSafeString(UserInfoHelper_flower.getInstance().isLanguageEn() ? hint:hint_tanz);
    }

    public String getParamName() {
        return StringUtil_flower.getSafeString(paramName);
    }

    public String getAction() {
        return StringUtil_flower.getSafeString(action);
    }

    public boolean isMustInput(){
        return !StringUtil_flower.getSafeString(isMust).equals("NO");
    }

    public void setRelationships(ArrayList<String> relationships) {
        this.relationships = relationships;
    }

    public void setBankNames(String[] bankNames) {
        this.bankNames = bankNames;
    }

    public String[] getTitlesOfValue(){

        if (relationships!=null) return relationships.toArray(new String[0]);

        if (bankNames!=null) return bankNames;

        int size = values.size();

        if (valueJSONs==null||valueJSONs.isEmpty()){
            valueJSONs = new ArrayList<>();
            for (int i=0;i<size;i++){
                try {
                    valueJSONs.add(new JSONObject(values.get(i).toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        size = valueJSONs.size();
        String[] result = new String[size];

        for (int i=0;i<size;i++){
            if (valueJSONs.get(i).has("title_tanz"))
                result[i] = valueJSONs.get(i).optString(UserInfoHelper_flower.getInstance().isLanguageEn() ? "title":"title_tanz");
            else result[i] = valueJSONs.get(i).optString("title");
        }

        return result;
    }

    public String getParamNameState() {
        return StringUtil_flower.getSafeString(paramNameState);
    }

    public String getParamNameCity() {
        return StringUtil_flower.getSafeString(paramNameCity);
    }

    public String getParamNameArea() {
        return StringUtil_flower.getSafeString(paramNameArea);
    }

    public String getParamNameBankName() {
        return StringUtil_flower.getSafeString(paramNameBankName);
    }

    public String getParamNameBankCode() {
        return StringUtil_flower.getSafeString(paramNameBankCode);
    }

    public String getValueOfTitle(String title){

        Log.w("===", title);

        if (valueJSONs==null||valueJSONs.isEmpty()){
            valueJSONs = new ArrayList<>();
            for (int i=0;i<values.size();i++){
                try {
                    valueJSONs.add(new JSONObject(values.get(i).toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        for (JSONObject valueJSON:valueJSONs){

            String titleText = "";
            if (valueJSON.has("title_tanz")) titleText = valueJSON.optString(UserInfoHelper_flower.getInstance().isLanguageEn() ? "title":"title_tanz");
            else titleText = valueJSON.optString("title");

            if (titleText.equalsIgnoreCase(title)) return valueJSON.optString("value").trim();
        }

        return "";
    }
}
