package com.pumba30.soundcloudplayer.api.rest;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pumba30 on 30.06.2016.
 */
public class ClientInterceptor implements Interceptor {
    private static final String CLIENT_ID = "a09c7db0f83a5b19c3435543291fdf69";
    private static final String KEY_CLIENT_ID = "client_id";



    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
        HttpUrl urlWithQuery =
                originalHttpUrl.newBuilder().addQueryParameter(KEY_CLIENT_ID, CLIENT_ID).build();
        Request.Builder builder = original.newBuilder().url(urlWithQuery);
        Request request = builder.build();

        return chain.proceed(request);
    }
}
