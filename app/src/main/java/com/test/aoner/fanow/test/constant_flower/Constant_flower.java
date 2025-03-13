package com.test.aoner.fanow.test.constant_flower;

import com.adjust.sdk.AdjustConfig;

public interface Constant_flower {

    //tag2024
    boolean DebugFlag = true;

    //tag2024
    //adjust token
    String Environment = DebugFlag ? AdjustConfig.ENVIRONMENT_SANDBOX:AdjustConfig.ENVIRONMENT_PRODUCTION;
    String AJ_TOKEN = "yl6d90s6gc8w";
    String AJ_EVENT_APPLY = "jh57ip";
    String AJ_EVENT_REGISTER = "6vv9a1";


    String PRODUCT = "FLOWERLOAN";

    String CLIENT = "ANDROID";

    String APP_VERSION = "183";

    String APP_VERSION_NAME = "18.3";


    String APPSFLYER_DEV_KEY = "2tBibZeh5NSdUdtPUVUrGL";

    //tag2024
    String NAME = "com.test.aoner.fanow.test";//com.fowler.aoner.fanow.wanfeo

    String CUSTOMER_PHONE = "";

    String Data_Action_Input = "INPUT";
    String Data_Action_Select = "SELECT";
    String Data_Action_Date_Pick = "DATE_PICK";
    String Data_Action_Gender_Pick = "GENDER_PICK";
    String Data_Action_Contact_Relation = "CONTACT_GROUP_RELATION";
    String Data_Action_Contact_Name = "CONTACT_GROUP_NAME";
    String Data_Action_Contact_Phone = "CONTACT_GROUP_PHONE";
    String Data_Action_Credit_Report = "CREDIT_REPORT";
    String Data_Action_Address_Pick = "ADDRESS_PICK";
    String Data_Action_Bank_Pick = "BANK_PICK";

    String Country_Kenya = "Kenya";
    String Country_Nigeria = "Nigeria";
    String Country_Ghana = "Ghana";
    String Country_Cote = "Côte d'Ivoire";
    String Country_Uganda = "Uganda";
    String Country_Vietnam = "Việt Nam";
    String Country_Tanzan = "Tanzania";

    String Suffix_Kenya = "ksh";
    String Suffix_Nigeria = "naira";
    String Suffix_Ghana = "gha";
    String Suffix_Cote = "fcfa";
    String Suffix_Uganda = "ugx";
    String Suffix_Vietnam = "vnd";
    String Suffix_Tanzan = "tanz";

    String Money_Unit_Kenya = "KES";
    String Money_Unit_Nigeria = "₦";
    String Money_Unit_Ghana = "GHS";
    String Money_Unit_Cote = "FCFA";
    String Money_Unit_Uganda = "UGX";
    String Money_Unit_Vietnam = "VNĐ";
    String Money_Unit_Tanzan = "TZS";

    String Mobile_Prefix_Kenya = "+254";
    String Mobile_Prefix_Nigeria = "+234";
    String Mobile_Prefix_Ghana = "+233";
    String Mobile_Prefix_Cote = "+225";
    String Mobile_Prefix_Uganda = "+256";
    String Mobile_Prefix_Vietnam = "+84";
    String Mobile_Prefix_Tanzan = "+255";

    String WordPrefix = "flowerloan";

    String Privacy_Kenya = "https://sites.google.com/view/flowerloantzs";
    String Privacy_Nigeria = "https://sites.google.com/view/flowerloantzs";
    String Privacy_Ghana = "https://sites.google.com/view/flowerloantzs";
    String Privacy_Cote = "https://sites.google.com/view/flowerloanfcfa";
    String Privacy_Uganda = "https://sites.google.com/view/flowerloanugx";
    String Privacy_Vietnam = "https://sites.google.com/view/flowerloanvnd";
    String Privacy_Tanzan = "https://sites.google.com/view/flowerloantzs";

    String Privacy_Main = Privacy_Tanzan;

    String TimeZone_Ug = "Africa/Kampala";
    String TimeZone_Ke = "Africa/Nairobi";
    String TimeZone_Ni = "Africa/Lagos";
    String TimeZone_Gh = "Africa/Accra";
    String TimeZone_Ta = "Africa/Dar_es_Salaam";
    String TimeZone_Co = "Africa/Abidjan";
    String TimeZone_Vi = "Asia/Ho_Chi_Minh";


    String TanzStepName_Personal = "Taarifa za kibinafsi";
    String TanzStepName_Work = "Taarifa za kazi";
    String TanzStepName_Contact = "Maelezo ya mawasiliano";
    String TanzStepName_Account = "Maelezo ya akaunti";
    String TanzStepName_Ocr = "Tambua habari";
    String TanzStepName_Seife = "Maelezo ya selfie";

}
