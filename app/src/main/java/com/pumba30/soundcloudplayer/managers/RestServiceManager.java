package com.pumba30.soundcloudplayer.managers;

import android.util.Log;

import com.pumba30.soundcloudplayer.api.models.Playlist;
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
    public static final String TITLE_PLAYLIST = "title";
    public static final String KEY_PLAYLIST = "playlist";
    public static final String KEY_PLAYLIST_ID = "id";
    public static final String KEY_TRACK_ID = "id";
    public static final String KEY_SHARING = "sharing";
    public static final String KEY_TRACKS = "tracks";

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
        mApiService.createPlaylist(getMapToCreatePlaylist(titlePlaylist, sharing))
                .enqueue(new RestCallbackWrapper<>(playlistRestCallback));
    }

    public void addTrackToPlaylist(String playlistId, List<String> trackId,
                                   RestServiceManager.RestCallback<Playlist> playlistRestCallback) {
        mApiService.addTrackToPlaylist(playlistId, getMapToAddPlayList(trackId))
                .enqueue(new RestCallbackWrapper<>(playlistRestCallback));
    }

    public void getPlaylistById(String playlistId, RestServiceManager.RestCallback<Playlist> playlistRestCallback){
        mApiService.getPlaylistById(playlistId).enqueue(new RestCallbackWrapper<>(playlistRestCallback));
    }


    private Map<String, Map<String, List<Map<String, String>>>> getMapToAddPlayList(List<String> trackIdList) {

        List<Map<String, String>> listTracksIds = new ArrayList<>();
        for (int i = 0; i < trackIdList.size(); i++) {
            Map<String, String> mapTrackId = new HashMap<>();
            mapTrackId.put(KEY_TRACK_ID, trackIdList.get(i));
            listTracksIds.add(mapTrackId);
        }

        Map<String, List<Map<String, String>>> listMapTracksIds = new HashMap<>();
        listMapTracksIds.put(KEY_TRACKS, listTracksIds);

        Map<String, Map<String, List<Map<String, String>>>> mapPlaylist = new HashMap<>();
        mapPlaylist.put(KEY_PLAYLIST, listMapTracksIds);

        return mapPlaylist;
    }


    private Map<String, Map<String, String>> getMapToCreatePlaylist(String titlePlaylist, String sharing) {
        Map<String, String> map = new HashMap<>();
        map.put(TITLE_PLAYLIST, titlePlaylist);

        //sharing may be public or private
        map.put(KEY_SHARING, sharing);

        Map<String, Map<String, String>> playlist = new HashMap<>();
        playlist.put(KEY_PLAYLIST, map);

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
