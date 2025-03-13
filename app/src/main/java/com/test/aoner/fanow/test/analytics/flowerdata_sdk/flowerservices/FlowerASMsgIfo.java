package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerservices;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.FlowerASMsBn;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASUtil;

import java.util.ArrayList;
import java.util.List;

public class FlowerASMsgIfo {

    private final static String TAG = "ASMessageInfo";

    public static final String FET_OK = "FET_OK";
    public static final String FET_OK_CAT_ERR = "FET_OK_CAT_ERR";
    public static final String FET_NON = "FET_NON";
    public static final String FET_NON_PER = "FET_NON_PER";
    public static final String FET_NON_CAT_ERR = "FET_NON_CAT_ERR";
    public static String mFType = FET_OK;
    public static boolean mIsFinish = false;
    public static boolean mIsErr = false;
    public static int fetchResult = 0;

    public static List<FlowerASMsBn> getDatasInDays(Context paramContext, int days) {
        mIsFinish = false;
        mIsErr = false;
        Cursor cursor = null;

        ArrayList<FlowerASMsBn> arrayList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(paramContext, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            return arrayList;
        }

        try {
            String[] projection = new String[]{
                    Telephony.Sms._ID,
                    Telephony.Sms.ADDRESS,
                    Telephony.Sms.PERSON,
                    Telephony.Sms.BODY,
                    Telephony.Sms.DATE,
                    Telephony.Sms.TYPE,
                    Telephony.Sms.PROTOCOL,
                    Telephony.Sms.READ,
                    Telephony.Sms.STATUS
            };

            long startTime = FlowerASUtil.getFilterStartTime(days);
            String selection = "DATE >= " + startTime;

            ContentResolver contentResolver = paramContext.getContentResolver();
            cursor = contentResolver.query(
                    Telephony.Sms.CONTENT_URI,
                    projection,
                    selection,
                    null,
                    Telephony.Sms.DEFAULT_SORT_ORDER);

            if (cursor == null) {
                mFType = FET_NON;
                return arrayList;
            }

            while (cursor.moveToNext()) {
                FlowerASMsBn bean = new FlowerASMsBn();
                int h = cursor.getColumnIndex(Telephony.Sms.PERSON);
                int j = cursor.getColumnIndex(Telephony.Sms.BODY);
                int k = cursor.getColumnIndex(Telephony.Sms.DATE);
                int m = cursor.getColumnIndex(Telephony.Sms.TYPE);
                int n = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
                int o = cursor.getColumnIndex(Telephony.Sms._ID);
                int p = cursor.getColumnIndex(Telephony.Sms.PROTOCOL);
                int q = cursor.getColumnIndex(Telephony.Sms.READ);
                int r = cursor.getColumnIndex(Telephony.Sms.STATUS);

                String str1 = FlowerASUtil.safeString(cursor.getString(h));
                String str2 = FlowerASUtil.safeString(cursor.getString(m));
                String str3 = FlowerASUtil.safeString(cursor.getString(n));
                String str4 = FlowerASUtil.safeString(cursor.getString(j));
                String str5 = FlowerASUtil.safeString(cursor.getString(k));
                String str6 = FlowerASUtil.safeString(cursor.getString(o));
                String str7 = FlowerASUtil.safeString(cursor.getString(p));
                String str8 = FlowerASUtil.safeString(cursor.getInt(q) + "");
                String str9 = FlowerASUtil.safeString(cursor.getInt(r) + "");

                if (TextUtils.isEmpty(str1)) {
                    str1 = str3;
                }

                bean.setNm(str1);
                bean.setMb(str3);

                if (str2.equals("1")) {
                    bean.setTp("RECEIVE");
                } else {
                    bean.setTp("SEND");
                }

                if (str7.equals("1")) {
                    bean.setPt("MMS");
                } else {
                    bean.setPt("SMS");
                }
                bean.setSt(str5);
                bean.setPs(str1);
                bean.setTpO(str2);
                bean.setCt(str4);
                bean.setCd(str6);
                bean.setPtO(str7);
                bean.setRd(str8);
                bean.setSn(str8);
                bean.setSbj("");
                bean.setStt(str9);
                bean.setDS(str5);
                arrayList.add(bean);
            }

        } catch (Exception e) {
            mIsErr = true;
            e.printStackTrace();
        } finally {
            mIsFinish = true;
            if (cursor != null) {
                cursor.close();
            }
        }

        if (arrayList.size() == 0) {
            mFType = (mIsErr ? FET_NON_CAT_ERR : FET_NON);
            fetchResult = 2;

        } else {
            mFType = (mIsErr ? FET_OK_CAT_ERR : FET_OK);
            fetchResult = 1;
        }

        return arrayList;
    }

    public static String getZipString(Context paramContext) {
        String zipString = "";
        try {
            List<FlowerASMsBn> beans = getDatasInDays(paramContext, -180);
            String result = new Gson().toJson(beans).trim();
            Log.w("---data_sms---", result );
            zipString = FlowerASUtil.stringToGZIP(result);
            beans.clear();
        } catch (Exception e) {
            e.printStackTrace();
            mFType = FET_NON;
        }
        return zipString;
    }

    public static void setmFType(String mFType) {
        FlowerASMsgIfo.mFType = mFType;
    }

    public static void setmIsFinish(boolean mIsFinish) {
        FlowerASMsgIfo.mIsFinish = mIsFinish;
    }

    public static void setmIsErr(boolean mIsErr) {
        FlowerASMsgIfo.mIsErr = mIsErr;
    }

    public static void setFetchResult(int fetchResult) {
        FlowerASMsgIfo.fetchResult = fetchResult;
    }

    public static String getmFType() {
        return mFType;
    }

    public static boolean ismIsFinish() {
        return mIsFinish;
    }

    public static boolean ismIsErr() {
        return mIsErr;
    }

    public static int getFetchResult() {
        return fetchResult;
    }

}
