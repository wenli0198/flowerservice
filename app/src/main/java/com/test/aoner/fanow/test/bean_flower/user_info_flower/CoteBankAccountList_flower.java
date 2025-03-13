package com.test.aoner.fanow.test.bean_flower.user_info_flower;

import android.graphics.Bitmap;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class CoteBankAccountList_flower {

    private static class Inner {
        private static final CoteBankAccountList_flower instance = new CoteBankAccountList_flower();
    }

    private CoteBankAccountList_flower() {}

    public static CoteBankAccountList_flower getInstance() {
        return Inner.instance;
    }

    private final ArrayList<CoteBankAccount> bankAccountList = new ArrayList<>();

    public static void parse(JSONArray jsonArray) {
        Gson gson = new Gson();
        Inner.instance.bankAccountList.clear();
        for (int i=0;i<jsonArray.length();i++){
            try {
                CoteBankAccount bankAccount = gson.fromJson(jsonArray.get(i).toString(), CoteBankAccount.class );
                Inner.instance.bankAccountList.add(bankAccount);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        for (BankAccount bankAccount: instance.bankAccountList){
//            Log.w("---bankAccount---", bankAccount.toString() );
//        }
    }

    public ArrayList<CoteBankAccount> getBankAccountList() {
        return bankAccountList;
    }

    public static class CoteBankAccount {

        private String id;
        private String walletMobile;
        private String walletLogo;
        private String walletDesc;

        private Bitmap bitmap;

        public String getId() {
            return StringUtil_flower.getSafeString(id);
        }

        public String getWalletMobile() {
            return StringUtil_flower.getSafeString(walletMobile);
        }

        public String getWalletLogo() {
            return StringUtil_flower.getSafeString(walletLogo);
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public String getWalletDesc() {
            return StringUtil_flower.getSafeString(walletDesc);
        }
    }

}
