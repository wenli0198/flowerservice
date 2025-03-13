package com.test.aoner.fanow.test.activity_fragment_flower.fragment_flower;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseLoanFragment_flower;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.AppShowInfo_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessInfo_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.LoanApplyDetailInfo_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.dialog_flower.NoticeDialog_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.view_flower.module_flower.AddBankAccountView_Cote_flower;
import com.test.aoner.fanow.test.view_flower.module_flower.ShowWalletAccountListView_Cote_flower;

public class LoanWaitFragment_flower extends BaseLoanFragment_flower {

    private ImageView imageIv;
    private TextView titleTv, textTv;
    private View tipsView;

    private TextView loanAmountTv;
    private TextView loanTermTv;
    private View showAccountView;

    private TextView coteTipTv;

    private TextView accountTextTv;
    private TextView accountNameTv, accountNumTv;

    private ShowWalletAccountListView_Cote_flower showAccountView_Cote;
    private AddBankAccountView_Cote_flower addBankAccountView_cote;

    private static boolean showReviewNoticeFlag = true;
    private static boolean showLoanNoticeFlag = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_wait_flower, container, false);
        init(view);
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void init(View view){

        Button refreshStatusBtn = view.findViewById(R.id.fragment_loan_wait_btn_refresh_status);
        imageIv = view.findViewById(R.id.fragment_loan_wait_iv_image);
        titleTv = view.findViewById(R.id.fragment_loan_wait_tv_title);
        textTv = view.findViewById(R.id.fragment_loan_wait_tv_text);
        loanAmountTv = view.findViewById(R.id.fragment_loan_wait_tv_loan_amount);
        loanTermTv = view.findViewById(R.id.fragment_loan_wait_tv_loan_term);
        showAccountView = view.findViewById(R.id.fragment_loan_wait_view_show_account);
        accountTextTv = view.findViewById(R.id.fragment_loan_wait_tv_show_account_text);
        accountNameTv = view.findViewById(R.id.fragment_loan_wait_tv_show_account_name);
        accountNumTv = view.findViewById(R.id.fragment_loan_wait_tv_show_account_num);
        showAccountView_Cote = view.findViewById(R.id.fragment_loan_wait_view_show_account_cote);
        addBankAccountView_cote = view.findViewById(R.id.fragment_loan_wait_view_add_account_cote);
        tipsView = view.findViewById(R.id.fragment_loan_wait_view_tips);
        coteTipTv = view.findViewById(R.id.fragment_loan_wait_tv_tip_cote);
        ViewGroup coteTipLayout = view.findViewById(R.id.fragment_loan_wait_layout_tip_cote);

        addBankAccountView_cote.setPageTag(getPagetag());

        refreshStatusBtn.setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_REFRESH_CLICK");
            HttpManager_flower.getInstance().requestUserProcess();
        });

        if (ProcessInfo_flower.getInstance().isOrderLoaning()){
            imageIv.setBackgroundResource(R.drawable.img_loan_loaning);
            titleTv.setText(getString(R.string.fragment_loan_loaning_title));
            textTv.setText(getString(R.string.fragment_loan_loaning_text));
            tipsView.setVisibility(View.GONE);
            pageTag_flower = "LOAN_LOANING_FRAGMENT";
        }else {
            imageIv.setBackgroundResource(R.drawable.img_loan_review);
            titleTv.setText(getString(R.string.fragment_loan_review_title));
            textTv.setText(getString(R.string.fragment_loan_review_text));
            tipsView.setVisibility(View.VISIBLE);
            pageTag_flower = "LOAN_REVIEWING_FRAGMENT";
        }

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Cote)&&(!TextUtils.isEmpty(AppShowInfo_flower.getInstance().getTransferDelayImportant()))){
            coteTipLayout.setVisibility(View.VISIBLE);
            coteTipTv.setText(AppShowInfo_flower.getInstance().getTransferDelayImportant());
        }else coteTipLayout.setVisibility(View.GONE);

        switch (StaticConfig_flower.Select_Country){
            case Constant_flower.Country_Cote:
                if (ProcessInfo_flower.getInstance().isNewUser()) showAccountView_Cote.setVisibility(View.VISIBLE);
                else addBankAccountView_cote.setVisibility(View.VISIBLE);
                break;
            case Constant_flower.Country_Ghana:
                showAccountView.setVisibility(View.VISIBLE);
                accountNameTv.setText("Wallet");
                accountTextTv.setText("Once the loan is approved, the loan will be transferred to one of your available wallet mobile.");
                break;
            default:
                showAccountView.setVisibility(View.VISIBLE);
                accountNameTv.setText("Account");
                accountTextTv.setText("Once the loan is approved, the loan will be transferred to one of your available account.");
        }

        GlobalConfig_flower.getInstance().handleOperatedView(requireActivity(),
                view.findViewById(R.id.fragment_loan_wait_view_operated),
                view.findViewById(R.id.fragment_loan_wait_iv_operated_logo),
                view.findViewById(R.id.fragment_loan_wait_tv_company_name));

        HttpManager_flower.getInstance().requestLoanApplyDetail();

    }

    @SuppressLint("SetTextI18n")
    public void onLoanApplyDetailResponse(){

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Vietnam) && UserInfoHelper_flower.getInstance().getToken().equalsIgnoreCase("669a3f732fc46612e4279dae47dc8e98")){

            loanAmountTv.setText(StaticConfig_flower.getMoneyUnit()+" 10000000");
            loanTermTv.setText("365 "+getString(R.string.word_days));
            accountNumTv.setText(LoanApplyDetailInfo_flower.getInstance().getBankAccount());
            return;
        }

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Tanzan) && UserInfoHelper_flower.getInstance().getToken().equalsIgnoreCase("46f26120d465e1e5f65f9520ea329136")){

            loanAmountTv.setText(StaticConfig_flower.getMoneyUnit()+" 200000");
            loanTermTv.setText("365 "+getString(R.string.word_days));
            accountNumTv.setText(LoanApplyDetailInfo_flower.getInstance().getBankAccount());
            return;
        }

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Cote) && UserInfoHelper_flower.getInstance().getToken().equalsIgnoreCase("5b5bfedc274f62d2d25ea49637c60cb0")){

            loanAmountTv.setText("300000 "+ LoanApplyDetailInfo_flower.getInstance().getAmount());
            loanTermTv.setText("365 "+getString(R.string.word_days));
            accountNumTv.setText(LoanApplyDetailInfo_flower.getInstance().getBankAccount());
            HttpManager_flower.getInstance().fetchBoundBankAccount_Cote();

            return;
        }

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Uganda) && GlobalConfig_flower.getInstance().isTestAccount()){

            loanAmountTv.setText("300000 "+ LoanApplyDetailInfo_flower.getInstance().getAmount());
            loanTermTv.setText("365 "+getString(R.string.word_days));
            accountNumTv.setText(LoanApplyDetailInfo_flower.getInstance().getBankAccount());

            return;
        }

        loanAmountTv.setText(StaticConfig_flower.getMoneyUnit()+" "+ LoanApplyDetailInfo_flower.getInstance().getAmount());
        loanTermTv.setText(LoanApplyDetailInfo_flower.getInstance().getDays()+" "+getString(R.string.word_days));

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Ghana)) accountNumTv.setText(LoanApplyDetailInfo_flower.getInstance().getWalletMobile());
        else accountNumTv.setText(LoanApplyDetailInfo_flower.getInstance().getBankAccount());

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Cote)) HttpManager_flower.getInstance().fetchBoundBankAccount_Cote();
        else HttpManager_flower.getInstance().fetchStarWindowConfig();
    }

    public void updateWalletAccounts_Cote(){
        showAccountView_Cote.updateAccountList();
        addBankAccountView_cote.updateAccountList();
        HttpManager_flower.getInstance().fetchStarWindowConfig();
    }

    @Override
    public String getPagetag() {
        if (ProcessInfo_flower.getInstance().isOrderLoaning()) return "LOANING";
        return "REVIEW";
    }

    public void onBindBankAccountResponse(){
        AddBankAccountView_Cote_flower.addAccountFlag = true;
        HttpManager_flower.getInstance().fetchBoundBankAccount_Cote();
    }

    public void onDeleteBankAccountResponse(){
        Toast.makeText(requireContext(),"Supprimé avec succès",Toast.LENGTH_SHORT).show();
        HttpManager_flower.getInstance().fetchBoundBankAccount_Cote();
    }

    public void notice(){

        if ("LOAN_REVIEWING_FRAGMENT".equalsIgnoreCase(pageTag_flower) && showReviewNoticeFlag && AppShowInfo_flower.getInstance().reviewNoticeFlag()){
            showReviewNoticeFlag = false;
            new NoticeDialog_flower(requireContext(),
                    AppShowInfo_flower.getInstance().getReviewNoticeTitle(),
                    AppShowInfo_flower.getInstance().getReviewNoticeContent()
                    ).show();
        }

        else if ("LOAN_LOANING_FRAGMENT".equalsIgnoreCase(pageTag_flower) && showLoanNoticeFlag && AppShowInfo_flower.getInstance().loaningNoticeFlag()){
            showLoanNoticeFlag = false;
            new NoticeDialog_flower(requireContext(),
                    AppShowInfo_flower.getInstance().getLoaningNoticeTitle(),
                    AppShowInfo_flower.getInstance().getLoaningNoticeContent()
            ).show();
        }

    }

}