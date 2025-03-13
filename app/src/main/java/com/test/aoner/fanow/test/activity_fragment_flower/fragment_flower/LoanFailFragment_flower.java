package com.test.aoner.fanow.test.activity_fragment_flower.fragment_flower;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseLoanFragment_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.AppShowInfo_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessInfo_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;


public class LoanFailFragment_flower extends BaseLoanFragment_flower {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_fail_flower, container, false);
        init_flower(view);
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void init_flower(View view){

        TextView textTv = view.findViewById(R.id.fragment_loan_fail_tv_text);

        view.findViewById(R.id.fragment_loan_fail_btn_refresh_status).setOnClickListener(v -> HttpManager_flower.getInstance().requestUserProcess());
        String days = ProcessInfo_flower.getInstance().getDeniedBetweenDay();
        if (TextUtils.isEmpty(days)) days = AppShowInfo_flower.getInstance().getRejectPeriod();
        textTv.setText(getString(R.string.fragment_loan_fail_text_1)+ days + getString(R.string.fragment_loan_fail_text_2));

        GlobalConfig_flower.getInstance().handleOperatedView(requireActivity(),
                view.findViewById(R.id.fragment_loan_fail_btn_view_operated),
                view.findViewById(R.id.fragment_loan_fail_btn_iv_operated_logo),
                view.findViewById(R.id.fragment_loan_fail_btn_tv_company_name));

    }

    @Override
    public String getPagetag() {
        return "REJECT";
    }
}