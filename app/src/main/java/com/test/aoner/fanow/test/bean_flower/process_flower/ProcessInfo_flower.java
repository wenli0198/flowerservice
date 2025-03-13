package com.test.aoner.fanow.test.bean_flower.process_flower;

import android.text.TextUtils;
import android.util.Log;

import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.constant_flower.EnumApplyStatus_flower;
import com.test.aoner.fanow.test.constant_flower.EnumOrderStatus_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProcessInfo_flower {

    private static class Inner {
        private static ProcessInfo_flower instance = new ProcessInfo_flower();
    }

    private ProcessInfo_flower() {}

    public static ProcessInfo_flower getInstance() {
        return Inner.instance;
    }

    public static void parse(String jsonStr) {
        Gson gson = new Gson();
        Inner.instance = gson.fromJson(jsonStr, ProcessInfo_flower.class);
        try {
            Inner.instance.srcJSON = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (Inner.instance.isOrderPendingRepayment()) {
            Inner.instance.setLoanState(LoanState.Order_Pending_Repayment);
        } else if (Inner.instance.isOrderOverdueRepayment()) {
            Inner.instance.setLoanState(LoanState.Order_Overdue_Repayment);
        } else if (Inner.instance.isOrderLoaning()) {
            Inner.instance.setLoanState(LoanState.Order_Loaning);
        } else if (Inner.instance.isLoanRejcted()) {
            Inner.instance.setLoanState(LoanState.Loan_Rejected);
        } else if (Inner.instance.isLoanReviewing()) {
            Inner.instance.setLoanState(LoanState.Loan_Reviewing);
        } else {
            Inner.instance.setLoanState(LoanState.No_Order);
        }

        if (Constant_flower.DebugFlag)
            Log.w("---extStep---", String.valueOf(ProcessInfo_flower.getInstance().getExtStep().size()));
    }

    private String product;
    private ArrayList<String> allStepTitles;
    private ArrayList<String> allSteps;
    private ArrayList<String> steps;
    private LoanState loanState;
    private String idCard;
    private String idCardImg;
    private String birthDate;
    private String applyStatus;
    private String orderStatus;
    private String deniedBetweenDay;
    private String userType;
    private JSONObject srcJSON;

    private String recipientName;

    private ArrayList<String> extStep;
    private String ocr_info;

    public String getProduct() {
        return StringUtil_flower.getSafeString(product);
    }

    public int getStepsSize(){
        if (steps==null) return 0;
        return steps.size();
    }

    public String getIdCard() {
        return StringUtil_flower.getSafeString(idCard);
    }

    public String getIdCardImg() {
        return StringUtil_flower.getSafeString(idCardImg);
    }

    public String getStepName(int index){
        if (steps==null||index<0||index>=steps.size()) return "";
        return steps.get(index);
    }

    public ProcessStep_flower getStep(int index){
        if (steps==null||index<0||index>=steps.size()||srcJSON==null) return null;
        String stepStr = srcJSON.optString(steps.get(index));

        return new Gson().fromJson(stepStr, ProcessStep_flower.class);
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setLoanState(LoanState loanState) {
        this.loanState = loanState;
    }

    public String getOrderStatus() {
        return StringUtil_flower.getSafeString(orderStatus);
    }

    public String getApplyStatus() {
        return StringUtil_flower.getSafeString(applyStatus);
    }

    public String getDeniedBetweenDay() {
        if (TextUtils.isEmpty(deniedBetweenDay)) return "30";
        return StringUtil_flower.getSafeString(deniedBetweenDay);
    }

    public ArrayList<String> getAllSteps() {
        if (allSteps==null) return new ArrayList<>();
        return allSteps;
    }

    public String getRecipientName() {
        return StringUtil_flower.getSafeString(recipientName);
    }

    public boolean isNewUser(){
        return !StringUtil_flower.getSafeString(userType).equalsIgnoreCase("OLD");
    }

    public ArrayList<String> getAllStepTitles() {
        return allStepTitles;
    }

    public String getStepTitle(){
        if (allStepTitles==null||allStepTitles.size()<allSteps.size()) return "";
        String stepName = getStepName(0);
        int i = allSteps.indexOf(stepName);
        if (i<0||i>=allStepTitles.size()) return "";
        return allStepTitles.get(i);
    }

    public ArrayList<String> getExtStep() {
        if (extStep==null) return new ArrayList<>();
        return extStep;
    }

    public boolean ocrInfoIsSDK() {
        return !TextUtils.isEmpty(ocr_info) && ocr_info.equalsIgnoreCase("SDK");
    }

    public boolean isOrderLoaning() {
        return (getOrderStatus().equalsIgnoreCase(EnumOrderStatus_flower.CREATED) ||
                getOrderStatus().equalsIgnoreCase(EnumOrderStatus_flower.LENDING));
    }

    public boolean isOrderPendingRepayment() {
        return getOrderStatus().equalsIgnoreCase(EnumOrderStatus_flower.LOANED);
    }

    public boolean isOrderOverdueRepayment() {
        return (getOrderStatus().equalsIgnoreCase(EnumOrderStatus_flower.OVERDUE) ||
                getOrderStatus().equalsIgnoreCase(EnumOrderStatus_flower.BAD_DEBT));
    }

    public boolean isLoanRejcted() {
        return (getOrderStatus().equalsIgnoreCase(EnumOrderStatus_flower.DENIED) ||
                getApplyStatus().equalsIgnoreCase(EnumApplyStatus_flower.SYS_DENIED) ||
                getApplyStatus().equalsIgnoreCase(EnumApplyStatus_flower.MANU_DENIED) ||
                getApplyStatus().equalsIgnoreCase(EnumApplyStatus_flower.DS_DENIED));
    }

    public boolean isLoanReviewing() {
        return (getApplyStatus().equalsIgnoreCase(EnumApplyStatus_flower.CREATED) ||
                getApplyStatus().equalsIgnoreCase(EnumApplyStatus_flower.SYS_CHECK) ||
                getApplyStatus().equalsIgnoreCase(EnumApplyStatus_flower.SYS_APROVAL) ||
                getApplyStatus().equalsIgnoreCase(EnumApplyStatus_flower.MANU_APROVAL) ||
                getApplyStatus().equalsIgnoreCase(EnumApplyStatus_flower.DS_APROVAL));
    }

    public enum LoanState {
        ///////////////////
        No_Order,
        Loan_Rejected,
        Loan_Reviewing,
        Order_Loaning,
        Order_Pending_Repayment,
        Order_Overdue_Repayment,
    }
}
