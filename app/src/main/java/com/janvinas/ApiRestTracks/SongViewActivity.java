package com.janvinas.ApiRestTracks;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongViewActivity extends AppCompatActivity {

    String id;
    String title;
    String singer;

    TracksService tracksService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_song_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        singer = intent.getStringExtra("singer");

        ((TextView) findViewById(R.id.title)).setText(title);
        ((TextView) findViewById(R.id.singer)).setText(singer);

        tracksService = NetworkClient.getInstance().tracksService;

    }

    public void onBackClick(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onEditClick(View v){
        Track track = new Track();
        track.id = id;
        track.title = ((TextView) findViewById(R.id.title)).getText().toString();
        track.singer = ((TextView) findViewById(R.id.singer)).getText().toString();

        tracksService.updateTrack(track).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    Intent intent = new Intent(SongViewActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(SongViewActivity.this, "Error performing request!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SongViewActivity.this, "Error performing request!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onDeleteClick(View v){
        tracksService.deleteTrack(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    Intent intent = new Intent(SongViewActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(SongViewActivity.this, "Error performing request!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SongViewActivity.this, "Error performing request!", Toast.LENGTH_LONG).show();
            }
        });
    }
}