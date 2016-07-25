package com.pumba30.soundcloudplayer.player.playerEventBus;

public class TrackCollectionEvent {
    private final String mMessage;
    private final Object mObject;


    public TrackCollectionEvent(String message, Object o) {
        mObject = o;
        mMessage = message;
    }


    public String getMessage() {
        return mMessage;
    }

    public Object getObject() {
        return mObject;
    }
}
