package com.test.aoner.fanow.test.bean_flower;

import java.util.TimeZone;
import java.util.TreeMap;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.constant_flower.Url_flower;

import static com.test.aoner.fanow.test.constant_flower.Constant_flower.*;

public class StaticConfig_flower {

    public static String Upload_Info_Url = "";

    public static String Country_Word = "";

    public static String Select_Country = "";

    public static GlobalConfig_flower.CountryConfig Country_Config;

    public static Bitmap OperatedBitmap = null;

    public static String IP = "";

    public final static TreeMap<String,String> Country_Suffix_Map = new TreeMap<String,String>(){{
        put(Country_Cote, Suffix_Cote);
        put(Country_Ghana, Suffix_Ghana);
        put(Country_Nigeria, Suffix_Nigeria);
        put(Country_Uganda, Suffix_Uganda);
        put(Country_Kenya, Suffix_Kenya);
        put(Country_Vietnam, Suffix_Vietnam);
        put(Country_Tanzan, Suffix_Tanzan);
    }};

    public final static TreeMap<String,String> Country_Money_Unit_Map = new TreeMap<String,String>(){{
        put(Country_Cote, Money_Unit_Cote);
        put(Country_Ghana, Money_Unit_Ghana);
        put(Country_Nigeria, Money_Unit_Nigeria);
        put(Country_Uganda, Money_Unit_Uganda);
        put(Country_Kenya, Money_Unit_Kenya);
        put(Country_Vietnam, Money_Unit_Vietnam);
        put(Country_Tanzan, Money_Unit_Tanzan);
    }};

    public final static TreeMap<String,String> Country_Mobile_Prefix_Map = new TreeMap<String,String>(){{
        put(Country_Cote, Mobile_Prefix_Cote);
        put(Country_Ghana, Mobile_Prefix_Ghana);
        put(Country_Nigeria, Mobile_Prefix_Nigeria);
        put(Country_Uganda, Mobile_Prefix_Uganda);
        put(Country_Kenya, Mobile_Prefix_Kenya);
        put(Country_Vietnam, Mobile_Prefix_Vietnam);
        put(Country_Tanzan,Mobile_Prefix_Tanzan);
    }};

    public static String getMobilePrefix(){
        if (TextUtils.isEmpty(Select_Country)) return "";
        return Country_Mobile_Prefix_Map.get(Select_Country);
    }

    public static String getMoneyUnit(){
        if (TextUtils.isEmpty(Select_Country)) return "";
        return Country_Money_Unit_Map.get(Select_Country);
    }

    public static String getCountrySuffix(){
        if (TextUtils.isEmpty(Select_Country)) return "";
        return Country_Suffix_Map.get(Select_Country);
    }

    public static String getCountryUrlPrefix(){
        return "/" + Country_Word;
    }

    public static boolean hasCountryWord(){
        return !TextUtils.isEmpty(Country_Word);
    }

    public static String getWholeBaseUrl(){
        return getBaseUrl()+getCountryUrlPrefix();
    }

    public static String getBaseUrl(){
        if (TimeZone.getDefault().getID().equalsIgnoreCase(TimeZone_Ta)) {
            if (!TextUtils.isEmpty(IP)) return IP;
            else return Url_flower.Url_Asia;
        }else return Url_flower.Url_Africa;
    }

    public static String getFrontByTimezone(){
        if (!TextUtils.isEmpty(Country_Word)) return "/"+Country_Word;
        String tz = TimeZone.getDefault().getID();
        switch (tz){
            case TimeZone_Co:
                return "/flowerloanfcfa";
            case TimeZone_Ug:
                return "/flowerloanugx";
            case TimeZone_Vi:
                return "/flowerloanvnd";
            case TimeZone_Ta:
                return "/flowerloantanz";
        }
        return "/flowerloantanz";
    }

}
