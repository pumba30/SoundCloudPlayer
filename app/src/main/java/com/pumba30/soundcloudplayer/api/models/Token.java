package com.pumba30.soundcloudplayer.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pumba30 on 04.07.2016.
 */
public class Token {
    @SerializedName("access_token")
    private String mAccessToken;
    @SerializedName("scope")
    private String mScope;

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getScope() {
        return mScope;
    }

    public void setScope(String scope) {
        mScope = scope;
    }


    @Override
    public String toString() {
        return "Token{" +
                "mAccessToken='" + mAccessToken + '\'' +
                ", mScope='" + mScope + '\'' +
                '}';
    }
}
