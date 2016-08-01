package com.pumba30.soundcloudplayer.events;

import com.pumba30.soundcloudplayer.api.models.Playlist;

import java.util.List;

public class LoadPlaylistCompleteEvent {

    private final List<Playlist> mPlaylists;

    public LoadPlaylistCompleteEvent(List<Playlist> playlists) {
        mPlaylists = playlists;
    }

    public List<Playlist> getPlaylists() {
        return mPlaylists;
    }

}
