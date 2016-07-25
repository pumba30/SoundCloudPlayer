package com.pumba30.soundcloudplayer.managers;

import android.util.Log;

import com.pumba30.soundcloudplayer.api.models.Playlist;
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
        mApiService.getPlaylist().enqueue(new RestCallbackWrapper<>(restCallback));
    }

    public void toMyCollection(int idTrack, RestServiceManager.RestCallback<Track> trackRestCallback) {
        mApiService.addTrackToCollection(idTrack).enqueue(new RestCallbackWrapper<>(trackRestCallback));
    }

    public void deleteFromMyCollection(int idTrack, RestServiceManager.RestCallback<Track> trackRestCallback) {
        mApiService.deleteTRackFromMCollection(idTrack).enqueue(new RestCallbackWrapper<>(trackRestCallback));
    }

    public void getMyCollection(RestServiceManager.RestCallback<List<Track>> trackRestCallback) {
        mApiService.getListTrackColection().enqueue(new RestCallbackWrapper<>(trackRestCallback));
    }

    public void getUser(RestServiceManager.RestCallback<User> userRestCallback) {
        mApiService.getUser().enqueue(new RestCallbackWrapper<>(userRestCallback));
    }

    public void createPlaylist(RestServiceManager.RestCallback<Playlist> playlistRestCallback) {
        mApiService.createPlaylist(getMap()).enqueue(new RestCallbackWrapper<>(playlistRestCallback));
    }

    private Map<String, Map<String, String>> getMap() {

        Map<String, String> mapa = new HashMap<>();
        mapa.put("title", "adsf");
        mapa.put("id", "3434354343");

        Map<String, Map<String, String>> playlist = new HashMap<>();
        playlist.put("playlist", mapa);

//Yeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee!!!!!!!!!!!!!!!!!!!!!!!!!
        return playlist;
    }


    private Map<String, String> getQueryToMap(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        return map;
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
                Log.d(LOG_TAG, "Success responce to string: " + response.message());

            } else {
                mRestCallback.onError(response.code());
                Log.d(LOG_TAG, "Unsuccess responce to string: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            Log.d(LOG_TAG, t.getLocalizedMessage());
            Log.d(LOG_TAG, "Failure responce to string");
        }
    }


    public interface RestCallback<T> {

        void onSuccess(T response);

        void onError(int errorCode);
    }


}
