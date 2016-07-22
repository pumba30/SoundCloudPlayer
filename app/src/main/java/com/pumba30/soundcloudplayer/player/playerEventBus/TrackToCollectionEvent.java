package com.pumba30.soundcloudplayer.player.playerEventBus;

/**
 * Created by pumba30 on 17.07.2016.
 */
public class TrackToCollectionEvent {
    private final boolean mIsAdded;


    public TrackToCollectionEvent(boolean isAdded) {
        mIsAdded = isAdded;
    }

    public boolean isAdded() {
        return mIsAdded;
    }
}
