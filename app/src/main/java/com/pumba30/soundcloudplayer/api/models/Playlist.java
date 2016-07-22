package com.pumba30.soundcloudplayer.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    @SerializedName("duration")
    private long mDuration;
    @SerializedName("release_day")
    private Object mReleaseDay;
    @SerializedName("permalink_url")
    private String mPermalinkUrl;
    @SerializedName("genre")
    private Object mGenre;
    @SerializedName("permalink")
    private String mPermalink;
    @SerializedName("purchase_url")
    private Object mPurchaseUrl;
    @SerializedName("release_month")
    private Object mReleaseMonth;
    @SerializedName("description")
    private Object mDescription;
    @SerializedName("uri")
    private String mUri;
    @SerializedName("label_name")
    private Object mLabelName;
    @SerializedName("tag_list")
    private String mTagList;
    @SerializedName("release_year")
    private Object mReleaseYear;
    @SerializedName("secret_uri")
    private String mSecretUri;
    @SerializedName("track_count")
    private long mTrackCount;
    @SerializedName("user_id")
    private long mUserId;
    @SerializedName("last_modified")
    private String mLastModified;
    @SerializedName("license")
    private String mLicense;
    @SerializedName("tracks")
    private List<Track> mTrackList = new ArrayList<Track>();
    @SerializedName("playlist_type")
    private Object playlistType;
    @SerializedName("id")
    private int id;
    @SerializedName("downloadable")
    private Object mDownloadable;
    @SerializedName("sharing")
    private String mSharing;
    @SerializedName("secret_token")
    private String mSecretToken;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("release")
    private Object mRelease;
    @SerializedName("kind")
    private String mKind;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("type")
    private Object mType;
    @SerializedName("purchase_title")
    private Object mPurchaseTitle;
    @SerializedName("created_with")
    private CreatedWith mCreatedWith;
    @SerializedName("artwork_url")
    private Object mArtworkUrl;
    @SerializedName("ean")
    private Object mEan;
    @SerializedName("streamable")
    private boolean mStreamable;
    @SerializedName("user")
    private MiniUser mUser;
    @SerializedName("embeddable_by")
    private String mEmbeddableBy;
    @SerializedName("label_id")
    private Object mLabelId;

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public Object getReleaseDay() {
        return mReleaseDay;
    }

    public void setReleaseDay(Object releaseDay) {
        this.mReleaseDay = releaseDay;
    }

    public String getPermalinkUrl() {
        return mPermalinkUrl;
    }

    public void setPermalinkUrl(String permalinkUrl) {
        this.mPermalinkUrl = permalinkUrl;
    }

    public Object getGenre() {
        return mGenre;
    }

    public void setGenre(Object genre) {
        this.mGenre = genre;
    }

    public String getPermalink() {
        return mPermalink;
    }

    public void setPermalink(String permalink) {
        this.mPermalink = permalink;
    }

    public Object getPurchaseUrl() {
        return mPurchaseUrl;
    }

    public void setPurchaseUrl(Object purchaseUrl) {
        this.mPurchaseUrl = purchaseUrl;
    }

    public Object getReleaseMonth() {
        return mReleaseMonth;
    }

    public void setReleaseMonth(Object releaseMonth) {
        this.mReleaseMonth = releaseMonth;
    }

    public Object getDescription() {
        return mDescription;
    }

    public void setDescription(Object description) {
        this.mDescription = description;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        this.mUri = uri;
    }

    public Object getLabelName() {
        return mLabelName;
    }

    public void setLabelName(Object labelName) {
        this.mLabelName = labelName;
    }

    public String getTagList() {
        return mTagList;
    }

    public void setTagList(String tagList) {
        this.mTagList = tagList;
    }

    public Object getReleaseYear() {
        return mReleaseYear;
    }

    public void setReleaseYear(Object releaseYear) {
        this.mReleaseYear = releaseYear;
    }

    public String getSecretUri() {
        return mSecretUri;
    }

    public void setSecretUri(String secretUri) {
        this.mSecretUri = secretUri;
    }

    public long getTrackCount() {
        return mTrackCount;
    }

    public void setTrackCount(long trackCount) {
        this.mTrackCount = trackCount;
    }

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(long userId) {
        this.mUserId = userId;
    }

    public String getLastModified() {
        return mLastModified;
    }

    public void setLastModified(String lastModified) {
        this.mLastModified = lastModified;
    }

    public String getLicense() {
        return mLicense;
    }

    public void setLicense(String license) {
        this.mLicense = license;
    }

    public List<Track> getTrackList() {
        return mTrackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.mTrackList = trackList;
    }

    public Object getPlaylistType() {
        return playlistType;
    }

    public void setPlaylistType(Object playlistType) {
        this.playlistType = playlistType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getDownloadable() {
        return mDownloadable;
    }

    public void setDownloadable(Object downloadable) {
        this.mDownloadable = downloadable;
    }

    public String getSharing() {
        return mSharing;
    }

    public void setSharing(String sharing) {
        this.mSharing = sharing;
    }

    public String getSecretToken() {
        return mSecretToken;
    }

    public void setSecretToken(String secretToken) {
        this.mSecretToken = secretToken;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        this.mCreatedAt = createdAt;
    }

    public Object getRelease() {
        return mRelease;
    }

    public void setRelease(Object release) {
        this.mRelease = release;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        this.mKind = kind;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Object getType() {
        return mType;
    }

    public void setType(Object type) {
        this.mType = type;
    }

    public Object getPurchaseTitle() {
        return mPurchaseTitle;
    }

    public void setPurchaseTitle(Object purchaseTitle) {
        this.mPurchaseTitle = purchaseTitle;
    }

    public CreatedWith getCreatedWith() {
        return mCreatedWith;
    }

    public void setCreatedWith(CreatedWith createdWith) {
        this.mCreatedWith = createdWith;
    }

    public Object getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(Object artworkUrl) {
        this.mArtworkUrl = artworkUrl;
    }

    public Object getEan() {
        return mEan;
    }

    public void setEan(Object ean) {
        this.mEan = ean;
    }

    public boolean isStreamable() {
        return mStreamable;
    }

    public void setStreamable(boolean streamable) {
        this.mStreamable = streamable;
    }

    public MiniUser getUser() {
        return mUser;
    }

    public void setUser(MiniUser user) {
        this.mUser = user;
    }

    public String getEmbeddableBy() {
        return mEmbeddableBy;
    }

    public void setEmbeddableBy(String embeddableBy) {
        this.mEmbeddableBy = embeddableBy;
    }

    public Object getLabelId() {
        return mLabelId;
    }

    public void setLabelId(Object labelId) {
        this.mLabelId = labelId;
    }

}

