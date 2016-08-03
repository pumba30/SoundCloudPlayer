package com.pumba30.soundcloudplayer.api.rest;

import android.content.Intent;
import android.util.Log;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.api.models.Token;
import com.pumba30.soundcloudplayer.api.models.User;
import com.pumba30.soundcloudplayer.managers.RestServiceManager;
import com.pumba30.soundcloudplayer.managers.SessionManager;
import com.pumba30.soundcloudplayer.ui.activity.MainActivity;

import java.util.HashMap;

// authority on SoundCloud
public class AuthoritySoundCloud {
    private static final String LOG_TAG = AuthoritySoundCloud.class.getSimpleName();

    public static final String CLIENT_SECRET = "5642ba002227d30265b7652d3edb11ff";
    public static final String GRANT_TYPE = "authorization_code";
    public static final String REDIRECT_URI = "soundcloudplayer://callback";
    public static final String RESPONSE_TYPE = "code";
    public static final String SCOPE = "non-expiring";
    public static final String DISPLAY = "popup";
    public static final String STATE = "no_state";
    public static final String CLIENT_ID = "a09c7db0f83a5b19c3435543291fdf69";
    public static final String URL_SOUNDCLOUD_CONNECT = "https://www.soundcloud.com/connect?";
    public static final String KEY_CLIENT_ID = "client_id";
    public static final String KEY_CLIENT_SECRET = "client_secret";
    public static final String KEY_GRANT_TYPE = "grant_type";
    public static final String KEY_REDIRECT_URI = "redirect_uri";
    public static final String KEY_CODE = "code";

    private static RestServiceManager sRestServiceManager = App.getInstance().getRestServiceManager();
    private static SessionManager sSessionManager = App.getInstance().getSessionManager();


    public static String getUrlConnect() {
        return URL_SOUNDCLOUD_CONNECT +
                "client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&response_type=" + RESPONSE_TYPE +
                "&scope=" + SCOPE +
                "&display=" + DISPLAY +
                "&state=" + STATE;
    }

    public static void authorization(String code) {
        requestToken(code);
    }

    private static void requestToken(String code) {
        final HashMap<String, String> fieldMap = requestTokenUrlToFieldMap(code);

        sRestServiceManager.getToken(fieldMap, new RestServiceManager.RestCallback<Token>() {
            @Override
            public void onSuccess(Token response) {
                Log.d(LOG_TAG, "Token " + response.toString());
                sSessionManager.createLoginSession(response, true);
                requestUser();

                startMainActivity();
            }

            @Override
            public void onError(int errorCode) {
                Log.e(LOG_TAG, "Auth failure - " + errorCode);
                throw new RuntimeException("Auth failure");
            }
        });
    }

    private static void requestUser() {
        sRestServiceManager.getUser(new RestServiceManager.RestCallback<User>() {
            @Override
            public void onSuccess(User response) {
                Log.d(LOG_TAG, "User saved OK: " + response.toString());
                sSessionManager.saveUser(response);
            }

            @Override
            public void onError(int errorCode) {
                Log.e(LOG_TAG, "User null - " + errorCode);
                throw new NullPointerException("User null");
            }
        });
    }

    private static HashMap<String, String> requestTokenUrlToFieldMap(String code) {
        if (code != null) {
            HashMap<String, String> fieldMap = new HashMap<>();
            fieldMap.put(KEY_CLIENT_ID, CLIENT_ID);
            fieldMap.put(KEY_CLIENT_SECRET, CLIENT_SECRET);
            fieldMap.put(KEY_GRANT_TYPE, GRANT_TYPE);
            fieldMap.put(KEY_REDIRECT_URI, REDIRECT_URI);
            fieldMap.put(KEY_CODE, code);
            return fieldMap;
        } else {
            return null;
        }
    }

    private static void startMainActivity() {
        Intent intent = new Intent(App.getInstance(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getInstance().startActivity(intent);
    }

}
