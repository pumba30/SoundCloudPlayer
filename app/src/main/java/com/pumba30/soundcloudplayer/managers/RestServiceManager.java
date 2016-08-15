package com.pumba30.soundcloudplayer.managers;

import android.util.Log;

import com.pumba30.soundcloudplayer.api.models.Playlist;
import com.pumba30.soundcloudplayer.api.models.Playlists;
import com.pumba30.soundcloudplayer.api.models.Stations;
import com.pumba30.soundcloudplayer.api.models.Token;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.api.models.User;
import com.pumba30.soundcloudplayer.api.rest.ApiService;
import com.pumba30.soundcloudplayer.api.rest.ClientInterceptor;
import com.pumba30.soundcloudplayer.api.rest.RestClientFactory;
import com.pumba30.soundcloudplayer.utils.MapHelper;

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
    private static final String GENRE = "genre";
    private static final String STATION = "Station";
    private static final int WITH_NEXT_PAGE = 1;

    private ApiService mApiService;

    public RestServiceManager() {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new ClientInterceptor());
        mApiService = RestClientFactory.getRestApiService(interceptors);
    }

    public void loadMusicByGenre(String genreMusic, RestServiceManager.RestCallback<List<Track>> restCallback) {
        mApiService.getMusic(MapHelper.getQueryToMap(GENRE, genreMusic))
                .enqueue(new RestCallbackWrapper<>(restCallback));
    }

    public void getToken(Map authMap, RestServiceManager.RestCallback<Token> restCallback) {
        mApiService.authorize(authMap).enqueue(new RestCallbackWrapper<>(restCallback));
    }

    public void getPlaylists(RestServiceManager.RestCallback<List<Playlist>> restCallback) {
        mApiService.getPlaylist().enqueue(new RestCallbackWrapper<>(restCallback));
    }

    public void toMyCollection(int idTrack, RestServiceManager.RestCallback<Track> trackRestCallback) {
        mApiService.addTrackToCollection(idTrack).enqueue(new RestCallbackWrapper<>(trackRestCallback));
    }

    public void deleteFromMyCollection(String idTrack, RestCallback<Track> trackRestCallback) {
        mApiService.deleteTrackFromMCollection(idTrack).enqueue(new RestCallbackWrapper<>(trackRestCallback));
    }

    public void getMyCollection(RestServiceManager.RestCallback<List<Track>> trackRestCallback) {
        mApiService.getListTrackColection().enqueue(new RestCallbackWrapper<>(trackRestCallback));
    }

    public void getUser(RestServiceManager.RestCallback<User> userRestCallback) {
        mApiService.getUser().enqueue(new RestCallbackWrapper<>(userRestCallback));
    }

    public void createPlaylist(String titlePlaylist, String sharing,
                               RestServiceManager.RestCallback<Playlist> playlistRestCallback) {
        mApiService.createPlaylist(MapHelper.getMapToCreatePlaylist(titlePlaylist, sharing))
                .enqueue(new RestCallbackWrapper<>(playlistRestCallback));
    }

    public void addTrackToPlaylist(String playlistId, List<String> trackId,
                                   RestServiceManager.RestCallback<Playlist> playlistRestCallback) {
        mApiService.addTrackToPlaylist(playlistId, MapHelper.getMapToAddPlayList(trackId))
                .enqueue(new RestCallbackWrapper<>(playlistRestCallback));
    }

    public void getPlaylistById(String playlistId, RestServiceManager.RestCallback<Playlist> playlistRestCallback) {
        mApiService.getPlaylistById(playlistId).enqueue(new RestCallbackWrapper<>(playlistRestCallback));
    }

    public void searchTrack(String query, RestServiceManager.RestCallback<List<Track>> trackRestCallback) {
        mApiService.searchTrack(query).enqueue(new RestCallbackWrapper<List<Track>>(trackRestCallback));
    }

    public void loadStations(RestCallback<Stations> stationCallback) {
        mApiService.loadStations(STATION, WITH_NEXT_PAGE).enqueue(new RestCallbackWrapper<Stations>(stationCallback));
    }

    public void loadMoreStations(Map<String, String> requestMapNextPage, RestCallback<Stations> stationsRestCallback) {
        mApiService.loadMoreStations(requestMapNextPage).enqueue(new RestCallbackWrapper<>(stationsRestCallback));
    }

    public void deletePlaylist(String playlistId, RestCallback<List<Playlist>> callback) {
        mApiService.deletePlaylist(playlistId).enqueue(new RestCallbackWrapper<>(callback));
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
