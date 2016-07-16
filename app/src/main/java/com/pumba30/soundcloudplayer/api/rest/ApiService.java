package com.pumba30.soundcloudplayer.api.rest;

import com.pumba30.soundcloudplayer.api.models.Token;
import com.pumba30.soundcloudplayer.api.models.Track;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by pumba30 on 28.06.2016.
 */
public interface ApiService {

    @GET("tracks")
    Call<List<Track>> loadPublicTracks();

    @GET("tracks/{trackId}")
    Call<Track> loadSoundCloudTrack(@Path("trackId") int trackId);

    @FormUrlEncoded
    @POST("oauth2/token") Call<Token> authorize(@FieldMap Map<String, String> authMap);

}
