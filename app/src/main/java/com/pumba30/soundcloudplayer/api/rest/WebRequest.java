package com.pumba30.soundcloudplayer.api.rest;


import android.content.Context;
import android.util.Log;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Playlist;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.events.LoadPlaylistCompleteEvent;
import com.pumba30.soundcloudplayer.events.ObjectsBusEvent;
import com.pumba30.soundcloudplayer.events.PlaylistCreatedEvent;
import com.pumba30.soundcloudplayer.managers.RestServiceManager;
import com.pumba30.soundcloudplayer.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class WebRequest {
    public static final String LOG_TAG = WebRequest.class.getSimpleName();
    public static final String TRACK_ADDED = "trackAdded";
    public static final String TRACK_DELETED = "trackDeleted";
    public static final String LIST_TRACK_LOADED = "listTrackLoaded";
    public static final String LIST_COLLECTION_TRACK_LOADED = "listCollectionTrackLoaded";
    public static final String PLAYLIST_LOADED = "playlistLoaded";
    public static final String SEARCH_TRACK_LOADED = "searchTrackLoaded";
    private static WebRequest sWebRequest = null;
    private Context mContext;
    private RestServiceManager mRestManager;

    public static synchronized WebRequest getInstance() {
        if (sWebRequest == null) {
            sWebRequest = new WebRequest();
        }
        return sWebRequest;
    }

    public WebRequest() {
        mContext = App.getInstance();
        mRestManager = App.getInstance().getRestServiceManager();
    }

    public void addTrackToMyCollection(Track track) {
        int idTrack = track.getId();
        mRestManager.toMyCollection(idTrack, new RestServiceManager.RestCallback<Track>() {
            @Override
            public void onSuccess(Track response) {
                Utils.toast(mContext, R.string.added_to_collection);
                EventBus.getDefault().post(new ObjectsBusEvent<>(TRACK_ADDED, null));
            }

            @Override
            public void onError(int errorCode) {
                Utils.toast(mContext, R.string.failed_added);
            }
        });
    }

    public void deleteTrackFromCollection(String trackId) {
        mRestManager.deleteFromMyCollection(trackId, new RestServiceManager.RestCallback<Track>() {
            @Override
            public void onSuccess(Track response) {
                Utils.toast(mContext, R.string.track_deleted);
                EventBus.getDefault().post(new ObjectsBusEvent<>(TRACK_DELETED, null));
            }

            @Override
            public void onError(int errorCode) {
                Log.d(LOG_TAG, "Error delete track: " + errorCode);
            }
        });
    }

    public void getCollectionList() {
        mRestManager.getMyCollection(new RestServiceManager.RestCallback<List<Track>>() {
            @Override
            public void onSuccess(List<Track> response) {
                EventBus.getDefault()
                        .post(new ObjectsBusEvent<>(LIST_COLLECTION_TRACK_LOADED, response));
            }

            @Override
            public void onError(int errorCode) {
                Log.d(LOG_TAG, "Error get collection list: " + errorCode);
            }
        });
    }

    public void loadMusicByGenre(String genreMusic) {
        mRestManager.loadMusicByGenre(genreMusic, new RestServiceManager.RestCallback<List<Track>>() {
            @Override
            public void onSuccess(List<Track> tracks) {
                if (tracks != null) {
                    Log.d(LOG_TAG, "tracks:" + tracks.toString());
                    EventBus.getDefault()
                            .post(new ObjectsBusEvent<>(LIST_TRACK_LOADED, tracks));
                } else {
                    Log.d(LOG_TAG, "Error load track");
                }
            }

            @Override
            public void onError(int errorCode) {
                Log.d(LOG_TAG, "Error request code: " + String.valueOf(errorCode));
            }
        });
    }


    public void createPlaylist(String title, String sharing) {
        mRestManager.createPlaylist(title, sharing, new RestServiceManager.RestCallback<Playlist>() {
            @Override
            public void onSuccess(Playlist response) {
                EventBus.getDefault().post(new PlaylistCreatedEvent(response));
            }

            @Override
            public void onError(int errorCode) {
                Log.d(LOG_TAG, "Error request code: " + String.valueOf(errorCode));
            }
        });
    }

    public void getMePlaylists() {
        mRestManager.getPlaylists(new RestServiceManager.RestCallback<List<Playlist>>() {
            @Override
            public void onSuccess(List<Playlist> response) {
                EventBus.getDefault().post(new LoadPlaylistCompleteEvent(response));
            }

            @Override
            public void onError(int errorCode) {
                Log.d(LOG_TAG, "Error request code: " + String.valueOf(errorCode));
            }
        });
    }

    public void addTrackToPlaylist(String playlistId, List<String> tracksIds) {
        mRestManager.addTrackToPlaylist(playlistId, tracksIds, new RestServiceManager.RestCallback<Playlist>() {
            @Override
            public void onSuccess(Playlist response) {
                EventBus.getDefault().post(new ObjectsBusEvent<>(TRACK_ADDED, response));
            }

            @Override
            public void onError(int errorCode) {
                Log.d(LOG_TAG, "Error request code: " + String.valueOf(errorCode));
            }
        });
    }

    public void getPlaylistById(String playlistId) {
        mRestManager.getPlaylistById(playlistId, new RestServiceManager.RestCallback<Playlist>() {

            @Override
            public void onSuccess(Playlist response) {
                EventBus.getDefault().post(new ObjectsBusEvent<>(PLAYLIST_LOADED, response));
            }

            @Override
            public void onError(int errorCode) {
                Log.d(LOG_TAG, "Error request code: " + String.valueOf(errorCode));
            }
        });
    }

    public void searchTrack(String query) {
        mRestManager.searchTrack(query, new RestServiceManager.RestCallback<List<Track>>() {
            @Override
            public void onSuccess(List<Track> response) {
                EventBus.getDefault().post(new ObjectsBusEvent<>(SEARCH_TRACK_LOADED, response));
                Log.d(LOG_TAG, response.toString());
            }

            @Override
            public void onError(int errorCode) {
                Log.d(LOG_TAG, "Error request code: " + String.valueOf(errorCode));
            }
        });
    }

    public void loadStation() {
        mRestManager.loadStation(new RestServiceManager.RestCallback<List<Track>>() {

            @Override
            public void onSuccess(List<Track> response) {
                Log.d(LOG_TAG, "Station: " + response.toString());
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }
}
