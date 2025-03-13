package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans;

import static com.test.aoner.fanow.test.analytics.flowerdata_sdk.FlowerASBuilder.KEY_ADVERT_ID;
import static com.test.aoner.fanow.test.analytics.flowerdata_sdk.FlowerASBuilder.KEY_ANDROID;
import static com.test.aoner.fanow.test.analytics.flowerdata_sdk.FlowerASBuilder.KEY_ORDER_ID;
import static com.test.aoner.fanow.test.analytics.flowerdata_sdk.FlowerASBuilder.KEY_SDK_VERSION;
import static com.test.aoner.fanow.test.analytics.flowerdata_sdk.FlowerASBuilder.KEY_TZ;

import android.os.Build;

import com.google.gson.Gson;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;

public class FlowerASDecBn {

    public String manufacturer = Build.MANUFACTURER;
    public String laguage;
    public String area;
    public String ip2;
    public String networkEnvironment;
    public String cpu;
    public String tz;
    public String phoneModel = Build.PRODUCT;
    public String sysVersion = Build.VERSION.RELEASE;
    public String deviceName = Build.DEVICE;
    public String brand = Build.BRAND;
    public String screenWidth;
    public String mac;
    public String screenHeight;
    public String app;
    public String token;
    public String product;
    public String version;
    public String androidId;
    public String advertId;
    public String orderId;
    public String imei;
    public String networkData;
    public String frontCameraPixels;
    public String rearCameraPixels;
    public String ram;
    public String rom;
    public String ip;


    public FlowerASDecBn(){
        this.app = Constant_flower.PRODUCT;
        this.product = Constant_flower.PRODUCT;
        this.token = UserInfoHelper_flower.getInstance().getToken();
        this.version = KEY_SDK_VERSION;
        this.androidId = KEY_ANDROID;
        this.orderId = KEY_ORDER_ID;
        this.advertId = KEY_ADVERT_ID;
        this.tz = KEY_TZ;
    }


    public String getDeviceName() {return deviceName;}
    public void setDeviceName(String deviceName) {this.deviceName = deviceName;}
    public String getBrand() {return brand;}
    public void setBrand(String brand) {this.brand = brand;}
    public void setVersion(String version) {this.version = version;}
    public String getPhoneModel() {return phoneModel;}
    public void setPhoneModel(String phoneModel) {this.phoneModel = phoneModel;}
    public String getSysVersion() {return sysVersion;}
    public void setSysVersion(String sysVersion) {this.sysVersion = sysVersion;}
    public String getCu() {return cpu;}
    public void setCu(String cpu) {
        this.cpu = cpu;
    }
    public String getApp() {return app;}
    public void setApp(String app) {this.app = app;}
    public String getProduct() {return product;}
    public void setProduct(String product) {this.product = product;}
    public String getIp2() {return ip2;}
    public void setIp2(String ip2) {this.ip2 = ip2;}
    public String toParams(){
        return new Gson().toJson(this).trim()+"";
    }
    public void setScreenWidth(String screenWidth) {
        this.screenWidth = screenWidth;
    }
    public String getNetworkData() {return networkData;}
    public void setNetworkData(String networkData) {this.networkData = networkData;}
    public String getFrontCameraPixels() {return frontCameraPixels;}
    public String getManufacturer() {return manufacturer;}
    public void setManufacturer(String manufacturer) {this.manufacturer = manufacturer;}
    public String getLaguage() {return laguage;}
    public void setLaguage(String laguage) {this.laguage = laguage;}
    public String getArea() {return area;}
    public void setArea(String area) {this.area = area;}
    public String getRa() {return ram;}
    public void setRa(String ram) {this.ram = ram;}
    public String getRo() {return rom;}
    public void setRo(String rom) {this.rom = rom;}
    public String getIp() {return ip;}
    public void setIp(String ip) {this.ip = ip;}
    public String getNetworkEnvironment() {return networkEnvironment;}
    public String getImei() {return imei;}
    public void setImei(String imei) {this.imei = imei;}
    public String getMac() {return mac;}
    public void setMac(String mac) {this.mac = mac;}
    public String getScreenHeight() {return screenHeight;}
    public void setScreenHeight(String screenHeight) {this.screenHeight = screenHeight;}
    public String getScreenWidth() {return screenWidth;}
    public void setNetworkEnvironment(String networkEnvironment) {this.networkEnvironment = networkEnvironment;}
    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}
    public String getVersion() {return version;}
    public void setFrontCameraPixels(String frontCameraPixels) {
        this.frontCameraPixels = frontCameraPixels;
    }
    public String getRearCameraPixels() {return rearCameraPixels;}
    public void setRearCameraPixels(String rearCameraPixels) {
        this.rearCameraPixels = rearCameraPixels;
    }

}
