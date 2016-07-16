package com.pumba30.soundcloudplayer;

import android.annotation.SuppressLint;
import android.app.Application;

import com.pumba30.soundcloudplayer.api.rest.RestServiceManager;
import com.pumba30.soundcloudplayer.utils.PreferencesManager;

public class App extends Application {

    private static App sAppInstance;
    private RestServiceManager mRestServiceManager;


    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate() {
        mRestServiceManager = new RestServiceManager();
        sAppInstance = this;

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

    public RestServiceManager getRestServiceManager() {
        return mRestServiceManager;
    }
}
