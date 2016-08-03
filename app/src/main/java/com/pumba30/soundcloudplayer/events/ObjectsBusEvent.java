package com.pumba30.soundcloudplayer.events;

public class ObjectsBusEvent<T> {
    public final String mMessage;
    public final T mObject;


    public ObjectsBusEvent(String message, T obj) {
        mObject = obj;
        mMessage = message;
    }
}
