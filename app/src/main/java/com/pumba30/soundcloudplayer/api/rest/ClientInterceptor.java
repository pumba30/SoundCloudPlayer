package com.pumba30.soundcloudplayer.api.rest;

import android.text.TextUtils;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.utils.Utils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ClientInterceptor implements Interceptor {
    private static final String KEY_OAUTH_TOKEN = "oauth_token";

    private String mKey;
    private String mValue;


    public ClientInterceptor(String key, String value) {
        mKey = key;
        mValue = value;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        String token = null;
        if (!TextUtils.isEmpty(App.getToken())) {
            token = App.getToken();
        }
        HttpUrl originalHttpUrl = original.url();

        HttpUrl urlWithQuery;

        if (!App.isUserLogged()) {
            urlWithQuery = originalHttpUrl
                    .newBuilder()
                    .addQueryParameter(mKey, mValue)
                    .build();
        } else {
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
