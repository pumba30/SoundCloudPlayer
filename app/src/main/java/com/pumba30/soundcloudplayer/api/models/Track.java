package com.pumba30.soundcloudplayer.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pumba30 on 28.06.2016.
 */
public class Track implements Parcelable {
    @SerializedName("kind")
    private String mKind;

    @SerializedName("id")
    private int mId;

    @SerializedName("created_at")
    private String mCreatedAd;

    @SerializedName("user_id")
    private int mUserId;

    @SerializedName("duration")
    private int mDuration;

    @SerializedName("commentable")
    private boolean mCommentable;

    @SerializedName("state")
    private String mState;

    @SerializedName("original_content_size")
    private int mOriginalContentSize;

    @SerializedName("last_modified")
    private String mLastModified;

    @SerializedName("sharing")
    private String mSharing;

    @SerializedName("tag_list")
    private String mTagList;

    @SerializedName("permalink")
    private String mPermalink;

    @SerializedName("streamable")
    private boolean mStreamable;

    @SerializedName("embeddable_by")
    private String mEmbeddableBy;

    @SerializedName("downloadable")
    private boolean mDownloadable;

    @SerializedName("purchase_url")
    private String mPurchaseUrl;

    @SerializedName("label_id")
    private String mLabelId;

    @SerializedName("purchase_title")
    private String mPurchaseTitle;

    @SerializedName("genre")
    private String mGenre;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("description")
    private String mDescription;


    @SerializedName("label_name")
    private String mLabelName;

    @SerializedName("release")
    private String mRelease;

    @SerializedName("track_type")
    private String mTrackType;

    @SerializedName("key_signature")
    private String mKeySignature;

    @SerializedName("isrc")
    private String mIsrc;

    @SerializedName("video_url")
    private String mVideoUrl;

    @SerializedName("bpm")
    private String mBpm;

    @SerializedName("release_year")
    private String mReleaseYear;

    @SerializedName("release_month")
    private String mReleaseMonth;

    @SerializedName("release_day")
    private String mReleaseDay;

    @SerializedName("original_format")
    private String mOriginalFormat;

    @SerializedName("licence")
    private String mLicence;

    @SerializedName("uri")
    private String mUri;

    @SerializedName("user")
    private User mUser;

    @SerializedName("permalink_url")
    private String mPermalinkUrl;

    @SerializedName("artwork_url")
    private String mArtworkUrl;

    @SerializedName("waveform_url")
    private String mWaveFormUrl;

    @SerializedName("stream_url")
    private String mStreamUrl;

    @SerializedName("playback_count")
    private int mPlaybackCount;

    @SerializedName("dowload_count")
    private int mDownloadCount;

    @SerializedName("favoritings_count")
    private int mFavoritingsCount;

    @SerializedName("comment_count")
    private int mCommentCount;

    @SerializedName("attachments_uri")
    private String mAttachmentsUri;

    protected Track(Parcel in) {
        mKind = in.readString();
        mId = in.readInt();
        mCreatedAd = in.readString();
        mUserId = in.readInt();
        mDuration = in.readInt();
        mCommentable = in.readByte() != 0;
        mState = in.readString();
        mOriginalContentSize = in.readInt();
        mLastModified = in.readString();
        mSharing = in.readString();
        mTagList = in.readString();
        mPermalink = in.readString();
        mStreamable = in.readByte() != 0;
        mEmbeddableBy = in.readString();
        mDownloadable = in.readByte() != 0;
        mPurchaseUrl = in.readString();
        mLabelId = in.readString();
        mPurchaseTitle = in.readString();
        mGenre = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
        mLabelName = in.readString();
        mRelease = in.readString();
        mTrackType = in.readString();
        mKeySignature = in.readString();
        mIsrc = in.readString();
        mVideoUrl = in.readString();
        mBpm = in.readString();
        mReleaseYear = in.readString();
        mReleaseMonth = in.readString();
        mReleaseDay = in.readString();
        mOriginalFormat = in.readString();
        mLicence = in.readString();
        mUri = in.readString();
        mUser = in.readParcelable(User.class.getClassLoader());
        mPermalinkUrl = in.readString();
        mArtworkUrl = in.readString();
        mWaveFormUrl = in.readString();
        mStreamUrl = in.readString();
        mPlaybackCount = in.readInt();
        mDownloadCount = in.readInt();
        mFavoritingsCount = in.readInt();
        mCommentCount = in.readInt();
        mAttachmentsUri = in.readString();
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    public void setKind(String kind) {
        mKind = kind;
    }

    public String getKind() {
        return mKind;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAd = createdAt;
    }

    public String getCreatedAt() {
        return mCreatedAd;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setCommentable(boolean commentable) {
        mCommentable = commentable;
    }

    public boolean getCommentable() {
        return mCommentable;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getState() {
        return mState;
    }

    public void setOriginalContentSize(int originalContentSize) {
        mOriginalContentSize = originalContentSize;
    }

    public int getOriginalContentSize() {
        return mOriginalContentSize;
    }

    public void setLastModified(String lastModified) {
        mLastModified = lastModified;
    }

    public String getLastModified() {
        return mLastModified;
    }

    public void setSharing(String sharing) {
        mSharing = sharing;
    }

    public String getSharing() {
        return mSharing;
    }

    public void setTagList(String tagList) {
        mTagList = tagList;
    }

    public String getTagList() {
        return mTagList;
    }

    public void setPermalink(String permalink) {
        mPermalink = permalink;
    }

    public String getPermalink() {
        return mPermalink;
    }

    public void setStreamable(boolean streamable) {
        mStreamable = streamable;
    }

    public boolean getStreamable() {
        return mStreamable;
    }

    public void setEmbeddableBy(String embeddableBy) {
        mEmbeddableBy = embeddableBy;
    }

    public String getEmbeddableBy() {
        return mEmbeddableBy;
    }

    public void setDownloadable(boolean downloadable) {
        mDownloadable = downloadable;
    }

    public boolean getDownloadable() {
        return mDownloadable;
    }

    public void setPurchaseUrl(String purchaseUrl) {
        mPurchaseUrl = purchaseUrl;
    }

    public String getPurchaseUrl() {
        return mPurchaseUrl;
    }

    public void setLabelId(String labelId) {
        mLabelId = labelId;
    }

    public String getLabelId() {
        return mLabelId;
    }

    public void setPurchaseTitle(String purchaseTitle) {
        mPurchaseTitle = purchaseTitle;
    }

    public String getPurchaseTitle() {
        return mPurchaseTitle;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setLabelName(String labelName) {
        mLabelName = labelName;
    }

    public String getLabelName() {
        return mLabelName;
    }

    public void setRelease(String release) {
        mRelease = release;
    }

    public String getRelease() {
        return mRelease;
    }

    public void setTrackType(String trackType) {
        mTrackType = trackType;
    }

    public String getTrackType() {
        return mTrackType;
    }

    public void setKeySignature(String keySignature) {
        mKeySignature = keySignature;
    }

    public String getKeySignature() {
        return mKeySignature;
    }

    public void setIsrc(String isrc) {
        mIsrc = isrc;
    }

    public String getIsrc() {
        return mIsrc;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setBpm(String bpm) {
        mBpm = bpm;
    }

    public String getBpm() {
        return mBpm;
    }

    public void setReleaseYear(String releaseYear) {
        mReleaseYear = releaseYear;
    }

    public String getReleaseYear() {
        return mReleaseYear;
    }

    public void setReleaseMonth(String releaseMonth) {
        mReleaseMonth = releaseMonth;
    }

    public String getReleaseMonth() {
        return mReleaseMonth;
    }

    public void setReleaseDay(String releaseDay) {
        mReleaseDay = releaseDay;
    }

    public String getReleaseDay() {
        return mReleaseDay;
    }

    public void setOriginalFormat(String originalFormat) {
        mOriginalFormat = originalFormat;
    }

    public String getOriginalFormat() {
        return mOriginalFormat;
    }

    public void setLicence(String licence) {
        mLicence = licence;
    }

    public String getLicence() {
        return mLicence;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public String getUri() {
        return mUri;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public User getUser() {
        return mUser;
    }

    public void setPermalink_url(String permalinkUrl) {
        mPermalink = permalinkUrl;
    }

    public String getPermalinkUrl() {
        return mPermalink;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setWaveformUrl(String waveformUrl) {
        mWaveFormUrl = waveformUrl;
    }

    public String getWaveformUrl() {
        return mWaveFormUrl;
    }

    public void setStreamUrl(String streamUrl) {
        mStreamUrl = streamUrl;
    }

    public String getStreamUrl() {
        return mStreamUrl;
    }

    public void setPlaybackCount(int playbackCount) {
        mPlaybackCount = playbackCount;
    }

    public int getPlaybackCount() {
        return mPlaybackCount;
    }

    public void setDownloadCount(int downloadCount) {
        mDownloadCount = downloadCount;
    }

    public int getDownloadCount() {
        return mDownloadCount;
    }

    public void setFavoritingsCount(int favoritingsCount) {
        this.mFavoritingsCount = favoritingsCount;
    }

    public int getFavoritingsCount() {
        return mFavoritingsCount;
    }

    public void setCommentCount(int commentCount) {
        mFavoritingsCount = commentCount;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public void setAttachmentsUri(String attachmentsUri) {
        mAttachmentsUri = attachmentsUri;
    }

    public String getAttachmentsUri() {
        return mAttachmentsUri;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mKind);
        parcel.writeInt(mId);
        parcel.writeString(mCreatedAd);
        parcel.writeInt(mUserId);
        parcel.writeInt(mDuration);
        parcel.writeByte((byte) (mCommentable ? 1 : 0));
        parcel.writeString(mState);
        parcel.writeInt(mOriginalContentSize);
        parcel.writeString(mLastModified);
        parcel.writeString(mSharing);
        parcel.writeString(mTagList);
        parcel.writeString(mPermalink);
        parcel.writeByte((byte) (mStreamable ? 1 : 0));
        parcel.writeString(mEmbeddableBy);
        parcel.writeByte((byte) (mDownloadable ? 1 : 0));
        parcel.writeString(mPurchaseUrl);
        parcel.writeString(mLabelId);
        parcel.writeString(mPurchaseTitle);
        parcel.writeString(mGenre);
        parcel.writeString(mTitle);
        parcel.writeString(mDescription);
        parcel.writeString(mLabelName);
        parcel.writeString(mRelease);
        parcel.writeString(mTrackType);
        parcel.writeString(mKeySignature);
        parcel.writeString(mIsrc);
        parcel.writeString(mVideoUrl);
        parcel.writeString(mBpm);
        parcel.writeString(mReleaseYear);
        parcel.writeString(mReleaseMonth);
        parcel.writeString(mReleaseDay);
        parcel.writeString(mOriginalFormat);
        parcel.writeString(mLicence);
        parcel.writeString(mUri);
        parcel.writeParcelable(mUser, i);
        parcel.writeString(mPermalinkUrl);
        parcel.writeString(mArtworkUrl);
        parcel.writeString(mWaveFormUrl);
        parcel.writeString(mStreamUrl);
        parcel.writeInt(mPlaybackCount);
        parcel.writeInt(mDownloadCount);
        parcel.writeInt(mFavoritingsCount);
        parcel.writeInt(mCommentCount);
        parcel.writeString(mAttachmentsUri);
    }
}
