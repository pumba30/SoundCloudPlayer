package com.pumba30.soundcloudplayer.api.rest;

import com.pumba30.soundcloudplayer.api.models.Playlists;
import com.pumba30.soundcloudplayer.api.models.Token;
import com.pumba30.soundcloudplayer.api.models.Track;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by pumba30 on 28.06.2016.
 */
public interface ApiService {

    @GET("tracks")
    Call<List<Track>> loadPublicTracks();

    @GET("tracks")
    Call<List<Track>> getMusic(@QueryMap Map<String, String> stringMap);


    @GET("tracks/{trackId}")
    Call<Track> loadSoundCloudTrack(@Path("trackId") int trackId);

    @GET("me/playlists")
    Call<List<Playlists>> getMyPlaylists();

    @PUT("me/favorites/{trackId}")
    Call<Track> toMyCollection(@Path("trackId") int trackId);

    @DELETE("me/favorites/{trackId}")
    Call<Track> deleteFromMyCollection(@Path("trackId") int trackId);

    @GET("me/favorites")
    Call<List<Track>> getMyColection();

    @FormUrlEncoded
    @POST("oauth2/token")
    Call<Token> authorize(@FieldMap Map<String, String> authMap);



//    http://api.soundcloud.com/tracks?genres=rock&client_id=YOUR_CLIENT_ID
//    http://api.soundcloud.com/tracks?genres=all-music&client_id=YOUR_CLIENT_ID
    /*requests for charts track*/
//    @GET("top")
//    Call<List<Track>> getMusic(@QueryMap Map<String, String> stringMap);

}
