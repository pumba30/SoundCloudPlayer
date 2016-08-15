package com.pumba30.soundcloudplayer.api.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Stations {

    @SerializedName("collection")
    private List<Track> collection;
    @SerializedName("next_href")
    private String nextHref;

    public List<Track> getCollection() {
        return collection;
    }

    public String getNextHref() {
        return nextHref;
    }
}

