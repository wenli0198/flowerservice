package com.test.aoner.fanow.test.activity_fragment_flower.login_flower;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseLoginActivity_flower;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.dialog_flower.MsgDialog;
import com.test.aoner.fanow.test.util_flower.ThreadUtil_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.view_flower.widget_flower.TitleView_flower;

import org.json.JSONObject;

public class LoginVerCodeActivity_flower extends BaseLoginActivity_flower{

    private EditText verCodeEt;
    private TextView countDownTv;
    private TextView resendTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ver_code_flower);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init(){
        TitleView_flower titleView = findViewById(R.id.activity_login_ver_coder_view_title);
        setBackBtn_flower(titleView.getBackIb());

        verCodeEt = findViewById(R.id.activity_login_ver_coder_et_ver_code);
        countDownTv = findViewById(R.id.activity_login_ver_code_tv_count_down);
        resendTv = findViewById(R.id.activity_login_ver_coder_tv_resend);

        TextView mobileTv = findViewById(R.id.activity_login_ver_coder_et_tv_mobile);
        mobileTv.setText(getString(R.string.activity_login_enter_ver_code)+" "+
                StaticConfig_flower.Country_Mobile_Prefix_Map.get(StaticConfig_flower.Select_Country)+" "+
                UserInfoHelper_flower.getInstance().getHideMobile());

        if (getResources().getConfiguration().locale.getCountry().equalsIgnoreCase("vi")) mobileTv.append(" "+"của bạn");

        findViewById(R.id.activity_login_ver_coder_btn_next).setOnClickListener(v -> {

            HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_DONE_CLICK");

            if (TextUtils.isEmpty(verCodeEt.getText())){
                showToast_flower(getString(R.string.word_input_tip));
                return;
            }
            startLogin();
        });

        verCodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_DONE_CLICK");
                if (s.length()==4) startLogin();
            }
        });

        GlobalConfig_flower.getInstance().handleOperatedView(this,
                findViewById(R.id.activity_login_ver_coder_view_operated),
                findViewById(R.id.activity_login_ver_coder_iv_operated_logo),
                findViewById(R.id.activity_login_ver_coder_tv_company_name));

        onRequestOtpCodeResponse_flower(null,null);

        if (!TextUtils.isEmpty(BaseApplication_flower.getApplication_flower().getMsg())){
            new MsgDialog(this,BaseApplication_flower.getApplication_flower().getMsg(),null).show();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onRequestOtpCodeResponse_flower(String msg,JSONObject jsonObject) {


        if (!TextUtils.isEmpty(msg)){
            new MsgDialog(this,msg,null).show();
        }

        showToast_flower(getString(R.string.activity_login_ver_code_send_tip));
        countDownTv.setVisibility(View.VISIBLE);
        resendTv.setTextColor(ContextCompat.getColor(this,R.color.gray_flower));
        resendTv.setOnClickListener(null);
        ThreadUtil_flower.getInstance().runOnChildThread(() -> {
            int countDown = 60;
            while (countDown-- > 0) {
                try {
                    final int finalCountDown = countDown;
                    ThreadUtil_flower.getInstance().runOnUiThread(() -> {
                        countDownTv.setText("("+finalCountDown +"s)");
                        if (finalCountDown == 0) {
                            countDownTv.setVisibility(View.GONE);
                            resendTv.setTextColor(ContextCompat.getColor(this,R.color.black));
                            resendTv.setOnClickListener(view -> HttpManager_flower.getInstance().requestOtpCode(UserInfoHelper_flower.getInstance().getMobile()));
                        }
                    });
                    Thread.sleep(999);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Vietnam)&&(!TextUtils.isEmpty(msg))){
            new MsgDialog(this,msg, null).show();
        }
    }

    private void startLogin() {
        String phoneNum = UserInfoHelper_flower.getInstance().getMobile();
        String verCode = verCodeEt.getText().toString();
        if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(verCode) || verCode.length() < 4) {
            showToast_flower(getString(R.string.word_input_tip));
            return;
        }

        HttpManager_flower.getInstance().userLogin(phoneNum, verCode);

    }

    @Override
    public String getPagetag() {
        return "LOGIN_OTP";
    }

}