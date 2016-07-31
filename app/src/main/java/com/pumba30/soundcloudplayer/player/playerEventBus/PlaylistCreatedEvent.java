package com.pumba30.soundcloudplayer.player.playerEventBus;


import com.pumba30.soundcloudplayer.api.models.Playlist;

public class PlaylistCreatedEvent {

    private  Playlist mPlaylist;

    public PlaylistCreatedEvent(Playlist response) {
        mPlaylist = response;
    }

    public Playlist getPlaylist() {
        return mPlaylist;
    }
}
