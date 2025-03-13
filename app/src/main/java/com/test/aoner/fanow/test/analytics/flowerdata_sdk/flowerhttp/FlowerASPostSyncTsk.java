package com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerhttp;

import com.test.aoner.fanow.test.constant_flower.Constant_flower;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FlowerASPostSyncTsk {

    private final String url;
    private final String taskId;
    private final String body;

    public FlowerASPostSyncTsk(String taskId, String url, String body) {
        this.taskId = taskId;
        this.url = url;
        this.body = body;
    }

    public FlowerASResp getASResp() {
        try {
            URL url = new URL(this.url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setReadTimeout(180000);
            urlConnection.setConnectTimeout(120000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Content-Type", "application/json");
//            urlConnection.setRequestProperty("Content-Length", (body.getBytes()).length + "");
            urlConnection.setRequestProperty("product", Constant_flower.PRODUCT);
            urlConnection.getOutputStream().write(body.getBytes());
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
            try {
                int resCode = urlConnection.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

//                    JSONObject resultObj = new JSONObject(result.toString());
                    return new FlowerASResp(this.taskId, new JSONObject());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.getInputStream().close();
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new FlowerASResp(this.taskId, null);
    }

    public String getUrl() {
        return url;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getBody() {
        return body;
    }

    
}
