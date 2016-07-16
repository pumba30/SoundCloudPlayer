package com.pumba30.soundcloudplayer.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * With the help of an application created track
 */
public class CreatedWith {
    @SerializedName("id")
    private int mId;

    @SerializedName("kind")
    private String mKind;

    @SerializedName("name")
    private String mName;

    @SerializedName("uri")
    private String mUri;

    @SerializedName("permalink_url")
    private String mPermalinkUrl;

    @SerializedName("external_url")
    private String mExternalUrl;

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setKind(String kind) {
        mKind = kind;
    }

    public String getKind() {
        return mKind;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public String getUri() {
        return mUri;
    }

    public void setPermalinkUrl(String permalinkUrl) {
        mPermalinkUrl = permalinkUrl;
    }

    public String getPermalinkUrl() {
        return mPermalinkUrl;
    }

    public void setExternalUrl(String externalUrl) {
        mExternalUrl = externalUrl;
    }

    public String getExternalUrl() {
        return mExternalUrl;
    }
}
