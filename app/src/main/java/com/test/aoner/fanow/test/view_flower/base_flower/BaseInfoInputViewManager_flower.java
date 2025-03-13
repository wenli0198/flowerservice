package com.test.aoner.fanow.test.view_flower.base_flower;

import android.view.View;

import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessData_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;


public abstract class BaseInfoInputViewManager_flower {

    protected View mView;
    protected ProcessData_flower mData;

    protected final String pageTag;

    public BaseInfoInputViewManager_flower(View view, ProcessData_flower data,String pageTag){
        mView = view;
        mData = data;
        this.pageTag = StringUtil_flower.getSafeString(pageTag);
        init();
    }

    protected abstract void init();

    public abstract boolean checkInput();

    public abstract String getInput();

    public ProcessData_flower getData() {
        return mData;
    }
}
