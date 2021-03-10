package com.ics.hunar.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.ics.hunar.R;

public class Youtube_Activity extends YouTubeBaseActivity
{
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        youTubePlayerView = findViewById(R.id.player);

        youTubePlayerView.initialize("AIzaSyB-UWlnAyNBLzgNEktI4SDxSySyeAixUVM", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo("fhWaJi1Hsfo");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
                Toast.makeText(Youtube_Activity.this, error, Toast.LENGTH_LONG).show();
            }
        });


    }
}
