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

public class RestServiceManager {

    private ApiService mApiService;

    public RestServiceManager() {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new ClientInterceptor());
        mApiService = RestClientFactory.getRestApiService(interceptors);
    }


    public void loadPublicTracks(RestCallback<List<Track>> restCallback) {
        mApiService.loadPublicTracks().enqueue(new RestCallbackWrapper<>(restCallback));
    }


    public void getToken(Map authMap, RestCallback<Token> restCallback) {
        mApiService.authorize(authMap).enqueue(new RestCallbackWrapper<>(restCallback));
    }

    public void getPlayLists(RestCallback<List<Playlists>> restCallback) {
        mApiService.getMyPlaylists().enqueue(new RestCallbackWrapper<>(restCallback));
    }

    public void toMyCollection(int idTrack, RestCallback<Track> trackRestCallback) {
        mApiService.toMyCollection(idTrack).enqueue(new RestCallbackWrapper<>(trackRestCallback));
    }

    public void getMyCollection(RestCallback<List<Track>> trackRestCallback) {
        mApiService.getMyColection().enqueue(new RestCallbackWrapper<>(trackRestCallback));
    }


    private class RestCallbackWrapper<T> implements Callback<T> {

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
