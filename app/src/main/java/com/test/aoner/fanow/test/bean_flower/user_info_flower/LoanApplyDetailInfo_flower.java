package com.test.aoner.fanow.test.bean_flower.user_info_flower;

import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.google.gson.Gson;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;

import org.json.JSONObject;

import java.util.ArrayList;

public class LoanApplyDetailInfo_flower {

    private static class Inner {
        private static LoanApplyDetailInfo_flower instance = new LoanApplyDetailInfo_flower();
    }

    private LoanApplyDetailInfo_flower() {}

    public static LoanApplyDetailInfo_flower getInstance() {
        return Inner.instance;
    }

    private String days;
    private String amount;
    private String interest;
    private String interestAndServiceFee;
    private String processingFee;
    private String repayProFee;
    private String interestRate;
    private String realAmount;
    private String repayAmount;
    private String overdueRate;
    private String bankAccount;
    private String walletMobile;
    private String fraudRemindNor;
    private String fraudRemindBold;
    private String selectAmountFlag;
    private String processingVatFee;
    private String repayProVatFee;

    private ArrayList<ApplyDetailItem_flower> detailItemList;


    public static void parse(JSONObject objJson_flower) {
        Inner.instance = new Gson().fromJson(objJson_flower.toString(), LoanApplyDetailInfo_flower.class);
    }

    public String getDays() {
        return StringUtil_flower.getSafeString(days);
    }

    public String getAmount() {
        return StringUtil_flower.getSafeString(amount);
    }

    public String getInterest() {
        return StringUtil_flower.getSafeString(interest);
    }

    public String getInterestAndServiceFee() {
        return StringUtil_flower.getSafeString(interestAndServiceFee);
    }

    public String getProcessingFee() {
        return StringUtil_flower.getSafeString(processingFee);
    }

    public String getRepayProFee() {
        return StringUtil_flower.getSafeString(repayProFee);
    }

    public String getInterestRate() {
        return StringUtil_flower.getSafeString(interestRate);
    }

    public String getRealAmount() {
        return StringUtil_flower.getSafeString(realAmount);
    }

    public String getRepayAmount() {
        return StringUtil_flower.getSafeString(repayAmount);
    }

    public String getOverdueRate() {
        return StringUtil_flower.getSafeString(overdueRate);
    }

    public String getBankAccount() {
        return StringUtil_flower.getSafeString(bankAccount);
    }

    public String getWalletMobile() {
        return StringUtil_flower.getSafeString(walletMobile);
    }

    public String getFraudRemindNor() {
        return StringUtil_flower.getSafeString(fraudRemindNor);
    }

    public String getFraudRemindBold() {
        return StringUtil_flower.getSafeString(fraudRemindBold);
    }

    public String getSelectAmountFlag() {
        return StringUtil_flower.getSafeString(selectAmountFlag);
    }

    public String getProcessingVatFee() {
        return StringUtil_flower.getSafeString(processingVatFee);
    }

    public String getRepayProVatFee() {
        return StringUtil_flower.getSafeString(repayProVatFee);
    }

    public ArrayList<ApplyDetailItem_flower> getDetailItemList() {
        if (detailItemList == null) return new ArrayList<>();
        return detailItemList;
    }

    public ArrayList<ApplyDetailItem_flower> getTestAccountDetailItems(){
        ArrayList<ApplyDetailItem_flower> result = new ArrayList<>();
        switch (StaticConfig_flower.Select_Country){
            case Constant_flower.Country_Tanzan:
                if (UserInfoHelper_flower.getInstance().isLanguageEn()){
                    result.add(new ApplyDetailItem_flower("YES","Disburse amount","200000.00TZS"));
                    result.add(new ApplyDetailItem_flower("","Loan amount","200000.00TZS"));
                    result.add(new ApplyDetailItem_flower("","Pay channel fee","0.00TZS"));
                    result.add(new ApplyDetailItem_flower("","Credit report fee","0.00TZS"));
                    result.add(new ApplyDetailItem_flower("","Search fee","0.00TZS"));
                    result.add(new ApplyDetailItem_flower("","Technology fee","0.00TZS"));
                    result.add(new ApplyDetailItem_flower("YES","Repayment amount","236000.00TZS"));
                    result.add(new ApplyDetailItem_flower("","Interest","36000.00TZS"));
                    result.add(new ApplyDetailItem_flower("","Service fee","0.00TZS"));
                    result.add(new ApplyDetailItem_flower("","Overdue penalty interest","2.00% per day"));
                }else {
                    result.add(new ApplyDetailItem_flower("YES","Kiasi cha malipo","200000.00TZS"));
                    result.add(new ApplyDetailItem_flower("","Kiasi cha mkopo","200000.00TZS"));
                    result.add(new ApplyDetailItem_flower("","Lipa ada ya kituo","0.00TZS"));
                    result.add(new ApplyDetailItem_flower("","ada ya ripoti ya mkopo","0.00TZS"));
                    result.add(new ApplyDetailItem_flower("","gharama ya utafutaji","0.00TZS"));
                    result.add(new ApplyDetailItem_flower("","ada za kiufundi","0.00TZS"));
                    result.add(new ApplyDetailItem_flower("YES","Kiwango cha majibu","236000.00TZS"));
                    result.add(new ApplyDetailItem_flower("","Hamu","36000.00TZS"));
                    result.add(new ApplyDetailItem_flower("","Ada ya huduma","0.00TZS"));
                    result.add(new ApplyDetailItem_flower("","Riba ya adhabu iliyochelewa","2.00% per day"));
                }
                break;
            case Constant_flower.Country_Cote:
                result.add(new ApplyDetailItem_flower("YES","Montant du paiement","300000.00XOF"));
                result.add(new ApplyDetailItem_flower("","Montant de prêt","300000XOF"));
                result.add(new ApplyDetailItem_flower("","Frais de service","0XOF"));
                result.add(new ApplyDetailItem_flower("YES","Montant de remboursement","354000.00XOF"));
                result.add(new ApplyDetailItem_flower("","Intérêt","54000.00XOF"));
                result.add(new ApplyDetailItem_flower("","Frais de service","0.00XOF"));
                result.add(new ApplyDetailItem_flower("","Intérêt de pénalité en retard","2.00% par jour"));
                break;
            case Constant_flower.Country_Uganda:
            default:
                result.add(new ApplyDetailItem_flower("YES","Disburse amount","300000.00UGX"));
                result.add(new ApplyDetailItem_flower("","Loan amount","300000.00UGX"));
                result.add(new ApplyDetailItem_flower("","Service fee","0.00UGX"));
                result.add(new ApplyDetailItem_flower("YES","Repayment amount","354000.00UGX"));
                result.add(new ApplyDetailItem_flower("","Interest","54000.00UGX"));
                result.add(new ApplyDetailItem_flower("","Service fee","0.00UGX"));
                result.add(new ApplyDetailItem_flower("","Overdue penalty interest","2.00% per day"));

        }
        return result;
    }


}
