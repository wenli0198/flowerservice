package com.test.aoner.fanow.test.util_flower.http_flower;

import static com.test.aoner.fanow.test.bean_flower.StaticConfig_flower.Country_Word;
import static com.test.aoner.fanow.test.bean_flower.StaticConfig_flower.Upload_Info_Url;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_APP_SHOW_INFO;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_BANK_LIST;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_BIND_BANK_ACCOUNT;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_BVN_INFO;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_CREATE_VIRTUAL_ACCOUNT;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_DELETE_BOUND_BANK_ACCOUNT;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_FEED_BACK;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_FETCH_BANK_CODE;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_FETCH_BOUND_BANK_ACCOUNT;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_FETCH_STAR_WINDOW_CONFIG;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_GET_LOAN_APPLY_DETAIL;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_GET_ORDER_REPAY_H5;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_GLOBAL;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_LOAN_APPLY;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_LOAN_LIMIT;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_OCR_INFO;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_ORDER_REPAY_H5;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_ORDER_REPAY_SDK;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_REPAY_INFO;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_SMS_CODE;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_UPLOAD_IMAGE_FILE;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_USER_CANCEL;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_USER_LOGIN;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.PATH_USER_PROCESS;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.Path_Face_Recognition_Check;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.Path_Get_Discern_Icr_Info;
import static com.test.aoner.fanow.test.constant_flower.Url_flower.Path_Save_Perator_Information;

import android.text.TextUtils;

import com.test.aoner.fanow.test.analytics.flowerevent.FlowerIAnalyticsEvent;
import com.test.aoner.fanow.test.activity_fragment_flower.MainActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.ServiceFeedbackActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseInfoActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseLoginActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.info_flower.BindBankAccountActivity_Cote_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.info_flower.FaceUploadActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.info_flower.InfoUploadActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.info_flower.OcrUploadActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.loan_flower.AddPaystackBankAccountActivity_Niri_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.loan_flower.LoanApplyActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.loan_flower.LoanMainActivity_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.loan_flower.RepayPaystackActivity_Niri_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.login_flower.LoginPhoneNumActivity_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.AppShowInfo_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.bean_flower.process_flower.ProcessInfo_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.CoteBankAccountList_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.LoanApplyDetailInfo_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.LoanLimitInfo_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.LoginInfo_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.NiriBankAccountList_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.OcrInfo_Viet_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.RepayH5Info_gn_cl_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.RepayH5Info_ug_ke_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.RepayInfo_flower;
import com.test.aoner.fanow.test.dialog_flower.LoadingDialog_flower;
import com.test.aoner.fanow.test.dialog_flower.Msg2Dialog;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.analytics.flowerutil.FlowerAnalyticsUtil;
import com.test.aoner.fanow.test.v40.bean.StarWindowConfig_flower;

import org.json.JSONArray;
import org.json.JSONObject;

public class HttpPresenter_flower {

    private static class Inner {
        private static final HttpPresenter_flower instance = new HttpPresenter_flower();
    }

    private HttpPresenter_flower() {}

    public static HttpPresenter_flower getInstance() {
        return Inner.instance;
    }

    private BaseActivity_flower activity;

    public LoadingDialog_flower progressBarDialog;

    public void setActivity(BaseActivity_flower activity) {
        if (progressBarDialog != null && progressBarDialog.isShowing()) {
            progressBarDialog.dismiss();
        }
        if (activity != null) progressBarDialog = new LoadingDialog_flower(activity);
        this.activity = activity;
    }

    public void progressBarDialogShow() {
        if (progressBarDialog == null) return;
        progressBarDialog.show();
    }

    public void progressBarDialogDismiss() {
        if (progressBarDialog == null) return;
        progressBarDialog.dismiss();
    }

    public void showToastOnUiThread(String text) {
        if (activity != null) {
            activity.showToastOnUiThread_flower(text);
        }
    }

    public void notifyActivityHandleResponse(String word, JSONObject responseJson) {
        if (activity == null) return;
        try {
            String gcode = responseJson.optString("code");
            String gmsg = StringUtil_flower.getSafeString(responseJson.optString("msg"));
            Object gobj = responseJson.opt("obj");

            if (StringUtil_flower.safeParseInt(gcode)!=0) {
                onResponseFail(word,gmsg);
                return;
            }

            onResponseSuccess(word,gmsg,gobj);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onResponseSuccess(String word,String gmsg,Object gobj){
        if (gobj instanceof JSONObject) {
            JSONObject objJsonObject = (JSONObject) gobj;
            if (word.equalsIgnoreCase(Upload_Info_Url)||("/"+ Country_Word +word).equalsIgnoreCase(Upload_Info_Url)){
                if (activity instanceof BaseInfoActivity_flower) ((BaseInfoActivity_flower) activity).onInfoUploadResponse_flower();
                return;
            }
            switch (word) {
//                    case PATH_APP_VERSION:
//                        AppVersionInfo.parse(objJsonObject);
//                        if (activity instanceof MainActivity) ((MainActivity) activity).onQueryAppVersionResponse();
//                        break;
                case PATH_GLOBAL:
                    GlobalConfig_flower.parse(objJsonObject);
                    if (activity instanceof MainActivity_flower) ((MainActivity_flower) activity).onGlobalResponse_flower();
                    break;
                case PATH_APP_SHOW_INFO:
                    AppShowInfo_flower.parse(objJsonObject);
                    if (activity instanceof LoginPhoneNumActivity_flower) ((LoginPhoneNumActivity_flower) activity).onAppShowInfoResponse();
                    else if (activity instanceof LoanMainActivity_flower) ((LoanMainActivity_flower) activity).onAppShowInfoResponse();
                    break;
                case PATH_SMS_CODE:
                    if (activity instanceof BaseLoginActivity_flower) ((BaseLoginActivity_flower) activity).onRequestOtpCodeResponse_flower(gmsg,objJsonObject);
                    break;
                case PATH_USER_LOGIN:
                    LoginInfo_flower.parse(objJsonObject);
                    if (activity instanceof BaseLoginActivity_flower) ((BaseLoginActivity_flower) activity).onUserLoginResponse_flower();
                    break;
                case PATH_FEED_BACK:
                    if (activity instanceof ServiceFeedbackActivity_flower) ((ServiceFeedbackActivity_flower) activity).onSubmitServiceFeedback();
                    break;
                case PATH_BIND_BANK_ACCOUNT:
                    if (activity instanceof BindBankAccountActivity_Cote_flower) ((BindBankAccountActivity_Cote_flower) activity).onBindBankAccountResponse();
                    else if (activity instanceof LoanMainActivity_flower) ((LoanMainActivity_flower)activity).onCoteAddAccountResponse();
                    break;
                case PATH_DELETE_BOUND_BANK_ACCOUNT:
                    if (activity instanceof BindBankAccountActivity_Cote_flower) ((BindBankAccountActivity_Cote_flower) activity).onDeleteBankAccountResponse();
                    else if (activity instanceof LoanMainActivity_flower) ((LoanMainActivity_flower)activity).onCoteDeleteAccountResponse();
                    break;
                case PATH_USER_PROCESS:
                    ProcessInfo_flower.parse(objJsonObject.toString());
                    activity.onUserProcessResponse_flower();
                    break;
                case PATH_LOAN_LIMIT:
                    LoanLimitInfo_flower.parse(objJsonObject);
                    if (activity instanceof LoanMainActivity_flower) ((LoanMainActivity_flower) activity).onLoanLimitInfoResponse();
                    break;
                case PATH_GET_LOAN_APPLY_DETAIL:
                    LoanApplyDetailInfo_flower.parse(objJsonObject);
                    if (activity instanceof LoanMainActivity_flower) ((LoanMainActivity_flower) activity).onLoanApplyDetailResponse();
                    else if (activity instanceof BaseInfoActivity_flower) ((BaseInfoActivity_flower) activity).onLoanApplyDetailResponse_flower();
                    else if (activity instanceof LoanApplyActivity_flower) ((LoanApplyActivity_flower) activity).onLoanApplyDetailResponse();
                    break;
                case PATH_LOAN_APPLY:
//                        AFInstallHelper.getInstance().saveEventInfo(AFInstallHelper.AFEvent.AT_LOAN_APPLY);
//                        FirebaseHelper.getInstance().saveEventInfo(AFInstallHelper.AFEvent.AT_LOAN_APPLY);
                    FlowerAnalyticsUtil.INSTANCE.addEvent(FlowerIAnalyticsEvent.AT_LOAN_APPLY);
                    if (activity instanceof LoanApplyActivity_flower) ((LoanApplyActivity_flower) activity).onLoanApplyResponse();
                    break;
                case PATH_REPAY_INFO:
                    RepayInfo_flower.parse(objJsonObject);
                    if (activity instanceof LoanMainActivity_flower) ((LoanMainActivity_flower) activity).onRepayDetailResponse();
                    break;
                case PATH_ORDER_REPAY_H5:
                    RepayH5Info_gn_cl_flower.parse(objJsonObject);
                    if (activity instanceof LoanMainActivity_flower) ((LoanMainActivity_flower) activity).onOrderRepayH5Response(RepayH5Info_gn_cl_flower.getInstance().getUrl());
                    break;
                case PATH_GET_ORDER_REPAY_H5:
                    RepayH5Info_ug_ke_flower.parse(objJsonObject);
                    if (activity instanceof LoanMainActivity_flower) ((LoanMainActivity_flower) activity).onOrderRepayH5Response(RepayH5Info_ug_ke_flower.getInstance().getUrl());
                    break;
                case PATH_ORDER_REPAY_SDK:
                    if (activity instanceof AddPaystackBankAccountActivity_Niri_flower) ((AddPaystackBankAccountActivity_Niri_flower) activity).onOrderRepaySDKResponse(objJsonObject);
                    else if (activity instanceof RepayPaystackActivity_Niri_flower) ((RepayPaystackActivity_Niri_flower) activity).onOrderRepaySDKResponse(objJsonObject);
                    break;
                case PATH_CREATE_VIRTUAL_ACCOUNT:
                    if (activity instanceof LoanMainActivity_flower) ((LoanMainActivity_flower) activity).onInitOfflinePayChannelResponse();
                    break;
                case Path_Get_Discern_Icr_Info:
                    OcrInfo_Viet_flower.parse(objJsonObject);
                    break;
                case Path_Face_Recognition_Check:
                    if (activity instanceof BaseInfoActivity_flower) ((BaseInfoActivity_flower) activity).onInfoUploadResponse_flower();
                    break;
                case Path_Save_Perator_Information:
                    if (activity instanceof BaseInfoActivity_flower) ((BaseInfoActivity_flower) activity).onInfoUploadResponse_flower();
                    break;
                case PATH_UPLOAD_IMAGE_FILE:
                    String url = objJsonObject.optString("url");
                    if (activity instanceof OcrUploadActivity_flower) ((OcrUploadActivity_flower)activity).onPhotoUploadSuccess(url);
                    else if (activity instanceof FaceUploadActivity_flower) ((FaceUploadActivity_flower)activity).onImageUploadSuccess(url);
                    break;
                case PATH_FETCH_STAR_WINDOW_CONFIG:
                    StarWindowConfig_flower.parseFlowerData(objJsonObject);
                    if (activity instanceof LoanMainActivity_flower) ((LoanMainActivity_flower)activity).onStarWindowConfigResponse();
                    break;
                case PATH_OCR_INFO:
                    if (activity instanceof BaseInfoActivity_flower) HttpManager_flower.getInstance().requestUserProcess();
                    break;
                case PATH_USER_CANCEL:
                    activity.onUserCancelSuccess();
                    break;
                default:
                    //throw new IllegalStateException("Unexpected value: " + word);
            }
        } else if (gobj instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) gobj;
            switch (word) {
                case PATH_FETCH_BANK_CODE:
                    if (activity instanceof InfoUploadActivity_flower) ((InfoUploadActivity_flower) activity).onFetchBankCodeResponse(jsonArray);
                    break;
                case PATH_FETCH_BOUND_BANK_ACCOUNT:
                    CoteBankAccountList_flower.parse(jsonArray);
                    if (activity instanceof BindBankAccountActivity_Cote_flower) ((BindBankAccountActivity_Cote_flower) activity).onFetchBankAccountListResponse();
                    else if (activity instanceof LoanMainActivity_flower) ((LoanMainActivity_flower) activity).onFetchBankAccountListResponse();
                    else if (activity instanceof LoanApplyActivity_flower) ((LoanApplyActivity_flower) activity).onFetchBankAccountListResponse_Cote();
                    break;
                case PATH_BANK_LIST:
                    NiriBankAccountList_flower.parse(jsonArray);
                    if (activity instanceof RepayPaystackActivity_Niri_flower) ((RepayPaystackActivity_Niri_flower) activity).onFetchBankListResponse();
                    else if (activity instanceof LoanMainActivity_flower) ((LoanMainActivity_flower) activity).onFetchBankListResponse_Niri();
                    break;
            }
        } else {
            gmsg = StringUtil_flower.getSafeString(gmsg);
            if (TextUtils.isEmpty(gmsg)) {
                gmsg = "Response something error";
            }
            activity.showToast_flower(gmsg);
        }
    }

    private void onResponseFail(String word,String gmsg){

        if (PATH_USER_LOGIN.equalsIgnoreCase(word)){
            new Msg2Dialog(activity,gmsg).show();
            return;
        }

        activity.showToast_flower(gmsg);
        switch (word){
            case Path_Get_Discern_Icr_Info:
//                if (activity instanceof OcrUploadActivity_flower) ((OcrUploadActivity_flower)activity).onDiscernIcrInfoFailResponse();
                break;
            case PATH_UPLOAD_IMAGE_FILE:
//                if (activity instanceof FaceUploadActivity2_Viet_flower) ((FaceUploadActivity2_Viet_flower)activity).onPhotoUploadFailResponse();
                break;
        }
    }

}
