package com.pumba30.soundcloudplayer.player.playerEventBus;

import com.pumba30.soundcloudplayer.api.models.Playlist;

import java.util.List;

/**
 * Created by pumba30 on 25.07.2016.
 */
public class LoadPlaylistComplete {

    private List<Playlist> mPlaylists;

    public LoadPlaylistComplete(List<Playlist> playlists) {
        mPlaylists = playlists;
    }

    public List<Playlist> getPlaylists() {
        return mPlaylists;
    }
}
