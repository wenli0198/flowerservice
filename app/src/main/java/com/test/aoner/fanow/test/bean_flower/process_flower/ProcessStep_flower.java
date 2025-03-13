package com.test.aoner.fanow.test.bean_flower.process_flower;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;

import java.util.ArrayList;

public class ProcessStep_flower {

    private ProcessStep_flower(){}

    private String apiUrl;
    private JsonArray dataList;
    private JsonArray extDataList;
    private String showTopTips;
    private String itemCount;
    private String isContact;
    private String contactParamName;
    private ArrayList<ArrayList<String>> relationShips;
    private ArrayList<ArrayList<String>> relationShips_tanz;


    public String getApiUrl() {
        return StringUtil_flower.getSafeString(apiUrl);
    }

    public boolean showTopTips(){
        return StringUtil_flower.getSafeString(showTopTips).equalsIgnoreCase("YES");
    }

    public int getDataListSize(){
        if (dataList==null) return 0;
        return dataList.size();
    }

    public ProcessData_flower getProcessData(int index){
        if (index<0||index>=dataList.size()) return null;
        return new Gson().fromJson(dataList.get(index).toString(), ProcessData_flower.class);
    }

    public int getExtDataListSize(){
        if (extDataList == null) return 0;
        return extDataList.size();
    }

    public ProcessData_flower getExtProcessData(int index){
        if (index<0||index>=extDataList.size()) return null;
        return new Gson().fromJson(extDataList.get(index).toString(), ProcessData_flower.class);
    }

    public int getItemCount() {
        return StringUtil_flower.safeParseInt(itemCount);
    }

    public boolean isContact() {
        return StringUtil_flower.getSafeString(isContact).equalsIgnoreCase("YES");
    }

    public String getContactParamName() {
        return StringUtil_flower.getSafeString(contactParamName);
    }

    public ArrayList<String> getRelationShips(int index) {
        if (index<0 || relationShips==null || relationShips.size()<=index) return new ArrayList<>();

        if (!UserInfoHelper_flower.getInstance().isLanguageEn() && relationShips_tanz!=null && !relationShips_tanz.isEmpty() && index<relationShips_tanz.size())
            return relationShips_tanz.get(index);

        return relationShips.get(index);
    }

}
