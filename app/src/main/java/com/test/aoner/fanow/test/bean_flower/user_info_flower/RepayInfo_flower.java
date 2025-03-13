package com.test.aoner.fanow.test.bean_flower.user_info_flower;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.google.gson.Gson;

import org.json.JSONObject;

public class RepayInfo_flower {

    private static class Inner {
        private static RepayInfo_flower instance = new RepayInfo_flower();
    }

    private RepayInfo_flower(){}

    public static RepayInfo_flower getInstance(){
        return Inner.instance;
    }

    private String amount;
    private String interest;
    private String repayDate;
    private String overdueDay;
    private String overdueAmount;
    private String repayAmount;
    private String partRepayFlag;
    private String days;
    private String tenure;
    private String processingFee;
    private String repayProFee;
    private String remainDay;
    private String serviceFee;
    private String bankCardNo;
    private String expiryYear;
    private String expiryMonth;
    private String cvv;
    private String virtualBankAccount;
    private String virtualBankName;
    
    private String serviceVatFee;
    private String deferredFlag;
    private DeferredRepayInfo deferredRepay;

    public static void parse(JSONObject objJson){
        Inner.instance = new Gson().fromJson(objJson.toString(), RepayInfo_flower.class);
    }

    public String getAmount() {
        return StringUtil_flower.getSafeString(amount);
    }

    public String getInterest() {
        return StringUtil_flower.getSafeString(interest);
    }

    public String getRepayDate() {
        return StringUtil_flower.getSafeString(repayDate);
    }

    public String getOverdueDay() {
        return StringUtil_flower.getSafeString(overdueDay);
    }

    public boolean isOverdue(){
        return StringUtil_flower.safeParseInt(overdueDay)>0;
    }

    public String getServiceFee() {
        return StringUtil_flower.getSafeString(serviceFee);
    }

    public String getOverdueAmount() {
        return StringUtil_flower.getSafeString(overdueAmount);
    }

    public String getRepayAmount() {
        return StringUtil_flower.getSafeString(repayAmount);
    }

    public String getPartRepayFlag() {
        return StringUtil_flower.getSafeString(partRepayFlag);
    }

    public String getDays() {
        return StringUtil_flower.getSafeString(days);
    }

    public String getTenure() {
        return StringUtil_flower.getSafeString(tenure);
    }

    public String getProcessingFee() {
        return StringUtil_flower.getSafeString(processingFee);
    }

    public String getRepayProFee() {
        return StringUtil_flower.getSafeString(repayProFee);
    }

    public String getRemainDay() {
        return StringUtil_flower.getSafeString(remainDay);
    }

    public String getBankCardNo() {
        return StringUtil_flower.getSafeString(bankCardNo);
    }

    public String getExpiryYear() {
        return StringUtil_flower.getSafeString(expiryYear);
    }

    public String getExpiryMonth() {
        return StringUtil_flower.getSafeString(expiryMonth);
    }

    public String getCvv() {
        return StringUtil_flower.getSafeString(cvv);
    }

    public String getVirtualBankAccount() {
        return StringUtil_flower.getSafeString(virtualBankAccount);
    }

    public String getVirtualBankName() {
        return StringUtil_flower.getSafeString(virtualBankName);
    }


    public String getServiceVatFee() {
        return StringUtil_flower.getSafeString(serviceVatFee);
    }

    public boolean getDeferredFlag() {
        return StringUtil_flower.getSafeString(deferredFlag).equalsIgnoreCase("YES");
    }

    public String getDeferredCurrentRepayAmount(){
        if (deferredRepay == null) return "";
        return StringUtil_flower.getSafeString(deferredRepay.currentRepayAmount);
    }

    public String getDeferredDays(){
        if (deferredRepay == null) return "";
        return StringUtil_flower.getSafeString(deferredRepay.days);
    }

    public String getDeferredCurrentRepayTime(){
        if (deferredRepay == null) return "";
        return StringUtil_flower.getSafeString(deferredRepay.currentRepayTime);
    }

    public String getDeferredRepayTime(){
        if (deferredRepay == null) return "";
        return StringUtil_flower.getSafeString(deferredRepay.deferredRepayTime);
    }

    public String getDeferredRepayAmount(){
        if (deferredRepay == null) return "";
        return StringUtil_flower.getSafeString(deferredRepay.deferredRepayAmount);
    }

    private static class DeferredRepayInfo{

        private String currentRepayAmount;
        private String days;
        private String currentRepayTime;
        private String deferredRepayTime;
        private String deferredRepayAmount;

    }
}
