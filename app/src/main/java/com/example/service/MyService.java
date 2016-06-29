package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class MyService extends Service {
    //使用MediaPlayer进行媒体播放
    private MediaPlayer mediaPlayer;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.hckz);
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MusicService:", "onStartCommand");

        int operate = -1;
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            operate = bundle.getInt("operate");
            switch (operate) {
                case 1:
                    play();
                    break;
                case 2:
                    stop();
                    break;
                case 3:
                    pause();
                    break;
                default:
                    break;
            }
        }
        return operate;
    }

    @Override
    public void onDestroy() {
        Log.i("MusicService:", "onDestroy");

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    ///////////////////////////////////////

    /**
     * 播放
     */
    public void play() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    /**
     * 停止
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                //在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
                mediaPlayer.prepare();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
