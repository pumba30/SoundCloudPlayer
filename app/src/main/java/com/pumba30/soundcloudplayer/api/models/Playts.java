package com.pumba30.soundcloudplayer.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Playts {

    public List<Playlist> mPlaylists;

    public List<Playlist> getPlaylists() {
        return mPlaylists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        mPlaylists = playlists;
    }
}
