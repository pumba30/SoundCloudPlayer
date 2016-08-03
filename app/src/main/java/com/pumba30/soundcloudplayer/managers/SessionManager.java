package com.pumba30.soundcloudplayer.managers;

import android.util.Log;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.api.models.Token;
import com.pumba30.soundcloudplayer.api.models.User;

public class SessionManager {
    public static final String LOG_TAG = SessionManager.class.getSimpleName();

    public void createLoginSession(Token token, boolean isUserLoggedIn) {
        if (token != null) {
            PreferencesManager.storeLoginSession(App.getInstance(), token, isUserLoggedIn);
        } else {
            Log.d(LOG_TAG, "Create login session failed");
        }
    }

    public void saveUser(User user) {
        PreferencesManager.saveUser(App.getInstance(), user);
    }

    public boolean checkLogin() {
        return PreferencesManager.isUserLoggedIn(App.getInstance());
    }

    public void logoutUser() {
        PreferencesManager.logoutUser(App.getInstance());
    }


    public User getUser() {
        return PreferencesManager.getUser(App.getInstance());
    }

    public String getToken() {
        return PreferencesManager.getToken(App.getInstance());
    }

    public boolean isUserLoggedIn() {
        return PreferencesManager.isUserLoggedIn(App.getInstance());
    }

}
