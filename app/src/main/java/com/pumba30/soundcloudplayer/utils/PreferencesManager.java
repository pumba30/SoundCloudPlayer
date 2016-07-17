package com.pumba30.soundcloudplayer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.pumba30.soundcloudplayer.api.models.Token;

/**
 * Created by pumba30 on 04.07.2016.
 */
public class PreferencesManager {

    public static final String PREFERENCES_MANAGER = "soundCloudPlayer";
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_USER_IS_IN_ACCOUNT = "userIsInAccount";
    public static final String KEY_TOKEN = "keyToken";
    private SharedPreferences mPreferences;
    private static PreferencesManager sManager;

    private PreferencesManager(Context context) {
        mPreferences = context.getSharedPreferences(PREFERENCES_MANAGER, Context.MODE_PRIVATE);
    }

    public static synchronized PreferencesManager getInstance(Context context) {
        if (sManager == null) {
            sManager = new PreferencesManager(context);
        }
        return sManager;
    }

    public void storeToken(Token token) {
        SharedPreferences.Editor editor = mPreferences.edit();

        editor.putString(KEY_TOKEN, token.getAccessToken());
        editor.putBoolean(KEY_USER_IS_IN_ACCOUNT, true);
        editor.apply();
    }

    public String getToken() {
        return mPreferences.getString(KEY_TOKEN, "");
    }


    //action user logout
    public void clearToken() {
        mPreferences.edit().putString(KEY_TOKEN, null).apply();
    }

    public boolean getUserIsInAccount() {
        return mPreferences.getBoolean(KEY_USER_IS_IN_ACCOUNT, false);
    }

    //action user logout
    public void logoutUser(boolean isInAccount) {
        mPreferences.edit().putBoolean(KEY_USER_IS_IN_ACCOUNT, isInAccount).apply();
    }
}
