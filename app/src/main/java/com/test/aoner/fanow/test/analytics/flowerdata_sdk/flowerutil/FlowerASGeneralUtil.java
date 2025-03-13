package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.Locale;
import java.util.TimeZone;

public class FlowerASGeneralUtil {

    public static String getAndroidID(Context paramContext) {
        String androidID = Settings.System.getString(paramContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(androidID)) {
            androidID = "";
        }
        return androidID;
    }

    public static String getPhoneType(Context paramContext) {
        String valueStr = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                int type = telephonyManager.getPhoneType();
                switch (type) {
                    case TelephonyManager.PHONE_TYPE_NONE: {
                        break;
                    }
                    case TelephonyManager.PHONE_TYPE_GSM: {
                        valueStr = "GSM";
                        break;
                    }
                    case TelephonyManager.PHONE_TYPE_CDMA: {
                        valueStr = "CDMA";
                        break;
                    }
                    case TelephonyManager.PHONE_TYPE_SIP: {
                        valueStr = "SIP";
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valueStr;
    }

    public static String getLanguage(Context paramContext) {
        String str = "";
        try {
            str = Locale.getDefault().getLanguage();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return str;
    }

    public static String getDisplayLanguage(Context paramContext) {
        String str = "";
        try {
            str = Locale.getDefault().getDisplayLanguage();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return str;
    }

    public static String getIso3Language(Context paramContext) {
        String str = "";
        try {
            str = Locale.getDefault().getISO3Language();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return str;
    }

    public static String getIso3Country(Context paramContext) {
        String str = "";
        try {
            str = Locale.getDefault().getISO3Country();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return str;
    }

    public static String getImei(Context context) {
        return "";
    }

    public static String getTimeZoneId(Context context) {
        String tzid = "";
        try {
            TimeZone tz = TimeZone.getDefault();
            tzid = tz.getID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tzid;
    }

    public static String getNetworkType(Context paramContext) {
        String networkType = "";
        try {
            NetworkInfo network = ((ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (network != null && network.isAvailable()
                    && network.isConnected()) {
                int type = network.getType();
                if (type == ConnectivityManager.TYPE_WIFI) {
                    networkType = "WIFI";
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    networkType = "MOBILE";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return networkType;
    }

    public static String getSimOperatorName(Context paramContext) {
        String simName = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                simName = FlowerASUtil.safeString(telephonyManager.getNetworkOperatorName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simName;
    }

    public static String getIsDebug(Context context) {
        String isDebugStr = "NO";
        try {
            boolean isDebug = context.getApplicationInfo() != null && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            if (isDebug) {
                isDebugStr = "YES";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDebugStr;
    }

    public static String getElapsedRealtime(Context context) {
        String timeStr = "";
        try {
            timeStr = FlowerASUtil.safeString(String.valueOf(SystemClock.elapsedRealtime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStr;
    }

    public static String getLastBootTime(Context context) {
        String timeStr = "";
        try {
            ;
            timeStr = FlowerASUtil.safeString(String.valueOf((System.currentTimeMillis() - SystemClock.elapsedRealtime())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStr;
    }

    public static String getVersionName(Context paramContext) {
        String str = "1.0";
        try {
            PackageManager pm = paramContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(paramContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                if (!TextUtils.isEmpty(pi.versionName)) {
                    str = pi.versionName;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return str;
    }

    public static String getVersionCode(Context paramContext) {
        String str = "1";
        try {
            PackageManager pm = paramContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(paramContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                str = String.valueOf(pi.versionCode);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return str;
    }

    public static String getSimCardState(Context paramContext) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager == null) {
                return "NO_SIM";
            }
            int simstate = telephonyManager.getSimState();
            switch (simstate) {
                case TelephonyManager.SIM_STATE_ABSENT: {
                    return "NO_SIM";
                }
                case TelephonyManager.SIM_STATE_UNKNOWN: {
                    return "UNKNOWN";
                }
                case TelephonyManager.SIM_STATE_NETWORK_LOCKED: {
                    return "NETWORK_LOCKED";
                }
                case TelephonyManager.SIM_STATE_PIN_REQUIRED: {
                    return "PIN_LOCKED";
                }
                case TelephonyManager.SIM_STATE_PUK_REQUIRED: {
                    return "PUK_LOCKED";
                }
                case TelephonyManager.SIM_STATE_READY: {
                    return "YES";
                }
                case TelephonyManager.SIM_STATE_NOT_READY: {
                    return "NOT_READY";
                }
                case TelephonyManager.SIM_STATE_PERM_DISABLED: {
                    return "DISABLED";
                }
                case TelephonyManager.SIM_STATE_CARD_IO_ERROR: {
                    return "PRESENT_BUT_FAULT";
                }
                case TelephonyManager.SIM_STATE_CARD_RESTRICTED: {
                    return "CARRIER_RESTRICTIONS";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "NO_SIM";
    }

    public static String getPhoneNumber(Context paramContext) {
        return "";
    }

    public static String getArea(Context paramContext) {
        try {
            return Locale.getDefault().getISO3Country();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCpuModel(Context paramContext) {
        String cpuStr = "";
        try {
            cpuStr = Build.SUPPORTED_ABIS[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cpuStr;
    }

    public static String getDeviceHeight(Context paramContext) {
        String heightPixels = "";
        try {
            DisplayMetrics displayMetrics = paramContext.getResources().getDisplayMetrics();
            if (displayMetrics != null) {
                heightPixels = String.valueOf(displayMetrics.heightPixels);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return heightPixels;
    }

    public static String getDeviceWidth(Context paramContext) {
        String heightPixels = "";
        try {
            DisplayMetrics displayMetrics = paramContext.getResources().getDisplayMetrics();
            if (displayMetrics != null) {
                heightPixels = String.valueOf(displayMetrics.widthPixels);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return heightPixels;
    }


}
