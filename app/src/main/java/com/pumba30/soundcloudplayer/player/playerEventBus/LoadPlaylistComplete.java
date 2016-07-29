package com.pumba30.soundcloudplayer.player.playerEventBus;

import com.pumba30.soundcloudplayer.api.models.Playlist;

import java.util.List;

public class LoadPlaylistComplete {

    private final List<Playlist> mPlaylists;

    public LoadPlaylistComplete(List<Playlist> playlists) {
        mPlaylists = playlists;
    }

    public List<Playlist> getPlaylists() {
        return mPlaylists;
    }

}
