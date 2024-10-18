package com.janvinas.ApiRestTracks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TracksService {
    @GET("tracks")
    Call<List<Track>> getTracks();

    @POST("tracks")
    Call<Track> createTrack(@Body Track track);

    @PUT("tracks")
    Call<Void> updateTrack(@Body Track track);

    @GET("tracks/{trackId}")
    Call<Track> getTrack(@Path("trackId") String trackId);

    @DELETE("tracks/{trackId}")
    Call<Void> deleteTrack(@Path("trackId") String trackId);


}
