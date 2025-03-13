package com.test.aoner.fanow.test.activity_fragment_flower.fragment_flower;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.test.aoner.fanow.test.R;
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower;
import com.test.aoner.fanow.test.bean_flower.config_info_flower.GlobalConfig_flower;
import com.test.aoner.fanow.test.constant_flower.Constant_flower;
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseFragment_flower;

public class DataSafetyFragment_flower extends BaseFragment_flower {

    private WebView webView_flower;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_safety_flower, container, false);
        init_flower(view);
        return view;
    }

    private void init_flower(View view) {
        initWebViewConfigs_flower(view);

        switch (StaticConfig_flower.Select_Country) {
            case Constant_flower.Country_Cote:
                webView_flower.loadUrl(Constant_flower.Privacy_Cote);
                break;
            case Constant_flower.Country_Ghana:
                webView_flower.loadUrl(Constant_flower.Privacy_Ghana);
                break;
            case Constant_flower.Country_Kenya:
                webView_flower.loadUrl(Constant_flower.Privacy_Kenya);
                break;
            case Constant_flower.Country_Uganda:
                webView_flower.loadUrl(Constant_flower.Privacy_Uganda);
                break;
            case Constant_flower.Country_Nigeria:
                webView_flower.loadUrl(Constant_flower.Privacy_Nigeria);
                break;
            case Constant_flower.Country_Vietnam:
                webView_flower.loadUrl(Constant_flower.Privacy_Vietnam);
                break;
            case Constant_flower.Country_Tanzan:
                webView_flower.loadUrl(Constant_flower.Privacy_Tanzan);
                break;
            default: webView_flower.loadUrl(Constant_flower.Privacy_Main);
        }

        GlobalConfig_flower.getInstance().handleOperatedView(requireActivity(),
                view.findViewById(R.id.fragment_data_safety_view_operated),
                view.findViewById(R.id.fragment_data_safety_iv_operated_logo),
                view.findViewById(R.id.fragment_data_safety_tv_company_name));

    }

    @SuppressLint({"SetJavaScriptEnabled", "ObsoleteSdkInt"})
    private void initWebViewConfigs_flower(View view) {
        webView_flower = view.findViewById(R.id.fragment_data_safety_web_view);
        webView_flower.setInitialScale(100);
        webView_flower.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return shouldOverrideUrlLoading(view, request.getUrl().toString());
            }
        });

        WebSettings settings = webView_flower.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setTextZoom(100);
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowUniversalAccessFromFileURLs(true);
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowContentAccess(true);
        }
    }

    @Override
    public String getPagetag() {
        return "STATEMENT";
    }
}