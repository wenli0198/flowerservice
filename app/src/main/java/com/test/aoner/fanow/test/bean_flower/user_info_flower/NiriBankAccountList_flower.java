package com.test.aoner.fanow.test.bean_flower.user_info_flower;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class NiriBankAccountList_flower {

    private static class Inner {
        private static final NiriBankAccountList_flower instance = new NiriBankAccountList_flower();
    }

    private NiriBankAccountList_flower() {}

    public static NiriBankAccountList_flower getInstance() {
        return Inner.instance;
    }

    private final ArrayList<NiriBankAccount> bankAccountList = new ArrayList<>();

    public static void parse(JSONArray jsonArray) {
        Gson gson = new Gson();
        Inner.instance.bankAccountList.clear();
        for (int i=0;i<jsonArray.length();i++){
            try {
                NiriBankAccount bankAccount = gson.fromJson(jsonArray.get(i).toString(), NiriBankAccount.class );
                Inner.instance.bankAccountList.add(bankAccount);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<NiriBankAccount> getBankAccountList() {
        if (bankAccountList==null) return new ArrayList<>();
        return bankAccountList;
    }

    public static class NiriBankAccount {

        private String bankName;
        private String cardno;
        private String cvv;
        private String expiryMonth;
        private String expiryYear;

        public String getBankName() {
            return StringUtil_flower.getSafeString(bankName);
        }

        public String getCardno() {
            return StringUtil_flower.getSafeString(cardno);
        }

        public String getCvv() {
            return StringUtil_flower.getSafeString(cvv);
        }

        public String getExpiryMonth() {
            return StringUtil_flower.getSafeString(expiryMonth);
        }

        public String getExpiryYear() {
            return StringUtil_flower.getSafeString(expiryYear);
        }
    }

}
