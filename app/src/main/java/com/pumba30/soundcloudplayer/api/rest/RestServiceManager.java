package com.pumba30.soundcloudplayer.api.rest;

import com.pumba30.soundcloudplayer.api.models.Playlists;
import com.pumba30.soundcloudplayer.api.models.Token;
import com.pumba30.soundcloudplayer.api.models.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RestServiceManager {

    protected ApiService mApiService;

    public RestServiceManager() {
    }

    protected class RestCallbackWrapper<T> implements Callback<T> {

        private RestCallback<T> mRestCallback;

        public RestCallbackWrapper(RestCallback<T> restCallback) {
            mRestCallback = restCallback;
        }


        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (response.isSuccessful()) {
                mRestCallback.onSuccess(response.body());
            } else {
                mRestCallback.onError(response.code());
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            mRestCallback.onError(RestCallback.INTERNAL_ERROR_CODE);
        }
    }


    public interface RestCallback<T> {
        int INTERNAL_ERROR_CODE = -1;

        void onSuccess(T response);

        void onError(int errorCode);
    }


}
