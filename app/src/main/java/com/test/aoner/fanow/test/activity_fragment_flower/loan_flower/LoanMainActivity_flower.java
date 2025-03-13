package com.test.aoner.fanow.test.activity_fragment_flower.loan_flower;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseLoanFragment_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.fragment_flower.DataSafetyFragment_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.fragment_flower.LoanFailFragment_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.fragment_flower.LoanMainFragment_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.fragment_flower.LoanWaitFragment_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.fragment_flower.PersonalCenterFragment_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.fragment_flower.RepayFragment_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.info_flower.FaceUploadActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.info_flower.OcrUploadActivity_flower;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessInfo_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.dialog_flower.InitOfflinePayChannelDialog_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseLoanActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.info_flower.BindBankAccountActivity_Cote_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.info_flower.InfoUploadActivity_flower;
import com.test.aoner.fanow.test.v40.bean.ActiveWindowConfig_flower;
import com.test.aoner.fanow.test.v40.bean.NormalWindowConfig_flower;
import com.test.aoner.fanow.test.v40.dialog.StarActiveDialog_flower;
import com.test.aoner.fanow.test.v40.dialog.StarNormalDialog_flower;

import java.util.Objects;

public class LoanMainActivity_flower extends BaseLoanActivity_flower {

    private RelativeLayout fragmentLayout;
    private ViewGroup homeLayout, dataSafetyLayout, personalCenterLayout;
    private ImageView homeIv, dataSafetyIv, personalCenterIv;
    private TextView homeTv, dataSafetyTv, personalCenterTv;

    private int fragmentShow = 1;

    private BaseLoanFragment_flower homeFragment;
    private DataSafetyFragment_flower dataSafetyFragment;
    private PersonalCenterFragment_flower personalCenterFragment;

    public static boolean showRateDialogFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_main_flower);
        init();
    }

    private void init(){
        fragmentLayout = findViewById(R.id.activity_loan_main_layout_fragment);
        homeLayout = findViewById(R.id.activity_loan_main_layout_home);
        homeIv = findViewById(R.id.activity_loan_main_iv_home);
        homeTv = findViewById(R.id.activity_loan_main_tv_home);
        dataSafetyLayout = findViewById(R.id.activity_loan_main_layout_data_safety);
        dataSafetyIv = findViewById(R.id.activity_loan_main_iv_data_safety);
        dataSafetyTv = findViewById(R.id.activity_loan_main_tv_data_safety);
        personalCenterLayout = findViewById(R.id.activity_loan_main_layout_personal_center);
        personalCenterIv = findViewById(R.id.activity_loan_main_iv_personal_center);
        personalCenterTv = findViewById(R.id.activity_loan_main_tv_personal_center);

        dataSafetyFragment = new DataSafetyFragment_flower();
        personalCenterFragment = new PersonalCenterFragment_flower();

        homeLayout.setOnClickListener(v -> changeToHome());
        dataSafetyLayout.setOnClickListener(v -> changeToDataSafety());
        personalCenterLayout.setOnClickListener(v -> changeToPersonalCenter());

    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePage();
    }

    private void updatePage() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (homeFragment != null) fragmentTransaction.remove(homeFragment);
        if (dataSafetyFragment != null) fragmentTransaction.remove(dataSafetyFragment);
        if (personalCenterFragment != null) fragmentTransaction.remove(personalCenterFragment);

        if (ProcessInfo_flower.getInstance().isOrderPendingRepayment() || ProcessInfo_flower.getInstance().isOrderOverdueRepayment()) {
            homeFragment = new RepayFragment_flower();
        } else if (ProcessInfo_flower.getInstance().isOrderLoaning() || ProcessInfo_flower.getInstance().isLoanReviewing()) {
            homeFragment = new LoanWaitFragment_flower();
        } else if (ProcessInfo_flower.getInstance().isLoanRejcted()) {
            homeFragment = new LoanFailFragment_flower();
        } else {
            homeFragment = new LoanMainFragment_flower();
        }

        fragmentTransaction.add(R.id.activity_loan_main_layout_fragment, Objects.requireNonNull(homeFragment))
                .add(R.id.activity_loan_main_layout_fragment, dataSafetyFragment)
                .add(R.id.activity_loan_main_layout_fragment, personalCenterFragment);

        switch (fragmentShow) {
            case 2:
                fragmentTransaction.show(dataSafetyFragment).hide(homeFragment).hide(personalCenterFragment);
                break;
            case 3:
                fragmentTransaction.show(personalCenterFragment).hide(homeFragment).hide(dataSafetyFragment);
                break;
            default:
                fragmentTransaction.show(homeFragment).hide(dataSafetyFragment).hide(personalCenterFragment);
        }

        fragmentTransaction.commit();
    }

    private void changeToHome() {

        homeIv.setImageResource(R.drawable.icon_tab_home_true);
        dataSafetyIv.setImageResource(R.drawable.icon_tab_data_safety_false);
        personalCenterIv.setImageResource(R.drawable.icon_tab_personal_center_false);

        homeTv.setTextColor(ContextCompat.getColor(this,R.color.green_flower));
        dataSafetyTv.setTextColor(ContextCompat.getColor(this,R.color.gray_ddd));
        personalCenterTv.setTextColor(ContextCompat.getColor(this,R.color.gray_ddd));

        getSupportFragmentManager().beginTransaction().show(homeFragment).hide(dataSafetyFragment).hide(personalCenterFragment).commit();

        fragmentShow = 1;
    }

    private void changeToDataSafety() {

        homeIv.setImageResource(R.drawable.icon_tab_home_false);
        dataSafetyIv.setImageResource(R.drawable.icon_tab_data_safety_true);
        personalCenterIv.setImageResource(R.drawable.icon_tab_personal_center_false);

        homeTv.setTextColor(ContextCompat.getColor(this,R.color.gray_ddd));
        dataSafetyTv.setTextColor(ContextCompat.getColor(this,R.color.green_flower));
        personalCenterTv.setTextColor(ContextCompat.getColor(this,R.color.gray_ddd));

        getSupportFragmentManager().beginTransaction().show(dataSafetyFragment).hide(homeFragment).hide(personalCenterFragment).commit();

        fragmentShow = 2;
    }

    private void changeToPersonalCenter() {

        homeIv.setImageResource(R.drawable.icon_tab_home_false);
        dataSafetyIv.setImageResource(R.drawable.icon_tab_data_safety_false);
        personalCenterIv.setImageResource(R.drawable.icon_tab_personal_center_true);

        homeTv.setTextColor(ContextCompat.getColor(this,R.color.gray_ddd));
        dataSafetyTv.setTextColor(ContextCompat.getColor(this,R.color.gray_ddd));
        personalCenterTv.setTextColor(ContextCompat.getColor(this,R.color.green_flower));

        getSupportFragmentManager().beginTransaction().show(personalCenterFragment).hide(homeFragment).hide(dataSafetyFragment).commit();

        fragmentShow = 3;

    }

    public void onAppShowInfoResponse() {
        if (personalCenterFragment != null) personalCenterFragment.updateCustomerService();
    }

    public void onLoanLimitInfoResponse() {
        if (homeFragment != null && homeFragment instanceof LoanMainFragment_flower)
            ((LoanMainFragment_flower) homeFragment).updateLoanAmount_flower();
    }

    @Override
    public void onUserProcessResponse_flower() {

        if (ProcessInfo_flower.getInstance().isOrderPendingRepayment() || ProcessInfo_flower.getInstance().isOrderOverdueRepayment()) {
            if (homeFragment instanceof RepayFragment_flower) {
                HttpManager_flower.getInstance().getRepayDetail();
            } else updatePage();
            return;
        }
        if (ProcessInfo_flower.getInstance().isLoanReviewing() || ProcessInfo_flower.getInstance().isOrderLoaning()) {
            if (!(homeFragment instanceof LoanWaitFragment_flower)) updatePage();
            return;
        }
        if (ProcessInfo_flower.getInstance().isLoanRejcted()) {
            if (!(homeFragment instanceof LoanFailFragment_flower)) updatePage();
            return;
        }
        if (homeFragment instanceof LoanMainFragment_flower) {
            if (((LoanMainFragment_flower) homeFragment).isRefresh){
                HttpManager_flower.getInstance().queryLoanLimit();
                ((LoanMainFragment_flower) homeFragment).isRefresh = false;
            }
            else startLoanProcess();
        } else {
            updatePage();
        }
    }

    private void startLoanProcess() {

        if (ProcessInfo_flower.getInstance().getStepsSize()>0){
            String stepName = ProcessInfo_flower.getInstance().getStepName(0);
            if ("sefie_info".equalsIgnoreCase(stepName)) startActivity_flower(FaceUploadActivity_flower.class);
            else if ("ocr_info".equalsIgnoreCase(stepName)) startActivity_flower(OcrUploadActivity_flower.class);
            else if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Cote)&&
                    "account_info".equalsIgnoreCase(stepName)) startActivity_flower(BindBankAccountActivity_Cote_flower.class);
            else startActivity_flower(InfoUploadActivity_flower.class);
        }

        else {
            startActivity_flower(LoanApplyActivity_flower.class);
            HttpManager_flower.getInstance().requestLoanApplyDetail();
        }

    }

    public void onLoanApplyDetailResponse() {

        if (ProcessInfo_flower.getInstance().isOrderLoaning() || ProcessInfo_flower.getInstance().isLoanReviewing()) {
            if ((homeFragment instanceof LoanWaitFragment_flower)) ((LoanWaitFragment_flower) homeFragment).onLoanApplyDetailResponse();
        }else startActivity_flower(LoanApplyActivity_flower.class);
    }

    public void onRepayDetailResponse() {
        if (homeFragment != null && homeFragment instanceof RepayFragment_flower)
            ((RepayFragment_flower) homeFragment).updateRepayDetail();
    }

    public void onOrderRepayH5Response(String url) {
        if (homeFragment != null && homeFragment instanceof RepayFragment_flower) {
            Uri uri = Uri.parse(StringUtil_flower.getSafeString(url));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void onFetchBankAccountListResponse(){
        if (homeFragment instanceof LoanWaitFragment_flower) ((LoanWaitFragment_flower) homeFragment).updateWalletAccounts_Cote();
    }

    public void onFetchBankListResponse_Niri(){
        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Nigeria)&&
                (ProcessInfo_flower.getInstance().isOrderPendingRepayment() || ProcessInfo_flower.getInstance().isOrderOverdueRepayment())) {
            if (homeFragment instanceof RepayFragment_flower) ((RepayFragment_flower) homeFragment).onFetchBankListResponse_Niri();
        }
    }

    public void onInitOfflinePayChannelResponse() {
        InitOfflinePayChannelDialog_flower.showDialog(this, dialogInterface -> HttpManager_flower.getInstance().getRepayDetail());
    }

    public void onStarWindowConfigResponse(){
        if (homeFragment!=null && homeFragment instanceof LoanWaitFragment_flower){
            if (showRateDialogFlag) showRateDialogFlag = false;
            else {
                ((LoanWaitFragment_flower)homeFragment).notice();
                return;
            }

            if (ActiveWindowConfig_flower.getInstance_flower().isSwitch_flower() &&
                    UserInfoHelper_flower.getInstance().getActivedialogCount()< ActiveWindowConfig_flower.getInstance_flower().getNum_flower()){
                new StarActiveDialog_flower(this).show();
            }

            else if (NormalWindowConfig_flower.getInstance_flower().isSwitch_flower() &&
                    UserInfoHelper_flower.getInstance().getNormaldialogCount()< NormalWindowConfig_flower.getInstance_flower().getNum_flower()){
                new StarNormalDialog_flower(this).show();
            }

            else ((LoanWaitFragment_flower)homeFragment).notice();
        }
    }

    public void onCoteDeleteAccountResponse(){
        if (homeFragment!=null&&homeFragment instanceof LoanWaitFragment_flower) ((LoanWaitFragment_flower) homeFragment).onDeleteBankAccountResponse();
    }

    public void onCoteAddAccountResponse(){
        if (homeFragment!=null&&homeFragment instanceof LoanWaitFragment_flower) ((LoanWaitFragment_flower) homeFragment).onBindBankAccountResponse();
    }

    @Override
    public String getPagetag() {
        return "HOME";
    }
}