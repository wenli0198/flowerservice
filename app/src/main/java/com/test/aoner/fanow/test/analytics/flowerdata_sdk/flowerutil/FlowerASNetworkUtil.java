package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class FlowerASNetworkUtil {

    public static final String KEY_BSSID = "bssid";
    public static final String KEY_SSID = "ssid";
    public static final String KEY_MAC = "mac";

    public static String getMacAddress(Context context) {
        String macAddress = "";
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                StringBuilder stringBuilder = new StringBuilder();
                NetworkInterface networkInterface;
                byte[] arrayOfByte;
                if ((arrayOfByte = (networkInterface = enumeration.nextElement()).getHardwareAddress()) == null || arrayOfByte.length == 0)
                    continue;
                int i = arrayOfByte.length;
                for (byte b1 : arrayOfByte) {
                    stringBuilder.append(String.format("%02X:", b1));
                }
                if (stringBuilder.length() > 0)
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                String str1 = stringBuilder.toString();
                if (!networkInterface.getName().equals("wlan0")) {
                    str1 = macAddress;
                }
                macAddress = str1;
            }
        } catch (SocketException socketException) {
            socketException.printStackTrace();
        }
        if (TextUtils.isEmpty(macAddress)) {
            macAddress = "";
        }
        return macAddress;
    }

    public static String getIPAddress(Context paramContext) {
        try {
            NetworkInfo info = ((ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    try {
                        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                            NetworkInterface anInterface = en.nextElement();
                            for (Enumeration<InetAddress> enumIpAddr = anInterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                                InetAddress inetAddress = enumIpAddr.nextElement();
                                if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                    return inetAddress.getHostAddress();
                                }
                            }
                        }
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }

                } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    @SuppressLint("WifiManagerPotentialLeak")
                    WifiManager wifiManager = (WifiManager) paramContext.getSystemService(Context.WIFI_SERVICE);
                    if (wifiManager == null) {
                        return "";
                    }
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if (wifiInfo == null) {
                        return "";
                    }
                    return converToIPString(wifiInfo.getIpAddress());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String converToIPString(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    @SuppressLint("WifiManagerPotentialLeak")
    public static String getCurrentWifi(Context context) {
        String curWifi = "";
        try {
            JSONObject jsonObject = new JSONObject();
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                String bssid = FlowerASUtil.safeString(wifiInfo.getBSSID());
                String ssid = FlowerASUtil.safeString((wifiInfo.getSSID().replace("\"", "")));
                String mac = FlowerASUtil.safeString(getMacAddress(context));
                if (TextUtils.isEmpty(bssid)) {
                    ssid = "";
                }
                jsonObject.put(KEY_BSSID, bssid);
                jsonObject.put(KEY_SSID, ssid);
                jsonObject.put(KEY_MAC, mac);
            }
            curWifi = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curWifi;
    }

    public static String getConfiguredWifi(Context context) {
        return "";
    }

    public static String getWifiCount(Context context) {
        return "";
    }

    public static String getVpnState(Context paramContext) {

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_VPN) {
                return "YES";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "NO";
    }

    public static String getIsWifiProxy(Context context) {
        try {
            String proxyHost = System.getProperty("http.proxyHost");
            String proxyPort = System.getProperty("http.proxyPort");
            if (proxyHost != null && proxyPort != null) {
                return "YES";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NO";
    }

}
