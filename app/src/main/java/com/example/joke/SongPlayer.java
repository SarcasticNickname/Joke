package com.example.joke;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class SongPlayer extends IntentService {
    private MediaPlayer player;
    public static final String NOTE_CHANNEL_ID = "abcdefg";
    public static final int NOTE_ID = 12345;

    public SongPlayer() {
        super("SongPlayer");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        player = MediaPlayer.create(getApplicationContext(), R.raw.song);
        player.start();
        notifyOnMusicStarted();
    }

    private void notifyOnMusicStarted() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, NOTE_CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.sym_def_app_icon)
                        .setContentTitle(getString(R.string.music_notification_title))
                        .setContentText(getString(R.string.music_notification_text))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setVibrate(new long[] {0, 1000})
                        .setAutoCancel(true);
        Intent actionIntent = new Intent(this, MainActivity.class);
        PendingIntent actionPendingIntent = PendingIntent.getActivity(this,
                0,
                actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(actionPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("abcdefg", "Channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(NOTE_ID, builder.build());
    }
}