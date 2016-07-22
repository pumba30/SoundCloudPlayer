package com.pumba30.soundcloudplayer.managers;

import android.util.Log;

import com.pumba30.soundcloudplayer.api.models.Playlists;
import com.pumba30.soundcloudplayer.api.models.Token;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.api.models.User;
import com.pumba30.soundcloudplayer.api.rest.ApiService;
import com.pumba30.soundcloudplayer.api.rest.ClientInterceptor;
import com.pumba30.soundcloudplayer.api.rest.RestClientFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestServiceManager {

    private static final String LOG_TAG = RestServiceManager.class.getSimpleName();
    public static final String GENRE = "genre";
    private String mGenre;


    private ApiService mApiService;

    public RestServiceManager() {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new ClientInterceptor());
        mApiService = RestClientFactory.getRestApiService(interceptors);
    }

    public void loadMusicByGenre(String genreMusic, RestServiceManager.RestCallback<List<Track>> restCallback) {
        mApiService.getMusic(getQueryToMap(GENRE, genreMusic))
                .enqueue(new RestCallbackWrapper<>(restCallback));
    }


    public void loadPublicTracks(RestServiceManager.RestCallback<List<Track>> restCallback) {
        mApiService.loadPublicTracks().enqueue(new RestCallbackWrapper<>(restCallback));
    }


    public void getToken(Map authMap, RestServiceManager.RestCallback<Token> restCallback) {
        mApiService.authorize(authMap).enqueue(new RestCallbackWrapper<>(restCallback));
    }

    public void getPlayLists(RestServiceManager.RestCallback<List<Playlists>> restCallback) {
        mApiService.getMyPlaylists().enqueue(new RestCallbackWrapper<>(restCallback));
    }

    public void toMyCollection(int idTrack, RestServiceManager.RestCallback<Track> trackRestCallback) {
        mApiService.toMyCollection(idTrack).enqueue(new RestCallbackWrapper<>(trackRestCallback));
    }

    public void deleteFromMyCollection(int idTrack, RestServiceManager.RestCallback<Track> trackRestCallback) {
        mApiService.deleteFromMyCollection(idTrack).enqueue(new RestCallbackWrapper<>(trackRestCallback));
    }

    public void getMyCollection(RestServiceManager.RestCallback<List<Track>> trackRestCallback) {
        mApiService.getMyColection().enqueue(new RestCallbackWrapper<>(trackRestCallback));
    }

    public void getUser(RestServiceManager.RestCallback<User> userRestCallback) {
        mApiService.getUser().enqueue(new RestCallbackWrapper<>(userRestCallback));
    }

    private static Map<String, String> getQueryToMap(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public String getGenre() {
        return mGenre;
    }

    protected class RestCallbackWrapper<T> implements Callback<T> {

        private RestCallback<T> mRestCallback;

        public RestCallbackWrapper(RestCallback<T> restCallback) {
            mRestCallback = restCallback;
        }


        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            Log.d(LOG_TAG, "Responce to string: " + response.message());

            if (response.isSuccessful()) {
                mRestCallback.onSuccess(response.body());
            } else {
                mRestCallback.onError(response.code());
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            Log.d(LOG_TAG, t.getLocalizedMessage());
        }
    }


    public interface RestCallback<T> {

        void onSuccess(T response);

        void onError(int errorCode);
    }


}
