package com.test.aoner.fanow.test.activity_fragment_flower.base_flower;

import androidx.fragment.app.Fragment;

import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;


public abstract class BaseFragment_flower extends Fragment implements PagetagInterface{

    protected String pageTag_flower;

    @Override
    public void onResume() {
        super.onResume();
        recordPageResumeEvent_flower();
    }

    @Override
    public void onPause() {
        super.onPause();
        recordPagePauseEvent_flower();
    }


    private void recordPagePauseEvent_flower() {
        HttpManager_flower.getInstance().saveUserBuriedPoint((getPagetag() + "_END").toUpperCase());
    }

    private void recordPageResumeEvent_flower() {
        HttpManager_flower.getInstance().saveUserBuriedPoint((getPagetag() + "_START").toUpperCase());
    }

}
