package com.test.aoner.fanow.test.activity_fragment_flower.login_flower;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.test.aoner.fanow.test.analytics.flowerutil.FlowerAnalyticsUtil;
import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseLoginActivity_flower;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.bean_flower.user_info_flower.LoginInfo_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;
import com.test.aoner.fanow.test.view_flower.widget_flower.InfoInputEditText;
import com.test.aoner.fanow.test.view_flower.widget_flower.SelectView_flower;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TimeZone;


public class LoginPhoneNumActivity_flower extends BaseLoginActivity_flower {

    private SelectView_flower countrySv;
    private TextView prefixTv;
    private InfoInputEditText phoneNumEt;

    private TextView mobileTitleTv;

    private ImageButton acceptIb;
    private TextView acceptTv;

    private boolean isAccept = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone_num_flower);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {

        countrySv = findViewById(R.id.activity_login_phone_num_sv_country);
        prefixTv = findViewById(R.id.activity_login_phone_num_tv_prefix);
        phoneNumEt = findViewById(R.id.activity_login_phone_num_et_phone_num);
        mobileTitleTv = findViewById(R.id.activity_login_phone_num_tv_mobile_title);
        acceptIb = findViewById(R.id.activity_login_phone_num_ib_accept);
        acceptTv = findViewById(R.id.activity_login_phone_num_tv_accept);

        phoneNumEt.setBpType(getPagetag()+"_MOBILE_INPUT");

        ArrayList<String> countries = GlobalConfig_flower.getInstance().getCountries();
        ArrayList<String> countriesName = new ArrayList<>();

        findViewById(R.id.activity_login_phone_num_btn_next).setOnClickListener(v -> {

            HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_CONFIRM_CLICK");

            if (TextUtils.isEmpty(countrySv.getInput()) || TextUtils.isEmpty(phoneNumEt.getText())) {
                showToast_flower(getString(R.string.word_input_tip));
                return;
            }

            if (!isAccept){
                String tips = getString(R.string.please)+" "+acceptTv.getText().toString().toLowerCase();
                showToast_flower(tips);
                return;
            }

            String phoneNum = StringUtil_flower.getSafeString(phoneNumEt.getText().toString());

            String accountToken = GlobalConfig_flower.getInstance().isTestAccount(phoneNum);
            if (!TextUtils.isEmpty(accountToken)) {
                UserInfoHelper_flower.getInstance().setMobile(phoneNum);
                try {
                    JSONObject object = new JSONObject();
                    object.put("token", accountToken);
                    object.put("userId", phoneNum);
                    object.put("typ", UserInfoHelper_flower.LOGIN_TYPE_OLD);
                    LoginInfo_flower.parse(object);
                    onUserLoginResponse_flower();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }

            HttpManager_flower.getInstance().requestOtpCode(phoneNum);
        });

        String timeZoneId = TimeZone.getDefault().getID();
        if (timeZoneId.equalsIgnoreCase(Constant_flower.TimeZone_Ug) && countries.contains(Constant_flower.WordPrefix + Constant_flower.Suffix_Uganda)) {
            selectCountry(Constant_flower.Country_Uganda);
            setCountryShow();
            countrySv.setVisibility(View.GONE);
        } else if (timeZoneId.equalsIgnoreCase(Constant_flower.TimeZone_Co) && countries.contains(Constant_flower.WordPrefix + Constant_flower.Suffix_Cote)) {
            selectCountry(Constant_flower.Country_Cote);
            setCountryShow();
            countrySv.setVisibility(View.GONE);
        } else {
            selectCountry(Constant_flower.Country_Tanzan);
            setCountryShow();
            countrySv.setVisibility(View.GONE);
        }

        countrySv.addListItem(countriesName);
        countrySv.setInterceptPickListener(pickStr -> {
            selectCountry(pickStr);
            HttpManager_flower.getInstance().getAppShowInfo();
        });

        acceptIb.setOnClickListener(v -> {
            HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_ACCEPT_CLICK",(!isAccept) ? "YES":"NO");
            setAccept(!isAccept);
        });

        initPrivacyPolicyAndTerm();

        GlobalConfig_flower.getInstance().handleOperatedView(this,
                findViewById(R.id.activity_login_phone_num_view_operated),
                findViewById(R.id.activity_login_phone_num_iv_operated_logo),
                findViewById(R.id.activity_login_phone_num_tv_company_name));

        if (UserInfoHelper_flower.getInstance().didLogin()) {

            countrySv.setInput(UserInfoHelper_flower.getInstance().getCountryName());
            prefixTv.setText(StaticConfig_flower.getMobilePrefix());
            phoneNumEt.setText(UserInfoHelper_flower.getInstance().getMobile());

            selectCountry(UserInfoHelper_flower.getInstance().getCountryName());
        }

        if (UserInfoHelper_flower.getInstance().didLogin() || (!TextUtils.isEmpty(countrySv.getInput())))
            HttpManager_flower.getInstance().getAppShowInfo();

    }

    private void initPrivacyPolicyAndTerm(){

        String privacyPath = null;

        switch (StaticConfig_flower.Select_Country) {
            case Constant_flower.Country_Cote:
                privacyPath = Constant_flower.Privacy_Cote;
                break;
            case Constant_flower.Country_Ghana:
                privacyPath = Constant_flower.Privacy_Ghana;
                break;
            case Constant_flower.Country_Kenya:
                privacyPath = Constant_flower.Privacy_Kenya;
                break;
            case Constant_flower.Country_Uganda:
                privacyPath = Constant_flower.Privacy_Uganda;
                break;
            case Constant_flower.Country_Nigeria:
                privacyPath = Constant_flower.Privacy_Nigeria;
                break;
            case Constant_flower.Country_Vietnam:
                privacyPath = Constant_flower.Privacy_Vietnam;
                break;
            case Constant_flower.Country_Tanzan:
                privacyPath = Constant_flower.Privacy_Tanzan;
                break;
        }

        acceptTv.setText(getString(R.string.accept));
        acceptTv.append(" ");

        final String fpp = privacyPath;
        SpannableString ss1 = new SpannableString(getString(R.string.privacy_policy));
        ss1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_PRIVACY_POLICY_CLICK");
                if (TextUtils.isEmpty(fpp)) showToast_flower(getString(R.string.tips_select_country));
                else startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(fpp)));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(getColor(R.color.green_flower));
            }
        },0,ss1.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        acceptTv.append(ss1);

        final String termOfUse = GlobalConfig_flower.getInstance().getTermOfUsePath();
        if (!TextUtils.isEmpty(termOfUse)){



            acceptTv.append(" ");
            acceptTv.append(getString(R.string.and));
            acceptTv.append(" ");

            SpannableString ss2 = new SpannableString(getString(R.string.terms_of_use));
            ss2.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    HttpManager_flower.getInstance().saveUserBuriedPoint(getPagetag()+"_TERM_OF_USE_CLICK");
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(termOfUse)));
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                    ds.setColor(getColor(R.color.green_flower));
                }
            },0,ss2.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            acceptTv.append(ss2);
        }

        acceptTv.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public void setAccept(boolean accept) {
        isAccept = accept;
        acceptIb.setBackgroundResource(isAccept ? R.drawable.icon_item_check_true:R.drawable.icon_item_check_false);
    }

    private void selectCountry(String countryName){

        StaticConfig_flower.Select_Country = countryName;
        StaticConfig_flower.Country_Word = Constant_flower.WordPrefix + StringUtil_flower.getSafeString(StaticConfig_flower.getCountrySuffix());

        try {
            FlowerAnalyticsUtil.INSTANCE.init(BaseApplication_flower.getApplication_flower());
            FlowerAnalyticsUtil.INSTANCE.initDevice(BaseApplication_flower.getApplication_flower());
            FlowerAnalyticsUtil.INSTANCE.saveFirebaseAndFacebookS2SInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        StaticConfig_flower.Country_Config = GlobalConfig_flower.getInstance().getCountryConfigTreeMap().get(StaticConfig_flower.Country_Word);
        UserInfoHelper_flower.getInstance().setCountryWord(StaticConfig_flower.Country_Word);
        UserInfoHelper_flower.getInstance().setCountryName(StaticConfig_flower.Select_Country);

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Kenya))
            mobileTitleTv.setText(getString(R.string.activity_login_enter_phone_num_ksh));
        else mobileTitleTv.setText(getString(R.string.activity_login_enter_phone_num));

        //HttpManager.getInstance().getAppShowInfo();
    }

    @Override
    public void onRequestOtpCodeResponse_flower(String msg,@NonNull JSONObject jsonObject) {

        String directLoginFlag = StringUtil_flower.getSafeString(jsonObject.optString("directLoginFlag"));

        if (directLoginFlag.equalsIgnoreCase("YES")) {
            HttpManager_flower.getInstance().userLoginOldUser(StringUtil_flower.getSafeString(phoneNumEt.getText().toString()));
            return;
        }

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Vietnam)&&(!TextUtils.isEmpty(msg))){
            BaseApplication_flower.getApplication_flower().setMsg(msg);
        }

        UserInfoHelper_flower.getInstance().setMobile(phoneNumEt.getText().toString());
        startActivity_flower(LoginVerCodeActivity_flower.class);
    }

    public void onAppShowInfoResponse() {
        setCountryShow();
        initPrivacyPolicyAndTerm();

        if (isAccept && UserInfoHelper_flower.getInstance().didLogin()) {
            HttpManager_flower.getInstance().requestUserProcess();
        }
    }

    private void setCountryShow(){

        countrySv.setInput(StaticConfig_flower.Select_Country);

        String mobile = StaticConfig_flower.Country_Config == null ? "" : StaticConfig_flower.Country_Config.getCustomer();
        phoneNumEt.setHint(TextUtils.isEmpty(mobile) ? Constant_flower.CUSTOMER_PHONE : mobile);
        prefixTv.setText(StaticConfig_flower.getMobilePrefix());
    }

    @Override
    public String getPagetag() {
        return "LOGIN_PHONE";
    }

    @Override
    public void onUserProcessResponse_flower() {
        super.onUserProcessResponse_flower();
    }
}