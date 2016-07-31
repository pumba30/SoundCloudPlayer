package com.pumba30.soundcloudplayer.player.playerEventBus;

/**
 * Created by pumba30 on 31.07.2016.
 */
public class SearchActivityRunnedEvent {
     private boolean mIsRunned;


    public SearchActivityRunnedEvent(boolean isRunned) {
        mIsRunned = isRunned;
    }

    public boolean isRunned() {
        return mIsRunned;
    }
}
