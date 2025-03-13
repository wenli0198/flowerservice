package com.test.aoner.fanow.test.analytics.flowerhttp;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;

import org.json.JSONObject;

import okhttp3.logging.HttpLoggingInterceptor;


public class HttpLogger implements HttpLoggingInterceptor.Logger {


    @Override
    public void log(@NonNull String s) {

        if (!Constant_flower.DebugFlag) return;

        try {
            if (TextUtils.isEmpty(s)){
                Log.e("Http_Log","String is Empty");
                return;
            }

            JSONObject json = new JSONObject(s);
            JsonElement jsonObject = JsonParser.parseString(json.toString());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Log.i("log",gson.toJson(jsonObject));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String formatJson(String jsonStr) {
        int level = 0;
        StringBuilder jsonForMatStr = new StringBuilder();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c).append("\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c).append("\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        return jsonForMatStr.toString();
    }

    public static String getLevelStr(int level) {
        StringBuilder levelStr = new StringBuilder();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

}
