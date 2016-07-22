package com.pumba30.soundcloudplayer.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("permalink")
    private String permalink;
    @SerializedName("username")
    private String username;
    @SerializedName("uri")
    private String uri;
    @SerializedName("permalink_url")
    private String permalinkUrl;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("country")
    private String country;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("city")
    private String city;
    @SerializedName("description")
    private String description;
    @SerializedName("discogs_name")
    private String discogsName;
    @SerializedName("myspace_name")
    private String myspaceName;
    @SerializedName("website")
    private String website;
    @SerializedName("website_title")
    private String websiteTitle;
    @SerializedName("online")
    private boolean online;
    @SerializedName("track_count")
    private int trackCount;
    @SerializedName("playlist_count")
    private int playlistCount;
    @SerializedName("followers_count")
    private int followersCount;
    @SerializedName("followings_count")
    private int followingsCount;
    @SerializedName("public_favorites_count")
    private int publicFavoritesCount;
    @SerializedName("plan")
    private String plan;
    @SerializedName("private_tracks_count")
    private int privateTracksCount;
    @SerializedName("private_playlists_count")
    private int privatePlaylistsCount;
    @SerializedName("primary_email_confirmed")
    private boolean primaryEmailConfirmed;

    protected User(Parcel in) {
        id = in.readInt();
        permalink = in.readString();
        username = in.readString();
        uri = in.readString();
        permalinkUrl = in.readString();
        avatarUrl = in.readString();
        country = in.readString();
        fullName = in.readString();
        city = in.readString();
        description = in.readString();
        discogsName = in.readString();
        myspaceName = in.readString();
        website = in.readString();
        websiteTitle = in.readString();
        online = in.readByte() != 0;
        trackCount = in.readInt();
        playlistCount = in.readInt();
        followersCount = in.readInt();
        followingsCount = in.readInt();
        publicFavoritesCount = in.readInt();
        plan = in.readString();
        privateTracksCount = in.readInt();
        privatePlaylistsCount = in.readInt();
        primaryEmailConfirmed = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPermalinkUrl() {
        return permalinkUrl;
    }

    public void setPermalinkUrl(String permalinkUrl) {
        this.permalinkUrl = permalinkUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscogsName() {
        return discogsName;
    }

    public void setDiscogsName(String discogsName) {
        this.discogsName = discogsName;
    }

    public String getMyspaceName() {
        return myspaceName;
    }

    public void setMyspaceName(String myspaceName) {
        this.myspaceName = myspaceName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsiteTitle() {
        return websiteTitle;
    }

    public void setWebsiteTitle(String websiteTitle) {
        this.websiteTitle = websiteTitle;
    }

    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public int getPlaylistCount() {
        return playlistCount;
    }

    public void setPlaylistCount(int playlistCount) {
        this.playlistCount = playlistCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    public void setFollowingsCount(int followingsCount) {
        this.followingsCount = followingsCount;
    }

    public int getPublicFavoritesCount() {
        return publicFavoritesCount;
    }

    public void setPublicFavoritesCount(int publicFavoritesCount) {
        this.publicFavoritesCount = publicFavoritesCount;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public int getPrivateTracksCount() {
        return privateTracksCount;
    }

    public void setPrivateTracksCount(int privateTracksCount) {
        this.privateTracksCount = privateTracksCount;
    }

    public int getPrivatePlaylistsCount() {
        return privatePlaylistsCount;
    }

    public void setPrivatePlaylistsCount(int privatePlaylistsCount) {
        this.privatePlaylistsCount = privatePlaylistsCount;
    }

    public boolean getPrimaryEmailConfirmed() {
        return primaryEmailConfirmed;
    }

    public void setPrimaryEmailConfirmed(boolean primaryEmailConfirmed) {
        this.primaryEmailConfirmed = primaryEmailConfirmed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(permalink);
        parcel.writeString(username);
        parcel.writeString(uri);
        parcel.writeString(permalinkUrl);
        parcel.writeString(avatarUrl);
        parcel.writeString(country);
        parcel.writeString(fullName);
        parcel.writeString(city);
        parcel.writeString(description);
        parcel.writeString(discogsName);
        parcel.writeString(myspaceName);
        parcel.writeString(website);
        parcel.writeString(websiteTitle);
        parcel.writeByte((byte) (online ? 1 : 0));
        parcel.writeInt(trackCount);
        parcel.writeInt(playlistCount);
        parcel.writeInt(followersCount);
        parcel.writeInt(followingsCount);
        parcel.writeInt(publicFavoritesCount);
        parcel.writeString(plan);
        parcel.writeInt(privateTracksCount);
        parcel.writeInt(privatePlaylistsCount);
        parcel.writeByte((byte) (primaryEmailConfirmed ? 1 : 0));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", permalink='" + permalink + '\'' +
                ", username='" + username + '\'' +
                ", uri='" + uri + '\'' +
                ", permalinkUrl='" + permalinkUrl + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", country='" + country + '\'' +
                ", fullName='" + fullName + '\'' +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", discogsName='" + discogsName + '\'' +
                ", myspaceName='" + myspaceName + '\'' +
                ", website='" + website + '\'' +
                ", websiteTitle='" + websiteTitle + '\'' +
                ", online=" + online +
                ", trackCount=" + trackCount +
                ", playlistCount=" + playlistCount +
                ", followersCount=" + followersCount +
                ", followingsCount=" + followingsCount +
                ", publicFavoritesCount=" + publicFavoritesCount +
                ", plan='" + plan + '\'' +
                ", privateTracksCount=" + privateTracksCount +
                ", privatePlaylistsCount=" + privatePlaylistsCount +
                ", primaryEmailConfirmed=" + primaryEmailConfirmed +
                '}';
    }
}