package com.pumba30.soundcloudplayer.api.rest;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.utils.PreferencesManager;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ClientInterceptor implements Interceptor {
    private static final String CLIENT_ID = "a09c7db0f83a5b19c3435543291fdf69";
    private static final String KEY_CLIENT_ID = "client_id";
    public static final String KEY_OAUTH_TOKEN = "oauth_token";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl urlWithQuery;
        if (!App.isUserLogged()) {
            urlWithQuery = originalHttpUrl
                    .newBuilder()
                    .addQueryParameter(KEY_CLIENT_ID, CLIENT_ID)
                    .build();
        } else {
            final String token = App.getToken();
            urlWithQuery = originalHttpUrl
                    .newBuilder()
                    .addQueryParameter(KEY_OAUTH_TOKEN, token)
                    .build();
        }

        Request.Builder builder = original.newBuilder().url(urlWithQuery);
        Request request = builder.build();

        return chain.proceed(request);
    }
}
