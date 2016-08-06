package com.pumba30.soundcloudplayer.events;

import com.pumba30.soundcloudplayer.api.models.Playlist;

import java.util.List;

public class LoadPlaylistCompleteEvent {

    public final List<Playlist> mPlaylists;

    public LoadPlaylistCompleteEvent(List<Playlist> playlists) {
        mPlaylists = playlists;
    }
}
