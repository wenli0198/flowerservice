package com.test.aoner.fanow.test.activity_fragment_flower.fragment_flower;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.aoner.fanow.test.analytics.flowerutil.FlowerAnalyticsUtil;
import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.UsagePermissionGuideActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseLoanFragment_flower;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.LoanLimitInfo_flower;
import com.test.aoner.fanow.test.dialog_flower.CustomerServiceDialog_flower;
import com.test.aoner.fanow.test.util_flower.PermissionUtil_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;

import pub.devrel.easypermissions.EasyPermissions;

public class LoanMainFragment_flower extends BaseLoanFragment_flower {

    private TextView loanAmountTv;

    public boolean isRefresh = false;

    private TextView limit1Tv,limit2Tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_main_flower, container, false);
        init_flower(view);
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void init_flower(View view) {
        loanAmountTv = view.findViewById(R.id.fragment_loan_main_tv_loan_amount);

        limit1Tv = view.findViewById(R.id.fragment_loan_main_tv_limit_1);
        limit2Tv = view.findViewById(R.id.fragment_loan_main_tv_limit_2);

        view.findViewById(R.id.fragment_loan_main_btn_refresh_status).setOnClickListener(v -> {
            isRefresh = true;
            HttpManager_flower.getInstance().requestUserProcess();
        });

        view.findViewById(R.id.fragment_loan_main_layout_apply_btn).setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint((getPagetag() + "_APPLY_CLICK").toUpperCase());
            if (PermissionUtil_flower.checkLaunchPermissions(requireActivity(), GlobalConfig_flower.getInstance().getHomeArr())) {
                try {
                    if (UserInfoHelper_flower.getInstance().didLogin()) {
                        FlowerAnalyticsUtil.INSTANCE.saveDeviceS2SInfo();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (GlobalConfig_flower.getInstance().needRequestUsageStatsPermission()) {
                    FlowerAnalyticsUtil.INSTANCE.checkUsageStatsPermission(BaseApplication_flower.getApplication_flower(), permissionGrant -> {
                        if (permissionGrant) {
                            HttpManager_flower.getInstance().requestUserProcess();

                        } else {
                            Activity activity = requireActivity();
                            if (requireActivity() instanceof BaseActivity_flower)
                                ((BaseActivity_flower)activity).startActivity_flower(UsagePermissionGuideActivity_flower.class);
                        }
                        return null;
                    });
                } else {
                    HttpManager_flower.getInstance().requestUserProcess();
                }
            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.launch_permissions), PermissionUtil_flower.REQ_CODE_PERMISSIONS, GlobalConfig_flower.getInstance().getHomeArr());
            }
        });

        view.findViewById(R.id.fragment_loan_main_ib_customer_service).setOnClickListener(v -> new CustomerServiceDialog_flower(requireContext()).show());

        GlobalConfig_flower.getInstance().handleOperatedView(requireActivity(),
                view.findViewById(R.id.fragment_loan_main_view_operated),
                view.findViewById(R.id.fragment_loan_main_iv_operated_logo),
                view.findViewById(R.id.fragment_loan_main_tv_company_name));

        new Thread(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HttpManager_flower.getInstance().queryLoanLimit();
        }).start();

    }

    @SuppressLint("SetTextI18n")
    public void updateLoanAmount_flower() {
        isRefresh = false;
        loanAmountTv.setText(StringUtil_flower.getSafeString(StaticConfig_flower.getMoneyUnit()).toUpperCase() + " "
                + LoanLimitInfo_flower.getInstance().getLimitAmount());
        limit1Tv.setText(StringUtil_flower.getSafeString(StaticConfig_flower.getMoneyUnit()).toUpperCase() + " "
                + LoanLimitInfo_flower.getInstance().getLimitIncreasedAmount1());
        limit2Tv.setText(StringUtil_flower.getSafeString(StaticConfig_flower.getMoneyUnit()).toUpperCase() + " "
                + LoanLimitInfo_flower.getInstance().getLimitIncreasedAmount2());
    }

    @Override
    public String getPagetag() {
        return "HOME";
    }
}