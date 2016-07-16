package com.pumba30.soundcloudplayer.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pumba30 on 28.06.2016.
 */
public class User {
    @SerializedName("id")
    private int mId;

    @SerializedName("kind")
    private String mKind;

    @SerializedName("permalink")
    private String mPermalink;

    @SerializedName("username")
    private String mUserName;

    @SerializedName("last_modified")
    private String mLastModified;

    @SerializedName("uri")
    private String mUri;

    @SerializedName("permalink_url")
    private String mPermalinkUrl;

    @SerializedName("avatar_url")
    private String mAvatarUrl;

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

    public void setPermalink(String permalink) {
        mPermalink = permalink;
    }

    public String getPermalink() {
        return mPermalink;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setLastModified(String lastModified) {
        mLastModified = lastModified;
    }

    public String getLastModified() {
        return mLastModified;
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

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }
}
