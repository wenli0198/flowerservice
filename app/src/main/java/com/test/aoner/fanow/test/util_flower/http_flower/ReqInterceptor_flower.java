package com.test.aoner.fanow.test.util_flower.http_flower;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ReqInterceptor_flower implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request_equalcash = chain.request();
//        Request.Builder builder_equalcash = request_equalcash.newBuilder();
//        Request targetRequest_equalcash = builder_equalcash.build();
        return chain.proceed(request_equalcash);
    }
}
