package com.test.aoner.fanow.test.activity_fragment_flower.fragment_flower;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.ServiceFeedbackActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.loan_flower.LoanMainActivity_flower;
import com.test.aoner.fanow.test.adapter_flower.CustomerServiceListAdapter_flower;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.dialog_flower.CustomerServiceDialog_flower;
import com.test.aoner.fanow.test.dialog_flower.DeleteDataDialog_flower;
import com.test.aoner.fanow.test.dialog_flower.LanguageSelectDialog_flower;
import com.test.aoner.fanow.test.dialog_flower.LogoutDialog_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseFragment_flower;

public class PersonalCenterFragment_flower extends BaseFragment_flower {

    private View serviceFeedbackLayout;
    private ListView customerServiceLv;
    private TextView customerServiceDescTv;
    private Button logoutBtn;
    private Button refreshStatusBtn;

    private View languageView;
    private View termOfUserView;
    private View deleteDataView;

    private CustomerServiceListAdapter_flower customerServiceListAdapter;

    private int listHeight = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center_flower, container, false);
        init(view);
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void init(View view){

        serviceFeedbackLayout = view.findViewById(R.id.fragment_personal_center_layout_service_feedback);
        customerServiceLv = view.findViewById(R.id.fragment_personal_center_lv_customer_service);
        customerServiceDescTv = view.findViewById(R.id.fragment_personal_center_tv_customer_service_desc);
        logoutBtn = view.findViewById(R.id.fragment_personal_center_btn_logout);
        refreshStatusBtn = view.findViewById(R.id.fragment_personal_center_btn_refresh_status);
        languageView = view.findViewById(R.id.fragment_personal_center_view_language);
        termOfUserView = view.findViewById(R.id.fragment_personal_center_view_term_of_user);
        deleteDataView = view.findViewById(R.id.fragment_personal_center_view_delete_data);

        view.findViewById(R.id.fragment_personal_center_layout_customer_service).setOnClickListener(v -> new CustomerServiceDialog_flower(requireContext()).show());

        TextView mobileTv = view.findViewById(R.id.fragment_personal_center_tv_mobile);
        mobileTv.setText(StaticConfig_flower.getMobilePrefix()+" "+UserInfoHelper_flower.getInstance().getHideMobile());

        serviceFeedbackLayout.setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_FEEDBACK_CLICK");
            Activity activity = requireActivity();
            if (activity instanceof BaseActivity_flower)
                ((BaseActivity_flower)activity).startActivity_flower(ServiceFeedbackActivity_flower.class);
        });

        refreshStatusBtn.setOnClickListener(v -> HttpManager_flower.getInstance().getAppShowInfo());
        view.findViewById(R.id.fragment_personal_center_btn_logout).setOnClickListener(v -> new LogoutDialog_flower((BaseActivity_flower) requireActivity()).show());

        customerServiceListAdapter = new CustomerServiceListAdapter_flower(requireContext());
        customerServiceLv.setAdapter(customerServiceListAdapter);

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Tanzan)){
            languageView.setVisibility(View.VISIBLE);
            languageView.setOnClickListener(v -> {

                HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_LANGUAGE_CLICK");

                new LanguageSelectDialog_flower(requireContext(),null,true).show();
            });
        }else languageView.setVisibility(View.GONE);


        final String termOfUserPath = GlobalConfig_flower.getInstance().getTermOfUsePath();
        if (TextUtils.isEmpty(termOfUserPath)) termOfUserView.setVisibility(View.GONE);
        else {
            termOfUserView.setVisibility(View.VISIBLE);
            termOfUserView.setOnClickListener(v -> {
                HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_TERM_OF_USER_CLICK");
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(termOfUserPath)));
            });
        }

        if (GlobalConfig_flower.getInstance().canDeleteData()){
            deleteDataView.setVisibility(View.VISIBLE);
            deleteDataView.setOnClickListener(v -> {
                HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_DELETE_PERSONAL_DATA_CLICK");
                new DeleteDataDialog_flower(requireContext()).show();
            });
        }else deleteDataView.setVisibility(View.GONE);

        GlobalConfig_flower.getInstance().handleOperatedView(requireActivity(),
                view.findViewById(R.id.fragment_personal_center_view_operated),
                view.findViewById(R.id.fragment_personal_center_iv_operated_logo),
                view.findViewById(R.id.fragment_personal_center_tv_company_name));

        updateCustomerService();

    }

    public void updateCustomerService(){

        customerServiceListAdapter.initCustomerService();
        customerServiceListAdapter.notifyDataSetChanged();

        ViewGroup.LayoutParams layoutParams = customerServiceLv.getLayoutParams();
        if (listHeight==0) listHeight = layoutParams.height;
        layoutParams.height = customerServiceListAdapter.getCount()*listHeight;
        customerServiceLv.setLayoutParams(layoutParams);
    }

    @Override
    public String getPagetag() {
        return "ME";
    }
}
