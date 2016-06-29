package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {
    private MediaPlayer player = null;

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.hckz);
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //操作指令
        int command = intent.getIntExtra("command", 0);
        switch (command) {
            case 1://播放
                play();
                break;
            case 2://暂停
                pause();
                break;
            case 3://停止
                stop();
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void play() {
        if (player != null && !player.isPlaying()) {
            player.start();
        }
    }

    public void pause() {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    public void stop() {
        if (player != null) {
            player.stop();
            try {
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.stop();
            player.release();//释放资源
        }
        super.onDestroy();

    }
}
