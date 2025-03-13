package com.test.aoner.fanow.test.activity_fragment_flower;

import android.os.Bundle;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower;
import com.test.aoner.fanow.test.analytics.flowerutil.FlowerAnalyticsUtil;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.view_flower.widget_flower.TitleView_flower;

public class UsagePermissionGuideActivity_flower extends BaseActivity_flower {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_permission_guide_flower);
        init();
    }

    private void init(){
        TitleView_flower titleView = findViewById(R.id.activity_usage_permission_guide_view_title);
        setBackBtn_flower(titleView.getBackIb());

        findViewById(R.id.activity_usage_permission_guide_btn_next).setOnClickListener(v -> {
            FlowerAnalyticsUtil.INSTANCE.startToUsageStatsPermissionSettingPage(BaseApplication_flower.getApplication_flower());
            finish();
        });

        GlobalConfig_flower.getInstance().handleOperatedView(this,
                findViewById(R.id.activity_usage_permission_guide_view_operated),
                findViewById(R.id.activity_usage_permission_guide_iv_operated_logo),
                findViewById(R.id.activity_usage_permission_guide_tv_company_name));

    }

    @Override
    public String getPagetag() {
        return "USAGE_PERMISSIOM";
    }
}