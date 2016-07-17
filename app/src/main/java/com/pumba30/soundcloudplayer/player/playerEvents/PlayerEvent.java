package com.pumba30.soundcloudplayer.player.playerEvents;

/**
 * Created by pumba30 on 07.07.2016.
 */
public class PlayerEvent {

    private final boolean mIsPlaying;

    public PlayerEvent(boolean isPlaying) {
        mIsPlaying = isPlaying;
    }

    public boolean isPlaying() {
        return mIsPlaying;
    }
}
