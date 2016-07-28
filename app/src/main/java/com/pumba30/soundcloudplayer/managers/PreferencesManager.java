package com.pumba30.soundcloudplayer.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.pumba30.soundcloudplayer.api.models.Token;
import com.pumba30.soundcloudplayer.api.models.User;

public class PreferencesManager {

    public static final String PREFERENCES_MANAGER = "soundCloudPlayer";
    public static final String KEY_USER_IS_LOGGED_IN = "userIsLoggedIn";
    public static final String KEY_TOKEN = "keyToken";
    public static final String KEY_USER = "keyUser";
    private static final String KEY_ITEM_SPINNER = "itemSpinner";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

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

    public void storeLoginSession(Token token, boolean isUserLoggedIn) {
        mEditor = mPreferences.edit();
        mEditor.putString(KEY_TOKEN, token.getAccessToken());
        mEditor.putBoolean(KEY_USER_IS_LOGGED_IN, isUserLoggedIn);
        mEditor.apply();
    }


    private String userToStrings(User user) {
        String stringUser;
        if (user != null) {
            Gson gson = new Gson();
            stringUser = gson.toJson(user);
        } else {
            throw new NullPointerException("User is null");
        }
        return stringUser;
    }

    public User getUser() {
        String userString = mPreferences.getString(KEY_USER, null);
        User user;
        if (userString != null) {
            Gson gson = new Gson();
            user = gson.fromJson(userString, User.class);
        } else {
            throw new NullPointerException("User in SharedPreferences is null");
        }
        return user;
    }

    //action user logout
    public void logoutUser() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(KEY_TOKEN, null);
        editor.putString(KEY_USER, null);
        editor.putBoolean(KEY_USER_IS_LOGGED_IN, false);
        editor.apply();
    }

    public String getToken() {
        return mPreferences.getString(KEY_TOKEN, null);
    }

    public boolean isUserLoggedIn() {
        return mPreferences.getBoolean(KEY_USER_IS_LOGGED_IN, false);
    }

    public void saveUser(User user) {
        String stringUser = userToStrings(user);
        mEditor = mPreferences.edit();
        mEditor.putString(KEY_USER, stringUser);
        mEditor.apply();
    }

    public void saveChoicedItemSpinner(int choicedItem) {
        mEditor = mPreferences.edit();
        mEditor.putInt(KEY_ITEM_SPINNER, choicedItem);
        mEditor.apply();
    }

    public int getChoicedItemSpinner() {
        return mPreferences.getInt(KEY_ITEM_SPINNER, -1);
    }
}
