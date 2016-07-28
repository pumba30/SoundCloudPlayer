package com.pumba30.soundcloudplayer.player.playerEventBus;

public class ObjectsBusEvent {
    private final String mMessage;
    private final Object mObject;


    public ObjectsBusEvent(String message, Object o) {
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
