package com.test.aoner.fanow.test.util_flower;

import static com.test.aoner.fanow.test.constant_flower.Constant_flower.TanzStepName_Account;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.TanzStepName_Contact;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.TanzStepName_Ocr;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.TanzStepName_Personal;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.TanzStepName_Seife;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.TanzStepName_Work;

import android.content.Context;
import android.text.TextUtils;

import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.util_flower.address_flower.AddressUtil_Niri_flower;
import com.test.aoner.fanow.test.util_flower.address_flower.AddressUtil_Tanz_flower;
import com.test.aoner.fanow.test.util_flower.address_flower.AddressUtil_Viet_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StringUtil_flower {

    public static String getSafeString(String str){
        if (TextUtils.isEmpty(str)) return "";
        return str.trim();
    }

    public static String getUrlSuffix(String url){
        if (TextUtils.isEmpty(url)) return "";
        String[] strs = url.split("/");
        if (strs.length<5) return "/"+strs[strs.length-1];
        StringBuilder result = new StringBuilder();
        for (int i = 4;i<strs.length;i++){
            result.append("/").append(strs[i]);
        }
        return result.toString();
    }

    public static String amountOfMoneyFormat(String numStr){
        StringBuilder result=new StringBuilder();
        if (numStr.indexOf('.')!=-1){
            String[] strings = numStr.split("\\.");
            result.append(".").append(strings[1]);
            numStr = strings[0];
        }
        for (int i=numStr.length()-1,count=0;i>=0;i--){
            char c = numStr.charAt(i);
            if (c>='0'&&c<='9'){
                result.insert(0,c);
                count++;
            }
            if (count%3==0&&i>0){
                result.insert(0,',');
            }
        }
        return result.toString();
    }

    public static String bankAccountHideFront(String bankAccount){
        if (TextUtils.isEmpty(bankAccount)) return "";
        StringBuilder result = new StringBuilder();
        int length = bankAccount.length();
        if (length<=4) return bankAccount;
        for (int i = 1;i<=4;i++){
            result.insert(0,bankAccount.charAt(length-i));
        }
        result.insert(0,"*****");
        return result.toString();
    }

    public static String bankAccountHideMiddle(String bankAccount){
        if (TextUtils.isEmpty(bankAccount)) return "";
        StringBuilder result = new StringBuilder();
        int length = bankAccount.length();
        for (int i = 1;i<=length;i++){
            if (i<=4||length-i<3) result.insert(0,bankAccount.charAt(length-i));
            else result.insert(0,"*");
        }
        return result.toString();
    }

    public static int safeParseInt(String str){
        str = str.trim();
        if (TextUtils.isEmpty(str)) return 0;
        StringBuilder safeStr = new StringBuilder();
        for (int i = 0;i<str.length();i++){
            char c = str.charAt(i);
            if (c>='0'&&c<='9') safeStr.append(c);
            else if (c == '.') return Integer.parseInt(safeStr.toString());
        }
        return Integer.parseInt(safeStr.toString());
    }

    public static double safeParseDouble(String str){
        try {
            str = str.trim();
            if (TextUtils.isEmpty(str)) return 0;
            return Double.parseDouble(str);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static String parseLimit(String limit){
        String[] strings = limit.split("-");
        return strings[strings.length-1];
    }

    public static void parseAddress(Context context){
        ThreadUtil_flower.getInstance().runOnChildThread(() -> {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open("address_list_niri.json")));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                AddressUtil_Niri_flower.getInstance().parse(jsonArray);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        ThreadUtil_flower.getInstance().runOnChildThread(() -> {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open("address_list_viet.json")));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                AddressUtil_Viet_flower.getInstance().parse(jsonArray);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        ThreadUtil_flower.getInstance().runOnChildThread(() -> {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open("address_list_tanzan.json")));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                AddressUtil_Tanz_flower.getInstance().parse(jsonArray);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }


    public static String clearFrontZero(String str){
        if (TextUtils.isEmpty(str)) return "0";
        else if (!str.startsWith("0")) return str;
        int startIndex = 0;
        int endIndex = str.length();
        while (startIndex<endIndex&&str.charAt(startIndex)=='0') startIndex++;
        return str.substring(startIndex,endIndex);
    }

    public static String processTitleFormat(String processName){
        if (TextUtils.isEmpty(processName)) return "";

        if (StaticConfig_flower.Select_Country.equalsIgnoreCase(Constant_flower.Country_Tanzan) && !UserInfoHelper_flower.getInstance().isLanguageEn()){
            switch (processName){
                case "personal_info": return TanzStepName_Personal;
                case "work_info": return TanzStepName_Work;
                case "contact_info": return TanzStepName_Contact;
                case "account_info": return TanzStepName_Account;
                case "sefie_info": return TanzStepName_Seife;
                case "ocr_info": return TanzStepName_Ocr;
            }
        }

        processName = processName.replaceAll("_"," ");
        processName = processName.substring(0,1).toUpperCase() + processName.substring(1);

        return processName;
    }

}
