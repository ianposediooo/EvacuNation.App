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
    private AudioManager audioManager;
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "SOS Sound Notification";

    // Variables to save and restore original audio state
    private int originalAudioMode = AudioManager.MODE_NORMAL;
    private boolean originalSpeakerphoneState = false;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

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

            // 1. SAVE original state
            originalAudioMode = audioManager.getMode();
            originalSpeakerphoneState = audioManager.isSpeakerphoneOn();

            // 2. FORCE AUDIO ROUTING (Most Aggressive)
            // Use the highest priority audio mode: MODE_IN_CALL.
            audioManager.setMode(AudioManager.MODE_IN_CALL);

            // Explicitly route this high-priority audio to the internal speaker.
            audioManager.setSpeakerphoneOn(true);

            // 3. Set the stream to maximum volume
            // Use STREAM_VOICE_CALL, which is the stream used for MODE_IN_CALL.
            final int streamType = AudioManager.STREAM_VOICE_CALL;

            int maxVolume = audioManager.getStreamMaxVolume(streamType);
            audioManager.setStreamVolume(streamType, maxVolume, 0);

            // 4. Initialize MediaPlayer with the highest priority stream type
            mediaPlayer = new MediaPlayer();
            try {
                // Set stream type BEFORE setting data source (deprecated but useful)
                mediaPlayer.setAudioStreamType(streamType);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mediaPlayer.setDataSource(getResources().openRawResourceFd(R.raw.sos_sound));
                }
                mediaPlayer.prepare(); // Use prepare() for resource file descriptor
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (mediaPlayer != null) {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }

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

        // 5. RESTORE original audio settings
        restoreAudioSettings();

        notificationManager.cancel(NOTIFICATION_ID);
    }

    private void restoreAudioSettings() {
        if (audioManager != null) {
            // Restore original states
            audioManager.setSpeakerphoneOn(originalSpeakerphoneState);
            audioManager.setMode(originalAudioMode);
        }
    }

    private void showNotification() {
        // ... (PendingIntent fix from previous steps is retained) ...
        Intent stopIntent = new Intent(this, SoundService.class);
        stopIntent.setAction("stop");

        int pendingIntentFlags;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntentFlags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        } else {
            pendingIntentFlags = PendingIntent.FLAG_UPDATE_CURRENT;
        }

        PendingIntent stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, pendingIntentFlags);

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

        // RESTORE audio settings
        restoreAudioSettings();

        stopForeground(true);
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}