package com.janvinas.ApiRestTracks;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private void getSongs(){
        RecyclerView recyclerView = findViewById(R.id.track_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        TracksService tracksService = NetworkClient.getInstance().tracksService;
        tracksService.getTracks().enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(response.body());
                myAdapter.setActivityInstance(MainActivity.this);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Log.d("retrofit", "Failed to perform HTTP Request " + call.request().url());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSongs();
    }

    public void onCreateClick(View v){
        Track track = new Track();
        track.singer = ((TextView) findViewById(R.id.newsong_singer)).getText().toString();
        track.title = ((TextView) findViewById(R.id.newsong_title)).getText().toString();

        TracksService tracksService = NetworkClient.getInstance().tracksService;
        tracksService.createTrack(track).enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                getSongs();
            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error performing request!", Toast.LENGTH_LONG).show();
            }
        });
    }
}