package com.example.joke;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;

public class SongPlayer extends IntentService {
    private MediaPlayer player;

    public SongPlayer() {
        super("SongPlayer");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        player = MediaPlayer.create(getApplicationContext(), R.raw.song);
        player.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
        }
    }
}