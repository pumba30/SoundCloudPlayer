package com.pumba30.soundcloudplayer.api.rest;

import com.pumba30.soundcloudplayer.api.models.Playlist;
import com.pumba30.soundcloudplayer.api.models.Playlists;
import com.pumba30.soundcloudplayer.api.models.Token;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.api.models.User;
import com.pumba30.soundcloudplayer.managers.SessionManager;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    /*User*/
    @GET("me")
    Call<User> getUser();

    @FormUrlEncoded
    @POST("oauth2/token")
    Call<Token> authorize(@FieldMap Map<String, String> authMap);


    /*Tracks*/
    @GET("tracks")
    Call<List<Track>> loadPublicTracks();

    @GET("tracks")
    Call<List<Track>> getMusic(@QueryMap Map<String, String> stringMap);

    @PUT("me/favorites/{trackId}")
    Call<Track> addTrackToCollection(@Path("trackId") int trackId);

    @DELETE("me/favorites/{trackId}")
    Call<Track> deleteTrackFromMCollection(@Path("trackId") String trackId);

    @GET("tracks/{trackId}")
    Call<Track> loadSoundCloudTrack(@Path("trackId") int trackId);

    @GET("tracks")
    Call<Track> searchTrack(@Query("q") String query);


    /*Collection e.i. Like tracks*/
    @GET("me/favorites")
    Call<List<Track>> getListTrackColection();


    /*Playlist*/
    @POST("playlists")
    Call<Playlist> createPlaylist(@Body Map<String, Map<String, String>> map);

    @GET("me/playlists")
    Call<List<Playlist>> getPlaylist();

    @PUT("me/playlists/{playlistId}")
    Call<Playlist> addTrackToPlaylist(@Path("playlistId") String playlistId,
                                      @Body Map<String, Map<String, List<Map<String, String>>>> map);

    @GET("me/playlists/{playlistId}")
    Call<Playlist> getPlaylistById(@Path("playlistId") String playlistId);

}
