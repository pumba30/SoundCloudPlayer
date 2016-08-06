package com.pumba30.soundcloudplayer.events;


import com.pumba30.soundcloudplayer.api.models.Playlist;

public class PlaylistCreatedEvent {

    public final Playlist mPlaylist;

    public PlaylistCreatedEvent(Playlist response) {
        mPlaylist = response;
    }
}
