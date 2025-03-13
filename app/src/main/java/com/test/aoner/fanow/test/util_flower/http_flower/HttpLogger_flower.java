package com.test.aoner.fanow.test.util_flower.http_flower;

import android.util.Log;

import androidx.annotation.NonNull;

import okhttp3.logging.HttpLoggingInterceptor;

public class HttpLogger_flower implements HttpLoggingInterceptor.Logger {

    @Override
    public void log(@NonNull String s) {
        if (s.length() > 4000) {
            int i = 0;
            while (i < s.length()) {
                if (i + 4000 < s.length()) {
                    Log.i(HttpLogger_flower.class.getSimpleName() + i,
                            formatJson(s.substring(i, i + 4000)));
                } else {
                    Log.i(HttpLogger_flower.class.getSimpleName() + i,
                            formatJson(s.substring(i, s.length())));
                }
                i += 4000;
            }
        } else {
            Log.i(HttpLogger_flower.class.getSimpleName(), formatJson(s));
        }
    }

    private String formatJson(String jsonStr) {
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

    private String getLevelStr(int level) {
        StringBuilder levelStr = new StringBuilder();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
}
