package com.test.aoner.fanow.test.bean_flower.config_info_flower;

import static com.test.aoner.fanow.test.bean_flower.StaticConfig_flower.OperatedBitmap;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.Suffix_Cote;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.Suffix_Ghana;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.Suffix_Kenya;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.Suffix_Nigeria;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.Suffix_Tanzan;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.Suffix_Uganda;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.Suffix_Vietnam;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.TimeZone_Co;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.TimeZone_Gh;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.TimeZone_Ke;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.TimeZone_Ni;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.TimeZone_Ta;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.TimeZone_Ug;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.TimeZone_Vi;
import static com.test.aoner.fanow.test.constant_flower.Constant_flower.WordPrefix;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.constant_flower.Url_flower;
import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.test.aoner.fanow.test.util_flower.helper_flower.UserInfoHelper_flower;
import com.google.gson.Gson;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.TreeMap;

public class GlobalConfig_flower {

    private static class Inner {
        private static GlobalConfig_flower instance = new GlobalConfig_flower();
    }

    private GlobalConfig_flower() {
    }

    public static GlobalConfig_flower getInstance() {
        return Inner.instance;
    }

    public static void parse(JSONObject jsonObject) {
        if (jsonObject == null) return;
        Inner.instance.countryConfigTreeMap.clear();
        Gson gson = new Gson();
        Inner.instance = gson.fromJson(jsonObject.toString(), GlobalConfig_flower.class);

        if (Inner.instance.woodenloancommon == null)
            Inner.instance.woodenloancommon = gson.fromJson(jsonObject.optString(WordPrefix + "common"), Common.class);

        if (Inner.instance.country == null)
            Inner.instance.country = new ArrayList<>();
        for (String country : Inner.instance.country) {
            Inner.instance.countryConfigTreeMap.put(country, gson.fromJson(jsonObject.optString(country), CountryConfig.class));
        }

    }

    private ArrayList<String> country;
    private Common woodenloancommon;
    private final TreeMap<String, CountryConfig> countryConfigTreeMap = new TreeMap<>();


    public ArrayList<String> getCountryFullPaths() {
        ArrayList<String> list = new ArrayList<>();
        for (String country : getCountries()) {
            if (country.endsWith("tanz")) {
                list.add(Url_flower.Url_Asia + "/" + country);
            } else {
                list.add(Url_flower.Url_Africa + "/" + country);
            }
        }
        return list;
    }

    public ArrayList<String> getCountries() {
        if (country == null) return new ArrayList<>();
        return country;
    }

    public TreeMap<String, CountryConfig> getCountryConfigTreeMap() {
        return countryConfigTreeMap;
    }

    public String[] getPermissionArr() {
        if (woodenloancommon == null) return new String[0];
        return woodenloancommon.getPermissionArr();
    }

    public String[] getHomeArr() {
        if (woodenloancommon == null) return new String[0];
        return woodenloancommon.getHomeArr();
    }

    public boolean isTestAccount(){
        CountryConfig countryConfig = countryConfigTreeMap.get(StaticConfig_flower.Country_Word);
        if (countryConfig == null) return false;
        String tk = UserInfoHelper_flower.getInstance().getToken();
        return tk.equalsIgnoreCase(countryConfig.getAccTk());
    }

    public String isTestAccount(String mobile) {
        CountryConfig countryConfig = countryConfigTreeMap.get(StaticConfig_flower.Country_Word);
        if (countryConfig == null) return "";
        if (mobile.equalsIgnoreCase(countryConfig.getAccM())) {
            return countryConfig.getAccTk();
        }
        return "";
    }

    public boolean canDeleteData(){
        CountryConfig countryConfig = countryConfigTreeMap.get(StaticConfig_flower.Country_Word);
        if (countryConfig == null) return false;
        return countryConfig.canDeleteData();
    }



    public Boolean needRequestUsageStatsPermission() {
        if (woodenloancommon == null) return false;
        return woodenloancommon.needRequestUsageStatsPermission();
    }

    public int getUsageStatsDays() {
        if (woodenloancommon == null) return 7;
        return woodenloancommon.getUsageStatsDays();
    }

    public String getAfKey() {
        if (woodenloancommon == null) return Constant_flower.APPSFLYER_DEV_KEY;
        return woodenloancommon.getAfKey();
    }

    private void setCountryConfig(){
        if (StaticConfig_flower.Country_Config == null){
            switch (TimeZone.getDefault().getID()){
                case TimeZone_Ta:
                    StaticConfig_flower.Country_Config = GlobalConfig_flower.getInstance().getCountryConfigTreeMap().get(WordPrefix+Suffix_Tanzan);
                    break;
                case TimeZone_Ug:
                    StaticConfig_flower.Country_Config = GlobalConfig_flower.getInstance().getCountryConfigTreeMap().get(WordPrefix+Suffix_Uganda);
                    break;
                case TimeZone_Ke:
                    StaticConfig_flower.Country_Config = GlobalConfig_flower.getInstance().getCountryConfigTreeMap().get(WordPrefix+Suffix_Kenya);
                    break;
                case TimeZone_Ni:
                    StaticConfig_flower.Country_Config = GlobalConfig_flower.getInstance().getCountryConfigTreeMap().get(WordPrefix+Suffix_Nigeria);
                    break;
                case TimeZone_Gh:
                    StaticConfig_flower.Country_Config = GlobalConfig_flower.getInstance().getCountryConfigTreeMap().get(WordPrefix+Suffix_Ghana);
                    break;
                case TimeZone_Co:
                    StaticConfig_flower.Country_Config = GlobalConfig_flower.getInstance().getCountryConfigTreeMap().get(WordPrefix+Suffix_Cote);
                    break;
                case TimeZone_Vi:
                    StaticConfig_flower.Country_Config = GlobalConfig_flower.getInstance().getCountryConfigTreeMap().get(WordPrefix+Suffix_Vietnam);
                    break;
            }
        }
    }

    public boolean isShowOperated(){
        return !TextUtils.isEmpty(getOperatedCompanyName()) && !TextUtils.isEmpty(getOperatedCompanyLogo());
    }

    public String getTermOfUsePath() {
        setCountryConfig();
        if (StaticConfig_flower.Country_Config == null) return "";
        else return StaticConfig_flower.Country_Config.getTermOfUsePath();
    }

    public String getOperatedCompanyName() {
        setCountryConfig();
        if (StaticConfig_flower.Country_Config == null) return "";
        else return StaticConfig_flower.Country_Config.getOperatedCompanyName();
    }

    public String getOperatedCompanyLogo() {
        setCountryConfig();
        if (StaticConfig_flower.Country_Config == null) return "";
        else return StaticConfig_flower.Country_Config.getOperatedCompanyLogo();
    }

    public void handleOperatedView(Activity activity, View operatedView, ImageView logoIv, TextView companyNameTv){
        if (!GlobalConfig_flower.getInstance().isShowOperated()) return;
        operatedView.setVisibility(View.VISIBLE);
        GlobalConfig_flower.getInstance().showOperatedLogo(activity,logoIv);
        companyNameTv.setText(GlobalConfig_flower.getInstance().getOperatedCompanyName());
    }

    private void showOperatedLogo(Activity activity, ImageView logoIv){

        if (OperatedBitmap!=null) {
            logoIv.setImageBitmap(OperatedBitmap);
            return;
        }

        String logoPath = getOperatedCompanyLogo();
        if (TextUtils.isEmpty(logoPath)) return;

        try {
            new Thread(() -> {
                try {
                    URL url = new URL(logoPath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(4995);
                    connection.setRequestMethod("GET");
                    if (connection.getResponseCode()==200){
                        InputStream inputStream = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        activity.runOnUiThread(() -> {
                            if (logoIv!=null) logoIv.setImageBitmap(bitmap);
                        });
                    }
                }catch (Exception e){
                    if (Constant_flower.DebugFlag) e.printStackTrace();
                }
            }).start();

        }catch (Exception e){
            if (Constant_flower.DebugFlag) e.printStackTrace();
        }

    }


    public static class CountryConfig {

        private String accM;
        private String accTk;
        private String customer;

        //Use Term
        private String termOfUsePath;

        //Operated Company
        private String operatedCompanyLogo;
        private String operatedCompanyName;

        //User Data Delete
        private String canDeleteData;

        private CountryConfig() {
        }

        public String getAccM() {
            return StringUtil_flower.getSafeString(accM);
        }

        public String getAccTk() {
            return StringUtil_flower.getSafeString(accTk);
        }

        public String getCustomer() {
            return StringUtil_flower.getSafeString(customer);
        }

        public String getTermOfUsePath() {
            return StringUtil_flower.getSafeString(termOfUsePath);
        }

        public boolean canDeleteData(){
            if (TextUtils.isEmpty(canDeleteData)) return false;
            return canDeleteData.equalsIgnoreCase("YES");
        }

        public String getOperatedCompanyName() {
            return StringUtil_flower.getSafeString(operatedCompanyName);
        }

        public String getOperatedCompanyLogo() {
            return StringUtil_flower.getSafeString(operatedCompanyLogo);
        }

        @Override
        public String toString() {
            return "CountryConfig{" +
                    "accM='" + accM + '\'' +
                    ", accTk='" + accTk + '\'' +
                    ", customer='" + customer + '\'' +
                    '}';
        }
    }

    public static class Common {

        private ArrayList<String> permissionGp;
        private ArrayList<String> permission;
        private ArrayList<String> home;
        private String accVersion;

        private String usageStatsPermission;

        private int usageStatsDays = 7;

        private String afKey;

        private Common() {
        }

        public ArrayList<String> getPermissionGp() {
            if (permissionGp == null) return new ArrayList<>();
            return permissionGp;
        }

        public ArrayList<String> getPermission() {
            if (permission == null) return new ArrayList<>();
            return permission;
        }

        public String[] getPermissionArr() {
            if (permission == null) return new String[0];
            String[] result = new String[permission.size()];
            for (int i = 0; i < permission.size(); i++) {
                result[i] = permission.get(i);
            }
            return result;
        }

        public ArrayList<String> getHome() {
            if (home == null) return new ArrayList<>();
            return home;
        }

        public String[] getHomeArr() {
            if (home == null) return new String[0];

            String[] result = new String[home.size()];
            for (int i = 0; i < home.size(); i++) {
                result[i] = home.get(0);
            }
            return result;
        }

        public String getAccVersion() {
            return StringUtil_flower.getSafeString(accVersion);
        }


        public boolean needRequestUsageStatsPermission() {
            if (TextUtils.isEmpty(usageStatsPermission)) {
                return false;
            } else {
                return usageStatsPermission.equalsIgnoreCase("YES");
            }
        }


        public int getUsageStatsDays() {
            return usageStatsDays;
        }

        public String getAfKey() {
            if (TextUtils.isEmpty(afKey)) {
                afKey = Constant_flower.APPSFLYER_DEV_KEY;
            }
            return afKey;
        }

        public void setAfKey(String afKey) {
            this.afKey = afKey;
        }
    }
}
