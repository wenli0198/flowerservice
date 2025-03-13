package com.test.aoner.fanow.test.activity_fragment_flower.loan_flower;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.AppShowInfo_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessInfo_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.ApplyDetailItem_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.LoanApplyDetailInfo_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.ThreadUtil_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.view_flower.module_flower.AddBankAccountView_Cote_flower;
import com.test.aoner.fanow.test.view_flower.module_flower.ShowWalletAccountListView_Cote_flower;
import com.test.aoner.fanow.test.view_flower.widget_flower.TitleView_flower;
import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseLoanActivity_flower;

import java.util.ArrayList;


public class LoanApplyActivity_flower extends BaseLoanActivity_flower {

    private TextView loanAmountTv;
    private TextView loanTermTv;
    private LinearLayout detailGroupLayout;


    private ShowWalletAccountListView_Cote_flower showWalletAccountListView_cote;
    private AddBankAccountView_Cote_flower addBankAccountView_cote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_apply_flower);
        init();
    }

    private void init(){
        TitleView_flower titleView = findViewById(R.id.activity_loan_apply_view_title);
        setBackBtn_flower(titleView.getBackIb());

        loanAmountTv = findViewById(R.id.activity_loan_apply_tv_loan_amount);
        loanTermTv = findViewById(R.id.activity_loan_apply_tv_loan_term);
        detailGroupLayout = findViewById(R.id.activity_loan_apply_layout_detail_group);

        showWalletAccountListView_cote = findViewById(R.id.activity_loan_apply_view_show_account_view);
        addBankAccountView_cote = findViewById(R.id.activity_loan_apply_view_add_account_view);

        addBankAccountView_cote.setPageTag(getPagetag());

        findViewById(R.id.activity_loan_apply_btn_apply).setOnClickListener(v ->
            ThreadUtil_flower.getInstance().runOnChildThread(() -> {
                HttpManager_flower.getInstance().saveUserBuriedPoint((getPagetag()+"_SUBMIT_CLICK").toUpperCase());
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HttpManager_flower.getInstance().loanApply(getString(R.string.language));
            }));

        GlobalConfig_flower.getInstance().handleOperatedView(this,
                findViewById(R.id.activity_loan_apply_view_operated),
                findViewById(R.id.activity_loan_apply_iv_operated_logo),
                findViewById(R.id.activity_loan_apply_tv_company_name));

    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpManager_flower.getInstance().requestLoanApplyDetail();
    }

    @SuppressLint("SetTextI18n")
    public void onLoanApplyDetailResponse(){

        String moneyUnit = StringUtil_flower.getSafeString(StaticConfig_flower.getMoneyUnit()).toUpperCase();

        if (GlobalConfig_flower.getInstance().isTestAccount()){
            loanTermTv.setText("365 "+getString(R.string.word_days));
            switch (StaticConfig_flower.Select_Country){
                case Constant_flower.Country_Tanzan:
                    loanAmountTv.setText(moneyUnit+" 200000");
                    break;
                case Constant_flower.Country_Uganda:
                case Constant_flower.Country_Cote:
                default:
                    loanAmountTv.setText(moneyUnit+" 300000");
            }
        }else {
            loanAmountTv.setText(moneyUnit+" "+ LoanApplyDetailInfo_flower.getInstance().getAmount());
            loanTermTv.setText(LoanApplyDetailInfo_flower.getInstance().getDays()+" "+getString(R.string.word_days));
        }

        showDetails();

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Cote))
            HttpManager_flower.getInstance().fetchBoundBankAccount_Cote();
    }

    @SuppressLint("InflateParams")
    private void showDetails(){

        ArrayList<ApplyDetailItem_flower> detailItemList = null;

        if (GlobalConfig_flower.getInstance().isTestAccount()) detailItemList = LoanApplyDetailInfo_flower.getInstance().getTestAccountDetailItems();
        else detailItemList = LoanApplyDetailInfo_flower.getInstance().getDetailItemList();

        detailGroupLayout.removeAllViews();

        View detailBgView = null;

        for (ApplyDetailItem_flower detailItem:detailItemList){
            if (detailItem.isTitle() || detailBgView == null){
                if (detailBgView!=null) {
                    detailGroupLayout.addView(detailBgView);
                    detailBgView = null;
                }
                detailBgView = LayoutInflater.from(this).inflate(R.layout.view_loan_apply_detail,null);
                addDetailItem(detailBgView.findViewById(R.id.view_loan_apply_detail_layout_group),detailItem);
            }else addDetailItem(detailBgView.findViewById(R.id.view_loan_apply_detail_layout_group),detailItem);

        }

        if (detailBgView!=null) {
            detailGroupLayout.addView(detailBgView);
            detailBgView = null;
        }

    }

    private void addDetailItem(LinearLayout groupLayout,ApplyDetailItem_flower detailItem){
        @SuppressLint("InflateParams") View itemView = LayoutInflater.from(this).inflate(R.layout.item_loan_apply_detail,null);
        TextView nameTv = itemView.findViewById(R.id.item_loan_apply_detail_tv_name);
        TextView valueTv = itemView.findViewById(R.id.item_loan_apply_detail_tv_value);
        nameTv.setText(detailItem.getShowName());
        valueTv.setText(detailItem.getShowDetail());
        groupLayout.addView(itemView);
    }

    public void onFetchBankAccountListResponse_Cote(){
        showWalletAccountListView_cote.updateAccountList();
        addBankAccountView_cote.updateAccountList();
    }

    public void onLoanApplyResponse(){
        HttpManager_flower.getInstance().requestUserProcess();
    }

    @Override
    public String getPagetag() {
        return "APPLY";
    }

}