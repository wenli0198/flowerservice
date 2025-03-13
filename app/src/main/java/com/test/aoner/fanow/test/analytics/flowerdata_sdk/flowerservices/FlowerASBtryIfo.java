package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.FlowerASBtryBn;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASUtil;

public class FlowerASBtryIfo {

    private OnBatteryInfoListener mOnBtyIfoListener;
    private IntentFilter mBtyFilter;
    private Context mContext;
    private String mIsCharging = "NO";
    private String mIsUsbCharging = "NO";
    private String mIsAcCharging = "NO";
    private String mCurLevel = "0";

    private BroadcastReceiver mBtyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                if (BatteryManager.BATTERY_STATUS_CHARGING == status ||
                        BatteryManager.BATTERY_STATUS_FULL == status) {
                    mIsCharging = "YES";
                }

                int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                boolean isUsbCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_USB);
                boolean isAcCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_AC);
                if (isUsbCharge) {
                    mIsUsbCharging = "YES";
                    mIsAcCharging = "NO";
                }
                if (isAcCharge) {
                    mIsUsbCharging = "NO";
                    mIsAcCharging = "YES";
                }

                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                mCurLevel = FlowerASUtil.safeString(level + "");

                if (mOnBtyIfoListener != null) {
                    FlowerASBtryBn batteryInfoBean = new FlowerASBtryBn();
                    batteryInfoBean.setIsCrg(mIsCharging);
                    batteryInfoBean.setBtyPct(mCurLevel);
                    batteryInfoBean.setIsAcCrg(mIsAcCharging);
                    batteryInfoBean.setIsUsbCrg(mIsUsbCharging);

                    String zipString = getZipString(batteryInfoBean);
                    mOnBtyIfoListener.onFetchSuccess(zipString);
                }
                clean();

            } catch (Exception e) {
                e.printStackTrace();
                clean();
            }
        }
    };

    public void getBatteryInfo(final Context finContext, final OnBatteryInfoListener batteryInfoListener) {
        this.mContext = finContext;
        this.mOnBtyIfoListener = batteryInfoListener;
        try {
            mBtyFilter = new IntentFilter();
            mBtyFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
            finContext.registerReceiver(mBtyReceiver, mBtyFilter);

        } catch (Exception e) {
            e.printStackTrace();
            finContext.unregisterReceiver(mBtyReceiver);
            if (batteryInfoListener != null) {
                FlowerASBtryBn batteryInfoBean = new FlowerASBtryBn();
                batteryInfoBean.setIsCrg(mIsCharging);
                batteryInfoBean.setBtyPct(mCurLevel);
                batteryInfoBean.setIsAcCrg(mIsAcCharging);
                batteryInfoBean.setIsUsbCrg(mIsUsbCharging);

                String zipString = getZipString(batteryInfoBean);
                batteryInfoListener.onFetchSuccess(zipString);
            }
        }
    }

    public void unRegisterReceiver(Context context) {
        try {
            if (mBtyReceiver != null) {
                context.unregisterReceiver(mBtyReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clean() {
        mContext.unregisterReceiver(mBtyReceiver);
        mBtyReceiver = null;
        mBtyFilter = null;
        mOnBtyIfoListener = null;
    }

    public interface OnBatteryInfoListener {
        void onFetchSuccess(String batteryInfoBean);
    }

    public static String getZipString(@NonNull FlowerASBtryBn batteryInfoBean) {
        String zipString = "";
        try {
            String result = new Gson().toJson(batteryInfoBean).trim();
            zipString = com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil.FlowerASUtil.stringToGZIP(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zipString;
    }

    public void setmOnBtyIfoListener(OnBatteryInfoListener mOnBtyIfoListener) {
        this.mOnBtyIfoListener = mOnBtyIfoListener;
    }

    public void setmBtyFilter(IntentFilter mBtyFilter) {
        this.mBtyFilter = mBtyFilter;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setmIsCharging(String mIsCharging) {
        this.mIsCharging = mIsCharging;
    }

    public void setmIsUsbCharging(String mIsUsbCharging) {
        this.mIsUsbCharging = mIsUsbCharging;
    }

    public void setmIsAcCharging(String mIsAcCharging) {
        this.mIsAcCharging = mIsAcCharging;
    }

    public void setmCurLevel(String mCurLevel) {
        this.mCurLevel = mCurLevel;
    }

    public void setmBtyReceiver(BroadcastReceiver mBtyReceiver) {
        this.mBtyReceiver = mBtyReceiver;
    }

    public OnBatteryInfoListener getmOnBtyIfoListener() {
        return mOnBtyIfoListener;
    }

    public IntentFilter getmBtyFilter() {
        return mBtyFilter;
    }

    public Context getmContext() {
        return mContext;
    }

    public String getmIsCharging() {
        return mIsCharging;
    }

    public String getmIsUsbCharging() {
        return mIsUsbCharging;
    }

    public String getmIsAcCharging() {
        return mIsAcCharging;
    }

    public String getmCurLevel() {
        return mCurLevel;
    }

    public BroadcastReceiver getmBtyReceiver() {
        return mBtyReceiver;
    }

}
