package com.pumba30.soundcloudplayer.api.rest;

import android.content.Context;
import android.util.Log;

import com.pumba30.soundcloudplayer.App;
import com.pumba30.soundcloudplayer.api.models.Token;
import com.pumba30.soundcloudplayer.utils.PreferencesManager;

import java.util.HashMap;

// authority on SoundCloud and receive the token
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
    public static final String FORMAT = "%sclient_id=%s&redirect_uri=%s&responce_type=%s&scope=%s&display=%s&state=%s";


    public static String getUrlConnect() {
        return URL_SOUNDCLOUD_CONNECT +
                "client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&response_type=" + RESPONSE_TYPE +
                "&scope=" + SCOPE +
                "&display=" + DISPLAY +
                "&state=" + STATE;
    }

    public static void authorization(String code, Context context) {
        requestToken(code, context);
    }

    private static void requestToken(String code, final Context context) {
        HashMap<String, String> fieldMap = requestTokenUrlToMap(code);

        App.getAppInstance().getRestServiceManager().getToken(fieldMap, new RestServiceManager.RestCallback<Token>() {
            @Override
            public void onSuccess(Token response) {
                PreferencesManager.getInstance(context).storeToken(response);
                Log.d(LOG_TAG, "Token " + response.toString());
            }

            @Override
            public void onError(int errorCode) {
                Log.e(LOG_TAG, "Auth failure - " + errorCode);
            }
        });
    }

    private static HashMap<String, String> requestTokenUrlToMap(String code) {
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


}
