package com.test.aoner.fanow.test.bean_flower.config_info_flower;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.ThreadUtil_flower;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AppShowInfo_flower {

    private static class Inner {
        private static AppShowInfo_flower instance = new AppShowInfo_flower();
    }

    private AppShowInfo_flower() {}

    public static AppShowInfo_flower getInstance() {
        return Inner.instance;
    }

    private Customize customize;
    private ArrayList<WalletChannel> walletChannels;

    public static void parse(JSONObject objJson) {
        //if (objJson==null) return;
        Gson gson = new Gson();
        Inner.instance = gson.fromJson(objJson.toString(), AppShowInfo_flower.class);
        if (Inner.instance.customize==null) Inner.instance.customize = gson.fromJson(objJson.optString("customize"),Customize.class);
    }

    public String getAFKey() {
        if (customize==null) return "";
        return customize.getAfKey();
    }

    public String[] getMobile() {
        if (customize == null) return new String[0];
        return customize.getMobile();
    }

    public String[] getEmail() {
        if (customize == null) return new String[0];
        return customize.getEmail();
    }

    public String getTransferDelayImportant() {
        if (customize==null) return "";
        return StringUtil_flower.getSafeString(customize.getTransferDelayImportant());
    }

    public String getRejectPeriod() {
        if (customize==null) return "";
        return customize.getRejectPeriod();
    }

    public boolean showSysReview(){
        if (customize==null) return false;
        return customize.showSysReview();
    }

    public boolean reviewNoticeFlag(){
        if (customize == null) return false;
        return customize.reviewNoticeFlag();
    }

    public String getReviewNoticeTitle() {
        if (customize == null) return "";
        return customize.getReviewNoticeTitle();
    }

    public String getReviewNoticeContent() {
        if (customize == null) return "";
        return customize.getReviewNoticeContent();
    }

    public boolean loaningNoticeFlag(){
        if (customize == null) return false;
        return customize.loaningNoticeFlag();
    }

    public String getLoaningNoticeTitle() {
        if (customize == null) return "";
        return customize.getLoaningNoticeTitle();
    }

    public String getLoaningNoticeContent() {
        if (customize == null) return "";
        return customize.getLoaningNoticeContent();
    }

    public static class Customize{

        private String homeAmount;
        private String showSysReview;
        private String noPointRecord;
        private String companyAddress;
        private String mobile;
        private String company;
        private String afKey;
        private String email;
        private String repayType;
        private String rejectPeriod;
        private JsonArray walletChannels;
        private String transferDelayImportant;

        //Notice
        private String reviewNoticeFlag;
        private String reviewNoticeTitle;
        private String reviewNoticeContent;
        private String loaningNoticeFlag;
        private String loaningNoticeTitle;
        private String loaningNoticeContent;

        private Customize(){}

        public boolean reviewNoticeFlag(){
            return "YES".equalsIgnoreCase(reviewNoticeFlag) && !TextUtils.isEmpty(reviewNoticeContent);
        }

        public String getReviewNoticeTitle() {
            return StringUtil_flower.getSafeString(reviewNoticeTitle);
        }

        public String getReviewNoticeContent() {
            return StringUtil_flower.getSafeString(reviewNoticeContent);
        }

        public boolean loaningNoticeFlag(){
            return "YES".equalsIgnoreCase(loaningNoticeFlag) && !TextUtils.isEmpty(loaningNoticeContent);
        }

        public String getLoaningNoticeTitle() {
            return StringUtil_flower.getSafeString(loaningNoticeTitle);
        }

        public String getLoaningNoticeContent() {
            return StringUtil_flower.getSafeString(loaningNoticeContent);
        }

        public String getHomeAmount() {
            return StringUtil_flower.getSafeString(homeAmount);
        }

        public boolean showSysReview(){
            return StringUtil_flower.getSafeString(showSysReview).equalsIgnoreCase("YES");
        }

        public String getNoPointRecord() {
            return StringUtil_flower.getSafeString(noPointRecord);
        }

        public String getCompanyAddress() {
            return StringUtil_flower.getSafeString(companyAddress);
        }

        public String[] getMobile() {
            return StringUtil_flower.getSafeString(mobile).split(",");
        }

        public String getCompany() {
            return StringUtil_flower.getSafeString(company);
        }

        public String getAfKey() {
            return StringUtil_flower.getSafeString(afKey);
        }

        public String[] getEmail() {
            return StringUtil_flower.getSafeString(email).split(",");
        }

        public String getRepayType() {
            return StringUtil_flower.getSafeString(repayType);
        }

        public String getRejectPeriod() {
            return StringUtil_flower.getSafeString(rejectPeriod);
        }

        public JsonArray getWalletChannels() {
            return walletChannels;
        }

        public String getTransferDelayImportant() {
            return StringUtil_flower.getSafeString(transferDelayImportant);
        }

    }

    private void initWalletChannels(){
        walletChannels = new ArrayList<>();
        JsonArray jsonArray = customize.getWalletChannels();
        Gson gson = new Gson();
        if (jsonArray!=null){
            for (int i=0;i<jsonArray.size();i++){
                WalletChannel walletChannel = gson.fromJson(jsonArray.get(i).toString(),WalletChannel.class);
                walletChannels.add(walletChannel);
            }
        }

        ThreadUtil_flower.getInstance().runOnChildThread(() -> {
            for (WalletChannel walletChannel:walletChannels){
                try {
                    if (!TextUtils.isEmpty(walletChannel.getLogo())){
                        URL url = new URL(walletChannel.getLogo());
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setConnectTimeout(5000);
                        connection.setRequestMethod("GET");
                        if (connection.getResponseCode()==200){
                            InputStream inputStream = connection.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            walletChannel.setBitmap(bitmap);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ArrayList<WalletChannel> getWalletChannels(){
        if (walletChannels==null){
            initWalletChannels();
        }
        return walletChannels;
    }

    public static class WalletChannel{

        private String desc;
        private String value;
        private String logo;

        private Bitmap bitmap;

        public String getDesc() {
            return StringUtil_flower.getSafeString(desc);
        }

        public String getValue() {
            return StringUtil_flower.getSafeString(value);
        }

        public String getLogo() {
            return StringUtil_flower.getSafeString(logo);
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

    }

}
