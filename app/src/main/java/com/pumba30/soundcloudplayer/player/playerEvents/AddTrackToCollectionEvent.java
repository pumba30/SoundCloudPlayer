package com.pumba30.soundcloudplayer.player.playerEvents;

/**
 * Created by pumba30 on 17.07.2016.
 */
public class AddTrackToCollectionEvent {
    private final boolean mIsAdded;


    public AddTrackToCollectionEvent(boolean isAdded) {
        mIsAdded = isAdded;
    }

    public boolean isAdded() {
        return mIsAdded;
    }
}
