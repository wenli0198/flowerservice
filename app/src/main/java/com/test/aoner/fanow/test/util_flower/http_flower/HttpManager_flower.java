package com.test.aoner.fanow.test.util_flower.http_flower;

import static com.test.aoner.fanow.test.bean_flower.StaticConfig_flower.Country_Word;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower;
import com.test.aoner.fanow.test.analytics.flowerutil.FlowerAnalyticsUtil;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.constant_flower.Url_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.ThreadUtil_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.v40.listener.OnVietSimInfoResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpManager_flower {

    private HttpManager_flower() {

        if (Constant_flower.DebugFlag)
            mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(mHttpLoggingInterceptor)
                .callTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(6, 4, TimeUnit.MINUTES))
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .sslSocketFactory(Objects.requireNonNull(SSLTool.INSTANCE.createSSLSocketFactory()), new SSLTool.TrustAllCerts())
                .hostnameVerifier(new SSLTool.TrustAllHostnameVerifier())
                .build();
    }

    private static class Inner {
        private static final HttpManager_flower instance = new HttpManager_flower();
    }

    public static HttpManager_flower getInstance() {
        return Inner.instance;
    }

    private static OkHttpClient mClient;

    private static final HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLogger_flower());

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            HttpPresenter_flower.getInstance().showToastOnUiThread("Request Fail");
            ThreadUtil_flower.getInstance().runOnUiThread(() -> HttpPresenter_flower.getInstance().progressBarDialogDismiss());
            if (Constant_flower.DebugFlag) Log.e("Request Fail", call.request().toString());
        }

        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            String body = Objects.requireNonNull(response.body()).string();
            ThreadUtil_flower.getInstance().runOnUiThread(() -> HttpPresenter_flower.getInstance().progressBarDialogDismiss());
            try {
                String word = StringUtil_flower.getUrlSuffix(response.request().url().toString());
                handleResponse(word, new JSONObject(body));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void handleResponse(String lastWord, JSONObject responseJson) {
        ThreadUtil_flower.getInstance().runOnUiThread(() -> HttpPresenter_flower.getInstance().notifyActivityHandleResponse(lastWord, responseJson));
    }

    @NonNull
    public JSONObject getRequestJson() {
        return getRequestJson(new JSONObject());
    }

    private JSONObject getRequestJson(JSONObject jsonObject) {
        try {
            jsonObject.put("isLimitAdTrackingEnabled", FlowerAnalyticsUtil.INSTANCE.getAdEnabled() + "");
            jsonObject.put("aduid", FlowerAnalyticsUtil.INSTANCE.getAdId());
            jsonObject.put("aduidPath", FlowerAnalyticsUtil.INSTANCE.getAndroidId());
            jsonObject.put("version", Constant_flower.APP_VERSION.trim());
            jsonObject.put("token", UserInfoHelper_flower.getInstance().getToken().trim());
            jsonObject.put("androidId", FlowerAnalyticsUtil.INSTANCE.getAndroidId());
            jsonObject.put("userId", UserInfoHelper_flower.getInstance().getUserId().trim());
            jsonObject.put("advertId", FlowerAnalyticsUtil.INSTANCE.getAdId());
            jsonObject.put("product", Constant_flower.PRODUCT.trim());
            jsonObject.put("clientType", Constant_flower.CLIENT.trim());
            jsonObject.put("apiVersion", "v3");
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void request(String lastWord, String jsonString) {
        if (!StaticConfig_flower.hasCountryWord()) return;
        requestWithoutCountryPrefix(StaticConfig_flower.getCountryUrlPrefix() + lastWord, jsonString);
    }

    private void requestWithoutCountryPrefix(String lastWord, String jsonString) {

        if (Constant_flower.DebugFlag)
            Log.w("", "Request:" + StaticConfig_flower.getBaseUrl() + lastWord);

        ThreadUtil_flower.getInstance().runOnUiThread(() -> HttpPresenter_flower.getInstance().progressBarDialogShow());

        ThreadUtil_flower.getInstance().runOnChildThread(() -> {
            MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
            Request request = new Request.Builder()
                    .url(StaticConfig_flower.getBaseUrl() + lastWord)
                    .post(RequestBody.Companion.create(jsonString, mediaType))
                    .build();
            Call call = mClient.newCall(request);
            call.enqueue(callback);
        });
    }

    public void queryGlobal() {

        requestWithoutCountryPrefix(Url_flower.PATH_GLOBAL, getRequestJson().toString());

//        String fullUrl = StaticConfig_flower.getBaseUrl();
//
//        if (Constant_flower.DebugFlag)
//            Log.w("", "Request:" + fullUrl);
//
//        ThreadUtil_flower.getInstance().runOnUiThread(() -> HttpPresenter_flower.getInstance().progressBarDialogShow());
//
//        ThreadUtil_flower.getInstance().runOnChildThread(() -> {
//            MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
//            Request request = new Request.Builder()
//                    .url(fullUrl)
//                    .post(RequestBody.Companion.create(getRequestJson().toString(), mediaType))
//                    .build();
//            Call call = mClient.newCall(request);
//            call.enqueue(callback);
//        });
    }


    public void getAppShowInfo() {
        request(Url_flower.PATH_APP_SHOW_INFO, getRequestJson().toString());
    }

    public void requestOtpCode(String phoneNum) {
        UserInfoHelper_flower.getInstance().setMobile(StringUtil_flower.getSafeString(phoneNum));
        JSONObject jsonObject = getRequestJson();
        try {
            //jsonObject.put("channelCode", GPInstallHelper.getInstance().getInstallReferrer().trim());
            //jsonObject.put("subChannelCode", GPInstallHelper.getInstance().getInstallReferrer().trim());
            //jsonObject.put("code", "");
            jsonObject.put("mobile", phoneNum.trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(Url_flower.PATH_SMS_CODE, jsonObject.toString());
    }


    public void userLogin(String phoneNum, String verCode) {
        UserInfoHelper_flower.getInstance().setMobile(StringUtil_flower.getSafeString(phoneNum));
        JSONObject jsonObject = getRequestJson();
        try {
            //tag2024
            jsonObject.put("channelCode", "FLOWERLOAN_GOOGLEAD");
            jsonObject.put("subChannelCode", "gclid=EAIaIQobChMI8siBrePXiAMVp0P2CB1c4jNIEAEYASAAEgKm4fD_BwE");


//            jsonObject.put("channelCode", FlowerAnalyticsUtil.INSTANCE.getInstallReferrer());
//            jsonObject.put("subChannelCode", FlowerAnalyticsUtil.INSTANCE.getInstallReferrer());
            jsonObject.put("code", verCode.trim());
            jsonObject.put("mobile", phoneNum.trim());
            jsonObject.put("notBindWalletFlag", "YES");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(Url_flower.PATH_USER_LOGIN, jsonObject.toString());
    }

    public void userLoginOldUser(String phoneNum) {
        UserInfoHelper_flower.getInstance().setMobile(StringUtil_flower.getSafeString(phoneNum));
        JSONObject jsonObject = getRequestJson();
        try {
            //tag2024
            jsonObject.put("channelCode", "FLOWERLOAN_GOOGLEAD");
            jsonObject.put("subChannelCode", "gclid=EAIaIQobChMI8siBrePXiAMVp0P2CB1c4jNIEAEYASAAEgKm4fD_BwE");

//            jsonObject.put("channelCode", FlowerAnalyticsUtil.INSTANCE.getInstallReferrer());
//            jsonObject.put("subChannelCode", FlowerAnalyticsUtil.INSTANCE.getInstallReferrer());
            jsonObject.put("code", "0000");
            jsonObject.put("mobile", phoneNum.trim());
            jsonObject.put("directLoginFlag", "YES");
            jsonObject.put("notBindWalletFlag", "YES");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(Url_flower.PATH_USER_LOGIN, jsonObject.toString());
    }

    public void submitServiceFeedback(String type, String opinion) {
        JSONObject jsonObject = getRequestJson();
        try {
            jsonObject.put("type", type);
            jsonObject.put("opinion", opinion);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(Url_flower.PATH_FEED_BACK, jsonObject.toString());
    }

    public void fetchBankCodes_Niri() {
        request(Url_flower.PATH_FETCH_BANK_CODE, getRequestJson().toString());
    }

    public void fetchBoundBankAccount_Cote() {
        request(Url_flower.PATH_FETCH_BOUND_BANK_ACCOUNT, getRequestJson().toString());
    }

    public void bindBankAccount(String walletMobile, String walletMobileConfirm, String walletChannel) {
        JSONObject jsonObject = getRequestJson();
        try {
            jsonObject.put("walletMobile", walletMobile);
            jsonObject.put("walletMobileConfirm", walletMobileConfirm);
            jsonObject.put("walletChannel", walletChannel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(Url_flower.PATH_BIND_BANK_ACCOUNT, jsonObject.toString());
    }


    public void deleteBoundBankAccount(String ids) {
        JSONObject jsonObject = getRequestJson();
        try {
            jsonObject.put("ids", ids);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(Url_flower.PATH_DELETE_BOUND_BANK_ACCOUNT, jsonObject.toString());
    }

    public void requestUserProcess() {
        request(Url_flower.PATH_USER_PROCESS, getRequestJson().toString());
    }

    public void uploadInfo(String url, TreeMap<String, String> dataMap) {
        try {
            JSONObject jsonObject = getRequestJson();
            for (String k : dataMap.keySet()) {
                String key = StringUtil_flower.getSafeString(k);
                String value = StringUtil_flower.getSafeString(dataMap.get(k));
                jsonObject.put(key, value);
            }
            StaticConfig_flower.Upload_Info_Url = url;
            String prefix = "/" + Country_Word;
            if (!url.startsWith(prefix)) url = prefix + url;
            requestWithoutCountryPrefix(url, jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queryLoanLimit() {
        request(Url_flower.PATH_LOAN_LIMIT, getRequestJson().toString());
    }

    public void requestLoanApplyDetail() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("localLanguage", UserInfoHelper_flower.getInstance().isLanguageEn() ? "NO" : "YES");
        } catch (JSONException e) {
            if (Constant_flower.DebugFlag) e.printStackTrace();
        }
        request(Url_flower.PATH_GET_LOAN_APPLY_DETAIL, getRequestJson(jsonObject).toString());
    }

    public void loanApply(String language) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("language", language);
        } catch (JSONException e) {
            if (Constant_flower.DebugFlag) e.printStackTrace();
        }

        request(Url_flower.PATH_LOAN_APPLY, getRequestJson(jsonObject).toString());
    }

    public void getRepayDetail() {
        request(Url_flower.PATH_REPAY_INFO, getRequestJson().toString());
    }

    public void orderRepayH5_gh_cl_vi(String deferredFlag) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("deferredFlag", deferredFlag.trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(Url_flower.PATH_ORDER_REPAY_H5, getRequestJson(jsonObject).toString());
    }

    public void orderRepayH5_ug_ke(String deferredFlag) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("deferredFlag", deferredFlag.trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(Url_flower.PATH_GET_ORDER_REPAY_H5, getRequestJson(jsonObject).toString());
    }

    public void orderRepaySDK(String cardNumber, String expiryYear, String expiryMonth, String cvv) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cardNumber", cardNumber.trim());
            jsonObject.put("expiryYear", expiryYear.trim());
            jsonObject.put("expiryMonth", expiryMonth.trim());
            jsonObject.put("cvv", cvv.trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(Url_flower.PATH_ORDER_REPAY_SDK, getRequestJson(jsonObject).toString());
    }

    public void bankList_niri() {
        request(Url_flower.PATH_BANK_LIST, getRequestJson().toString());
    }

    public void createVirtualAccount() {
        request(Url_flower.PATH_CREATE_VIRTUAL_ACCOUNT, getRequestJson().toString());
    }

    public void getDiscernIcrInfo(String imageUrl) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("imageUrl", imageUrl);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        request(Url_flower.Path_Get_Discern_Icr_Info, getRequestJson(jsonObject).toString());
    }


    public void uploadOcrInfo(String cardType, String frontImgUrl, String backImgUrl) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cardImgUrl", frontImgUrl);
            jsonObject.put("cardBackImgUrl", StringUtil_flower.getSafeString(backImgUrl));
            jsonObject.put("cardType", cardType);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        request(Url_flower.PATH_OCR_INFO, getRequestJson(jsonObject).toString());
    }

    public void uploadSefieInfo(String faceImgUrl) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("faceImgUrl", faceImgUrl);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        request(Url_flower.PATH_OCR_INFO, getRequestJson(jsonObject).toString());
    }

    public void saveUserBuriedPoint(String pageEvent) {
        new Thread(() -> {
            try {
                JSONObject jsonObject = getRequestJson();
                jsonObject.put("type", Constant_flower.PRODUCT + "_" + pageEvent);

                final String gurl = StaticConfig_flower.getBaseUrl() + StaticConfig_flower.getFrontByTimezone() + Url_flower.PATH_USER_BURIED_POINT;
                Request request = new Request.Builder()
                        .url(gurl)
                        .post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString()))
                        .build();

                Call call = mClient.newCall(request);
                call.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void saveUserBuriedPoint(String pageEvent, String remark) {
        new Thread(() -> {
            try {
                JSONObject jsonObject = getRequestJson();
                jsonObject.put("type", Constant_flower.PRODUCT + "_" + pageEvent);
                jsonObject.put("remark", remark);


                final String gurl = StaticConfig_flower.getBaseUrl() + StaticConfig_flower.getFrontByTimezone() + Url_flower.PATH_USER_BURIED_POINT;
                Request request = new Request.Builder()
                        .url(gurl)
                        .post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString()))
                        .build();

                Call call = mClient.newCall(request);
                call.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void saveFirebaseS2SInfo() {
        new Thread(() -> {
            try {
                String gversion = BaseApplication_flower.getApplication_flower()
                        .getPackageManager()
                        .getPackageInfo(BaseApplication_flower.getApplication_flower().getPackageName(), PackageManager.GET_META_DATA)
                        .versionName;

                JSONObject jsonObject = getRequestJson();
                jsonObject.put("name", Constant_flower.NAME);
                jsonObject.put("OSAndVersion", ("Android " + Build.VERSION.RELEASE));
                jsonObject.put("locale", Locale.getDefault().toString());
                jsonObject.put("device", Build.MODEL);
                jsonObject.put("appVersion", gversion);
                jsonObject.put("sdkVersion", gversion);
                jsonObject.put("build", "Build/" + Build.ID);
                jsonObject.put("lat", FlowerAnalyticsUtil.INSTANCE.getAdEnabled() + "");
//                jsonObject.put("firebaseAppId", FirebaseHelper.FIREBASE_APPID);
//                jsonObject.put("ga4AppInstanceId", FirebaseHelper.GA4_APP_INSTANCE_ID);

                final String gurl = StaticConfig_flower.getBaseUrl() + Url_flower.PATH_FIREBASE_S2S;
                Request request = new Request.Builder()
                        .url(gurl)
                        .post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString()))
                        .build();

                Call call = mClient.newCall(request);
                call.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void uploadImage(String filePath_equitable) {
        if (Looper.myLooper() != Looper.getMainLooper())
            ThreadUtil_flower.getInstance().runOnUiThread(() -> HttpPresenter_flower.getInstance().progressBarDialogShow());
        else HttpPresenter_flower.getInstance().progressBarDialogShow();

        try {
            if (!TextUtils.isEmpty(filePath_equitable)) {
                String fileSuffix_equitable = filePath_equitable.endsWith(".png") ? "image/png" : "image/jpg";
                final MediaType mediaType_equitable = MediaType.parse(fileSuffix_equitable);
                final File imageFile_equitable = new File(filePath_equitable);
                final String uplodUrl_equitable = StaticConfig_flower.getBaseUrl() + StaticConfig_flower.getCountryUrlPrefix() + Url_flower.PATH_UPLOAD_IMAGE_FILE;

                if (imageFile_equitable.isFile()) {
                    RequestBody imageBody = RequestBody.create(mediaType_equitable, imageFile_equitable);

                    RequestBody requestBody_equitable = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("image", imageFile_equitable.getName(), imageBody)
                            .build();

                    Request request_equitable = new Request.Builder()
                            .url(uplodUrl_equitable)
                            .post(requestBody_equitable)
                            .build();

                    Call call = mClient.newCall(request_equitable);
                    call.enqueue(callback);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchStarWindowConfig() {
        request(Url_flower.PATH_FETCH_STAR_WINDOW_CONFIG, getRequestJson().toString());
    }

    public void vietSimInfo(String step, String mobile, String company, String otp, OnVietSimInfoResponseListener onResponseListener) {

        JSONObject jsonObject = getRequestJson();
        try {
            jsonObject.put("step", step);
            jsonObject.put("cell", mobile);
            jsonObject.put("company", company);
            jsonObject.put("otp", otp);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        ThreadUtil_flower.getInstance().runOnUiThread(() -> HttpPresenter_flower.getInstance().progressBarDialogShow());

        ThreadUtil_flower.getInstance().runOnChildThread(() -> {
            MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
            Request request = new Request.Builder()
                    .url(StaticConfig_flower.getBaseUrl() + Url_flower.PATH_VIET_SIM_INFO)
                    .post(RequestBody.Companion.create(jsonObject.toString(), mediaType))
                    .build();
            Call call = mClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    HttpPresenter_flower.getInstance().showToastOnUiThread("Request Fail");
                    ThreadUtil_flower.getInstance().runOnUiThread(() -> HttpPresenter_flower.getInstance().progressBarDialogDismiss());
                }

                @Override
                public void onResponse(@NonNull Call call, Response response) throws IOException {
                    String body = Objects.requireNonNull(response.body()).string();
                    ThreadUtil_flower.getInstance().runOnUiThread(() -> HttpPresenter_flower.getInstance().progressBarDialogDismiss());
                    try {

                        JSONObject responseJson = new JSONObject(body);

                        String code = responseJson.optString("code");
                        String msg = StringUtil_flower.getSafeString(responseJson.optString("msg"));
                        Object obj = responseJson.opt("obj");

                        if (StringUtil_flower.safeParseInt(code) != 0) {
                            if (onResponseListener != null)
                                ThreadUtil_flower.getInstance().runOnUiThread(() -> onResponseListener.onResponseFail(msg));
                            return;
                        }

                        if (onResponseListener != null && obj instanceof JSONObject)
                            ThreadUtil_flower.getInstance().runOnUiThread(() -> onResponseListener.onResponseSuccess((JSONObject) obj));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        });

    }

    public void userCancel() {
        request(Url_flower.PATH_USER_CANCEL, getRequestJson().toString());
    }


}
