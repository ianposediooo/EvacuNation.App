package com.example.evacunation;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

/* loaded from: classes.dex */
public class SoundService extends Service {
    MediaPlayer player;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        ((AudioManager) getSystemService(Context.AUDIO_SERVICE)).setStreamVolume(3, 20, 0);
        MediaPlayer create = MediaPlayer.create(this, (int) R.raw.sossound);
        this.player = create;
        create.setLooping(true);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        player.start();
        return Service.START_NOT_STICKY;
    }

    @Override // android.app.Service
    public void onDestroy() {
        player.stop();
        player.release();
        stopSelf();
        super.onDestroy();
    }
}
