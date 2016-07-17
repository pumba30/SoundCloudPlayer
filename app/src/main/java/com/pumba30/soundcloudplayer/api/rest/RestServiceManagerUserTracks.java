package com.pumba30.soundcloudplayer.api.rest;

import com.pumba30.soundcloudplayer.api.models.Playlists;
import com.pumba30.soundcloudplayer.api.models.Token;
import com.pumba30.soundcloudplayer.api.models.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;

/**
 * Created by pumba30 on 17.07.2016.
 */
public class RestServiceManagerUserTracks extends RestServiceManager {
    private static final String CLIENT_ID = "a09c7db0f83a5b19c3435543291fdf69";
    private static final String KEY_CLIENT_ID = "client_id";

    public RestServiceManagerUserTracks() {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new ClientInterceptor(KEY_CLIENT_ID, CLIENT_ID));
        mApiService = RestClientFactory.getRestApiService(interceptors);
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
}
