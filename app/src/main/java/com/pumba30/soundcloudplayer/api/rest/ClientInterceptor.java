package com.pumba30.soundcloudplayer.api.rest;

import com.pumba30.soundcloudplayer.App;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ClientInterceptor implements Interceptor {
    private static final String LOG_TAG = ClientInterceptor.class.getSimpleName();
    private static final String KEY_OAUTH_TOKEN = "oauth_token";
    private static final String CLIENT_ID = "a09c7db0f83a5b19c3435543291fdf69";
    private static final String KEY_CLIENT_ID = "client_id";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl.Builder urlBuilder = original.url().newBuilder();
        String token = App.getInstance().getSessionManager().getToken();

        if (token == null) {
            urlBuilder.addEncodedQueryParameter(KEY_CLIENT_ID, CLIENT_ID).build();
        } else {
            urlBuilder.addEncodedQueryParameter(KEY_OAUTH_TOKEN, token);
        }

        Request newRequest = original.newBuilder()
                .url(urlBuilder.build())
                .build();

        return chain.proceed(newRequest);
    }
}
