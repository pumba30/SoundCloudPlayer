package com.pumba30.soundcloudplayer;

import android.annotation.SuppressLint;
import android.app.Application;

import com.pumba30.soundcloudplayer.managers.RestServiceManager;
import com.pumba30.soundcloudplayer.managers.SessionManager;

public class App extends Application {

    public static App sAppInstance;
    private RestServiceManager mRestServiceManager;
    private SessionManager mSessionManager;

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate() {
        sAppInstance = this;
        mRestServiceManager = new RestServiceManager();
        mSessionManager = new SessionManager();
    }

    public RestServiceManager getRestServiceManager() {
        return mRestServiceManager;
    }

    public SessionManager getSessionManager() {
        return mSessionManager;
    }


}
