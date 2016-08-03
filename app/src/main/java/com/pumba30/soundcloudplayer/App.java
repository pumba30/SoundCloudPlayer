package com.pumba30.soundcloudplayer;

import android.annotation.SuppressLint;
import android.app.Application;

import com.pumba30.soundcloudplayer.managers.RestServiceManager;
import com.pumba30.soundcloudplayer.managers.SessionManager;

public class App extends Application {

    private static App sAppInstance;
    private RestServiceManager mRestServiceManager;
    private SessionManager mSessionManager;

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate() {
        sAppInstance = this;
        mRestServiceManager = new RestServiceManager();
        mSessionManager = new SessionManager();
    }


    public static App getInstance() {
        if (sAppInstance != null) {
            return sAppInstance;
        } else {
            throw new RuntimeException("Some went wrong!");
        }
    }

    public RestServiceManager getRestServiceManager() {
        return mRestServiceManager;
    }

    public SessionManager getSessionManager() {
        return mSessionManager;
    }


}
