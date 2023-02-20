package com.example.evacunation;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;


public class SoundService extends Service {
    private MediaPlayer mediaPlayer;
    private NotificationManager notificationManager;
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "SOS Sound Notification";

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "SOS Sound Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer == null) {
            ((AudioManager) getSystemService(Context.AUDIO_SERVICE)).setStreamVolume(3, 20, 0);
            mediaPlayer = MediaPlayer.create(this, R.raw.sos_sound);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            showNotification();
        }

        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case "stop":
                    stopMedia();
                    break;
            }
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private void showNotification() {
        Intent stopIntent = new Intent(this, SoundService.class);
        stopIntent.setAction("stop");
        PendingIntent stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.evaclogo_2)
                .setContentTitle("Playing SOS Sound")
                .setContentText("EvacuNation SOS Sound")
                .addAction(R.drawable.stop, "Stop", stopPendingIntent);

        startForeground(NOTIFICATION_ID, builder.build());
    }

    private void stopMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        stopForeground(true);
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
