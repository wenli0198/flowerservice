package com.test.aoner.fanow.test.dialog_flower;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseActivity_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.login_flower.LoginPhoneNumActivity_flower;

public class LogoutDialog_flower extends Dialog {

    private final BaseActivity_flower activity;

    public LogoutDialog_flower(@NonNull BaseActivity_flower activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_logout_flower);
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView(){

        getWindow().setBackgroundDrawableResource(R.color.trans);

        ((TextView)findViewById(R.id.dialog_logout_tv_title)).setText("Log out");
        ((TextView)findViewById(R.id.dialog_logout_tv_explain)).setText("Are you sure you want to log out of your account?");

        Button cancelBtn = findViewById(R.id.dialog_logout_btn_cancel);
        Button logoutBtn = findViewById(R.id.dialog_logout_btn_submit);
        cancelBtn.setText("Cancel");
        logoutBtn.setText("Log out");
        cancelBtn.setTextColor(getContext().getColor(R.color.black));
        logoutBtn.setTextColor(getContext().getColor(R.color.gray_dd));
        cancelBtn.setOnClickListener(view -> {
            dismiss();
        });
        logoutBtn.setOnClickListener(view -> {
            dismiss();
            UserInfoHelper_flower.getInstance().setMobile("");
            UserInfoHelper_flower.getInstance().setToken("");
            UserInfoHelper_flower.getInstance().setUserId("");
            activity.startActivityForSingleTop_flower(LoginPhoneNumActivity_flower.class);
        });
        setCanceledOnTouchOutside(false);
    }
}
