package com.test.aoner.fanow.test.activity_fragment_flower.base_flower;

import static com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower.LOGIN_TYPE_REGISTER;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.test.aoner.fanow.test.analytics.flowerevent.FlowerIAnalyticsEvent;
import com.test.aoner.fanow.test.analytics.flowerutil.FlowerAnalyticsUtil;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.LoginInfo_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;

import org.json.JSONObject;

public abstract class BaseLoginActivity_flower extends BaseActivity_flower {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void onRequestOtpCodeResponse_flower(String msg,JSONObject jsonObject);

    public void onUserLoginResponse_flower() {
        try {

            UserInfoHelper_flower.getInstance().setToken(LoginInfo_flower.getInstance().getToken());
            UserInfoHelper_flower.getInstance().setUserId(LoginInfo_flower.getInstance().getUserId());
            //UserInfoHelper.getInstance().setMobile(phoneNum);

            if (LoginInfo_flower.getInstance().getType().equalsIgnoreCase(LOGIN_TYPE_REGISTER)){
                FlowerAnalyticsUtil.INSTANCE.addEvent(FlowerIAnalyticsEvent.AT_REGISTER);
            }

            HttpManager_flower.getInstance().requestUserProcess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}