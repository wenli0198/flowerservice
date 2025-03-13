package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerutil;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

public class FlowerASUtil {

    public static String stringToGZIP(String sourceString) {
        String zipString = "";

        if (!TextUtils.isEmpty(sourceString)) {
            try {
                String mUtf8Char = "UTF-8";
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
                gzipOutputStream.write(sourceString.getBytes(mUtf8Char));
                gzipOutputStream.close();

                byte[] zipBuffer = outputStream.toByteArray();
                zipString = Base64.encodeToString(zipBuffer, Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING);
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return zipString;
    }

    public static String safeString(String text) {
        if (text == null || TextUtils.isEmpty(text)) {
            text = "";
        }
        return text;
    }

    @SuppressLint("SimpleDateFormat")
    public static long getFilterStartTime(int nextDays) {
        try {
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.add(Calendar.DAY_OF_MONTH, nextDays);

            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH) + 1;
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(year + "-" + month + "-" + day + " 00:00:00");

            if (date != null) {
                return date.getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0L;
    }

    @SuppressLint("SimpleDateFormat")
    public static long getFilterEndTime() {
        try {
            Calendar mCalendar = Calendar.getInstance();
            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH) + 1;
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(year + "-" + month + "-" + day + " 23:59:59");

            if (date != null) {
                return date.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0L;
    }

    public static ArrayList<String> filterOrderIds(String content) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Pattern pattern = Pattern.compile("ORDER-[0-9]+");
            Matcher matcher = pattern.matcher(content.trim());

            while (matcher.find()) {
                String orderId = FlowerASUtil.safeString(matcher.group()).trim();
                if (!TextUtils.isEmpty(orderId)) {
                    if (!list.contains(orderId)) {
                        list.add(orderId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
