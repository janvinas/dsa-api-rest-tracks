package com.janvinas.ApiRestTracks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    private static NetworkClient instance = null;

    public Retrofit retrofit;
    public TracksService tracksService;

    private NetworkClient(){
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://localhost:8080/dsaApp/")
                .build();
        tracksService = retrofit.create(TracksService.class);
    };

    public static NetworkClient getInstance(){
        if(instance != null) return instance;
        return new NetworkClient();
    }
}
