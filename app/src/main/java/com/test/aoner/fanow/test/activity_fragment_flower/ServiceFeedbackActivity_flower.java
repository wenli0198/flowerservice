package com.test.aoner.fanow.test.activity_fragment_flower;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseActivity_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.util_flower.ThreadUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.view_flower.widget_flower.TitleView_flower;

import java.util.ArrayList;

public class ServiceFeedbackActivity_flower extends BaseActivity_flower {

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_feedback_flower);
        init();
    }

    private void init(){

        TitleView_flower titleView = findViewById(R.id.activity_service_feedback_view_title);
        setBackBtn_flower(titleView.getBackIb());

        ArrayList<TextView> typeTvs = new ArrayList<TextView>(){{
            add(findViewById(R.id.activity_service_feedback_tv_product_suggestion));
            add(findViewById(R.id.activity_service_feedback_tv_app_bugs));
            add(findViewById(R.id.activity_service_feedback_tv_payment_issues));
            add(findViewById(R.id.activity_service_feedback_tv_others));
        }};

        for (TextView typeTv1:typeTvs){
            typeTv1.setOnClickListener(v -> {
                for (TextView typeTv2:typeTvs){
                    if (typeTv1==typeTv2){
                        typeTv2.setBackgroundResource(R.drawable.ic_round_corner_green_flower_10dp);
                        typeTv2.setTextColor(getColor(R.color.white));
                        type = typeTv2.getText().toString();
                    }else {
                        typeTv2.setBackgroundResource(R.drawable.ic_bg_input);
                        typeTv2.setTextColor(getColor(R.color.black));
                    }
                }
            });
        }

        EditText inputEt = findViewById(R.id.activity_service_feedback_et_input);
        Button submitBtn = findViewById(R.id.activity_service_feedback_btn_submit);

        submitBtn.setOnClickListener(v -> {
            String input = inputEt.getText().toString();
            if (TextUtils.isEmpty(type)||TextUtils.isEmpty(input)) {
                showToast_flower(getString(R.string.word_input_tip));
                return;
            }
            HttpManager_flower.getInstance().submitServiceFeedback(type,input);
        });

        GlobalConfig_flower.getInstance().handleOperatedView(this,
                findViewById(R.id.activity_service_feedback_view_operated),
                findViewById(R.id.activity_service_feedback_iv_operated_logo),
                findViewById(R.id.activity_service_feedback_tv_company_name));

    }

    public void onSubmitServiceFeedback(){
        showToast_flower(getString(R.string.word_submit_success));
        ThreadUtil_flower.getInstance().postDelay(this::onBackPressed,1000);
    }

    @Override
    public String getPagetag() {
        return "SERVICE_FEEDBACK";
    }
}