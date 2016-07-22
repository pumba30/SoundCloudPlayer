package com.pumba30.soundcloudplayer.managers;

import android.util.Log;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.api.models.Token;
import com.pumba30.soundcloudplayer.api.models.User;

public class SessionManager {
    public static final String LOG_TAG = SessionManager.class.getSimpleName();
    private PreferencesManager mPreferencesManager = PreferencesManager.getInstance(App.sAppInstance);


    public void createLoginSession(Token token, boolean isUserLoggedIn) {
        if (token != null) {
            mPreferencesManager.storeLoginSession(token, isUserLoggedIn);
        } else {
            Log.d(LOG_TAG, "Create login session failed");
        }
    }

    public void saveUser(User user) {
        mPreferencesManager.saveUser(user);
    }

    public boolean checkLogin() {
        return mPreferencesManager.isUserLoggedIn();
    }

    public void logoutUser() {
        mPreferencesManager.logoutUser();
    }


    public User getUser() {
        return mPreferencesManager.getUser();
    }

    public String getToken() {
        return mPreferencesManager.getToken();
    }

    public boolean isUserLoggedIn() {

        return mPreferencesManager.isUserLoggedIn();
    }

}
