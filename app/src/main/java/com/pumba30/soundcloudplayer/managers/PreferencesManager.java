package com.pumba30.soundcloudplayer.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.pumba30.soundcloudplayer.api.models.Token;
import com.pumba30.soundcloudplayer.api.models.User;
import com.pumba30.soundcloudplayer.utils.Serializer;

public class PreferencesManager {

    public static final String PREFERENCES_MANAGER = "soundCloudPlayer";
    public static final String KEY_USER_IS_LOGGED_IN = "userIsLoggedIn";
    public static final String KEY_TOKEN = "keyToken";
    public static final String KEY_USER = "keyUser";
    private static final String KEY_ITEM_SPINNER = "itemSpinner";
    private static final String KEY_IS_LAUNCHED = "keyIsLaunched";


    public static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(PREFERENCES_MANAGER, Context.MODE_PRIVATE);
    }

    public static void storeLoginSession(Context context, Token token, boolean isUserLoggedIn) {
        getPref(context).edit()
                .putString(KEY_TOKEN, token.getAccessToken())
                .putBoolean(KEY_USER_IS_LOGGED_IN, isUserLoggedIn)
                .apply();
    }


    public static void saveUser(Context context, User user) {
        String stringUser = userToStrings(user);
        getPref(context).edit()
                .putString(KEY_USER, stringUser)
                .apply();
    }

    private static String userToStrings(User user) {
        Serializer<User> stringUser = new Serializer<>();
        return stringUser.objectToJson(user);
    }

    public static User getUser(Context context) {
        String user = getPref(context).getString(KEY_USER, null);
        Serializer<User> userSerializer = new Serializer<>();
        return userSerializer.objectFromJson(user, User.class);
    }

    //action user logout
    public static void logoutUser(Context context) {
        getPref(context).edit()
                .putString(KEY_TOKEN, null)
                .putString(KEY_USER, null)
                .putBoolean(KEY_USER_IS_LOGGED_IN, false)
                .apply();
    }

    public static String getToken(Context context) {
        return getPref(context).getString(KEY_TOKEN, null);
    }

    public static boolean isUserLoggedIn(Context context) {
        return getPref(context).getBoolean(KEY_USER_IS_LOGGED_IN, false);
    }

    public static void saveChoicedItemSpinner(Context context, int choicedItem) {
        getPref(context).edit()
                .putInt(KEY_ITEM_SPINNER, choicedItem)
                .apply();
    }

    public static int getChoicedItemSpinner(Context context) {
        return getPref(context).getInt(KEY_ITEM_SPINNER, -1);
    }

    public static void setLauchedSearchActivity(Context context, boolean lauchedSearchActivity) {
        getPref(context).edit()
                .putBoolean(KEY_IS_LAUNCHED, lauchedSearchActivity)
                .apply();
    }

    public static boolean isLaunchedSearchActivity(Context context) {
        return getPref(context).getBoolean(KEY_IS_LAUNCHED, false);
    }
}
