package com.pumba30.soundcloudplayer.managers;


import android.content.Context;
import android.util.Log;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Playlist;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.player.playerEventBus.TrackCollectionEvent;
import com.pumba30.soundcloudplayer.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class QueryManager {
    public static final String LOG_TAG = QueryManager.class.getSimpleName();
    public static final String TRACK_ADDED = "trackAdded";
    public static final String TRACK_DELETED = "trackDeleted";
    public static final String LIST_TRACK_LOADED = "listTrackLoaded";
    public static final String LIST_COLLECTION_TRACK_LOADED = "listCollectionTrackLoaded";
    private static QueryManager sQueryManager = null;
    private Context mContext;
    private RestServiceManager mRestManager;

    public static synchronized QueryManager getInstance() {
        if (sQueryManager == null) {
            sQueryManager = new QueryManager();
        }
        return sQueryManager;
    }

    public QueryManager() {
        mContext = App.sAppInstance;
        mRestManager = App.sAppInstance.getRestServiceManager();
    }

    public void addTrackToMyCollection(Track track) {
        int idTrack = track.getId();
        mRestManager.toMyCollection(idTrack, new RestServiceManager.RestCallback<Track>() {
            @Override
            public void onSuccess(Track response) {
                Utils.toast(mContext, R.string.added_to_collection);
                EventBus.getDefault().post(new TrackCollectionEvent(TRACK_ADDED, null));
            }

            @Override
            public void onError(int errorCode) {
                Utils.toast(mContext, R.string.failed_added);
            }
        });
    }

    public void deleteTrackFromCollection(Track track) {
        int idTrack = track.getId();
        mRestManager.deleteFromMyCollection(idTrack, new RestServiceManager.RestCallback<Track>() {
            @Override
            public void onSuccess(Track response) {
                Utils.toast(mContext, R.string.track_deleted);
                EventBus.getDefault().post(new TrackCollectionEvent(TRACK_DELETED, null));
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
                        .post(new TrackCollectionEvent(LIST_COLLECTION_TRACK_LOADED, response));
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
                            .post(new TrackCollectionEvent(LIST_TRACK_LOADED, tracks));
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


    public void createPlaylist() {
        mRestManager.createPlaylist(new RestServiceManager.RestCallback<Playlist>() {
            @Override
            public void onSuccess(Playlist response) {

            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }
}