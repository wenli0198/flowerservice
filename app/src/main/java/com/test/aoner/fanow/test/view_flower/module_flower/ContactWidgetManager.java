package com.test.aoner.fanow.test.view_flower.module_flower;

import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.view_flower.info_input_flower.ContactSelectViewManager_flower;
import com.test.aoner.fanow.test.view_flower.info_input_flower.SelectViewManager_flower;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactWidgetManager {

    public SelectViewManager_flower relationWidget;
    public ContactSelectViewManager_flower mobileWidget;
    public ContactSelectViewManager_flower nameWidget;

    public void setShow(String name,String mobile){
        if (nameWidget!=null) nameWidget.setText(name);
        if (mobile!=null) mobileWidget.setText(mobile);
    }

    public JSONObject toJson(){
        JSONObject result = new JSONObject();
        try {
            if (relationWidget!=null) result.put(relationWidget.getParamName(),relationWidget.getTextInput());
            if (nameWidget!=null) result.put(nameWidget.getParamName(),nameWidget.getInput());
            if (mobileWidget!=null) result.put(mobileWidget.getParamName(),mobileWidget.getInput());
        } catch (JSONException e) {
            if (Constant_flower.DebugFlag) e.printStackTrace();
        }
        return result;
    }

}
