package com.pumba30.soundcloudplayer.utils;

import com.google.gson.Gson;

public class Serializer<T> {


    public String objectToJson(T serializedObj) {
        if (serializedObj != null) {
            Gson gson = new Gson();
            return gson.toJson(serializedObj);
        } else {
            throw new NullPointerException("Object is null");
        }
    }

    public  T objectFromJson(String s, Class<T> type) {
        if (s != null) {
            Gson gson = new Gson();
            return gson.fromJson(s, type);
        } else {
            throw new NullPointerException("String is null");
        }
    }
}
