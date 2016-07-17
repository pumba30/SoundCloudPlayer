package com.pumba30.soundcloudplayer;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.pumba30.soundcloudplayer.api.rest.RestServiceManager;
import com.pumba30.soundcloudplayer.api.rest.RestServiceManagerUserTracks;
import com.pumba30.soundcloudplayer.utils.PreferencesManager;

public class App extends Application {

    private static App sAppInstance;
    private RestServiceManagerUserTracks mRestServiceManager;


    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate() {
        sAppInstance = this;
        mRestServiceManager = new RestServiceManagerUserTracks();

    }

    public static App getAppInstance() {
        if (sAppInstance == null) {
            throw new RuntimeException("Something is going wrong.");
        }

        return sAppInstance;
    }

    public static boolean isUserLogged() {
        return PreferencesManager.getInstance(getAppInstance()).getUserIsInAccount();
    }

    @NonNull
    public static String getToken() {
        return PreferencesManager.getInstance(getAppInstance()).getToken();
    }

    public RestServiceManagerUserTracks getRestServiceManager() {
        return mRestServiceManager;
    }
}
