package com.pumba30.soundcloudplayer.api.rest;

import android.support.annotation.NonNull;

import com.pumba30.soundcloudplayer.BuildConfig;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClientFactory {
    public static final String BASE_URL = "https://api.soundcloud.com/";

    public static ApiService getRestApiService(List<Interceptor> interceptors) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient(interceptors))
                .build();

        return retrofit.create(ApiService.class);
    }


    @NonNull
    private static OkHttpClient getClient(List<Interceptor> interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(120, TimeUnit.SECONDS);
        builder.writeTimeout(120, TimeUnit.SECONDS);
        builder.readTimeout(120, TimeUnit.SECONDS);
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(newLoggingInterceptor(HttpLoggingInterceptor.Level.BODY));
        }

        return builder.build();
    }

    private static Interceptor newLoggingInterceptor(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(level);
        return interceptor;
    }

}
