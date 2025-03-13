package com.test.aoner.fanow.test.activity_fragment_flower.base_flower;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.test.aoner.fanow.test.activity_fragment_flower.info_flower.FaceUploadActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.info_flower.OcrUploadActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.loan_flower.LoanApplyActivity_flower;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessInfo_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.info_flower.BindBankAccountActivity_Cote_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.info_flower.InfoUploadActivity_flower;


public abstract class BaseInfoActivity_flower extends BaseActivity_flower {

    public Uri photoUri_flower;

    private Runnable onPermissionAllPassRun_flower;

    private String pageTag;

    public void onInfoUploadResponse_flower(){
        HttpManager_flower.getInstance().requestUserProcess();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageTag = ProcessInfo_flower.getInstance().getStepName(0).toUpperCase();
    }

    @Override
    public void onUserProcessResponse_flower() {

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

        finish();

    }

    public void onLoanApplyDetailResponse_flower(){
        startActivity_flower(LoanApplyActivity_flower.class);
    }

    public void setOnPermissionAllPassRun_flower(Runnable onPermissionAllPassRun_flower) {
        this.onPermissionAllPassRun_flower = onPermissionAllPassRun_flower;
    }

    @Override
    protected void onLaunchPermissionsAllPass_flower() {
        if (onPermissionAllPassRun_flower !=null){
            onPermissionAllPassRun_flower.run();
            onPermissionAllPassRun_flower = null;
        }
    }

    @Override
    public String getPagetag() {
        return TextUtils.isEmpty(pageTag) ? getClass().getSimpleName():pageTag;
    }
}
