package com.test.aoner.fanow.test.util_flower.helper_flower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower;


public class UserInfoHelper_flower {

    public static final String SP_KEY_TOKEN = "token";
    public static final String SP_KEY_MOBILE = "phoneNum";
    public static final String SP_KEY_USERID = "userid";
    public static final String SP_KEY_COUNTRY_WORD = "country_word";
    public static final String SP_KEY_COUNTRY_NAME = "country_name";

    public static final String SP_KEY_NORMALDIALOG_COUNT_flower = "normal_dialog_count";
    public static final String SP_KEY_ACTIVEDIALOG_COUNT_flower = "active_dialog_count";

    public static final String LOGIN_TYPE_REGISTER = "REGISTER";
    public static final String LOGIN_TYPE_OLD = "OLD";

    public static final String Sp_Key_Language = "flower_language";


    public static String USER_HAD_SELECTED_AMOUNT_FLAG = "NO";

    private final SharedPreferences mSharedPreferences;

    private String mMobile;

    private String mToken;

    private String mUserId;

    private String mCountryWord;

    private String mCountryName;

    private String language;

    private int normaldialogCount;
    private int activedialogCount;

    private static class InnerUserInfoHelper {
        private static final UserInfoHelper_flower instance = new UserInfoHelper_flower();
    }

    public static UserInfoHelper_flower getInstance() {
        return InnerUserInfoHelper.instance;
    }

    private UserInfoHelper_flower() {
        mSharedPreferences = BaseApplication_flower.getApplication_flower().getSharedPreferences(DeviceHelper_flower.getPackageName(), Context.MODE_PRIVATE);
        this.mMobile = mSharedPreferences.getString(SP_KEY_MOBILE, "");
        this.mToken = mSharedPreferences.getString(SP_KEY_TOKEN, "");
        this.mCountryWord = mSharedPreferences.getString(SP_KEY_COUNTRY_WORD, "");
        this.mCountryName = mSharedPreferences.getString(SP_KEY_COUNTRY_NAME, "");
        this.mUserId = mSharedPreferences.getString(SP_KEY_USERID, "");
        normaldialogCount = mSharedPreferences.getInt(SP_KEY_NORMALDIALOG_COUNT_flower,0);
        activedialogCount = mSharedPreferences.getInt(SP_KEY_ACTIVEDIALOG_COUNT_flower,0);
        this.language = mSharedPreferences.getString(Sp_Key_Language,"");
    }

    public void init() {}

    public boolean didLogin() {
        return (!TextUtils.isEmpty(this.mToken)&&(!TextUtils.isEmpty(this.mCountryWord)));
    }

    public String getMobile() {
        mMobile = StringUtil_flower.getSafeString(mMobile);
        return mMobile;
    }

    public String getHideMobile() {
        return StringUtil_flower.bankAccountHideFront(getMobile());
    }

    @SuppressLint("ApplySharedPref")
    public void setMobile(String mobile) {
        this.mMobile = StringUtil_flower.getSafeString(mobile);
        mSharedPreferences.edit().putString(SP_KEY_MOBILE, this.mMobile).commit();
    }

    public String getToken() {
        mToken = StringUtil_flower.getSafeString(mToken);
        return mToken;
    }

    public String getCountryWord() {
        mCountryWord = StringUtil_flower.getSafeString(mCountryWord);
        return mCountryWord;
    }

    public String getCountryName() {
        mCountryName = StringUtil_flower.getSafeString(mCountryName);
        return mCountryName;
    }

    public String getUserId() {
        mUserId = StringUtil_flower.getSafeString(mUserId);
        return mUserId;
    }

    public boolean isLanguageEn(){
        return !"SW".equalsIgnoreCase(language);
    }

    @SuppressLint("ApplySharedPref")
    public void setToken(String token) {
        this.mToken = StringUtil_flower.getSafeString(token);
        mSharedPreferences.edit().putString(SP_KEY_TOKEN, this.mToken).commit();
    }

    @SuppressLint("ApplySharedPref")
    public void setUserId(String userId) {
        this.mUserId = StringUtil_flower.getSafeString(userId);
        mSharedPreferences.edit().putString(SP_KEY_USERID, this.mUserId).commit();
    }

    @SuppressLint("ApplySharedPref")
    public void setCountryWord(String countryWord) {
        this.mCountryWord = StringUtil_flower.getSafeString(countryWord);
        mSharedPreferences.edit().putString(SP_KEY_COUNTRY_WORD, this.mCountryWord).commit();
    }

    @SuppressLint("ApplySharedPref")
    public void setCountryName(String countryName) {
        this.mCountryName = StringUtil_flower.getSafeString(countryName);
        mSharedPreferences.edit().putString(SP_KEY_COUNTRY_NAME, this.mCountryName).commit();
    }

    @SuppressLint("ApplySharedPref")
    public void setLanguage(boolean isEn) {
        this.language = isEn ? "EN":"SW";
        mSharedPreferences.edit().putString(Sp_Key_Language, this.language).commit();
    }

    public boolean hasSetLanguage(){
        return !TextUtils.isEmpty(language);
    }

    public int getNormaldialogCount() {
        return normaldialogCount;
    }

    public int getActivedialogCount() {
        return activedialogCount;
    }

    @SuppressLint("ApplySharedPref")
    public void addNormaldialogCount(){
        normaldialogCount++;
        mSharedPreferences.edit().putInt(SP_KEY_NORMALDIALOG_COUNT_flower,normaldialogCount).commit();
    }

    @SuppressLint("ApplySharedPref")
    public void addActivedialogCount(){
        activedialogCount++;
        mSharedPreferences.edit().putInt(SP_KEY_ACTIVEDIALOG_COUNT_flower,activedialogCount).commit();
    }

}
