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

/**
 * Created by pumba30 on 28.06.2016.
 */
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
    Call<Track> deleteTRackFromMCollection(@Path("trackId") int trackId);

    @GET("tracks/{trackId}")
    Call<Track> loadSoundCloudTrack(@Path("trackId") int trackId);

    
    /*Collection a.k.a Like tracks*/
    @GET("me/favorites")
    Call<List<Track>> getListTrackColection();


    /*Playlist*/
    @POST("playlists")
    Call<Playlist> createPlaylist(@Body Map<String, Map<String, String>> map);

    @GET("me/playlists")
    Call<List<Playlists>> getPlaylist(); // TODO: 25.07.2016  work method is not checked

}
