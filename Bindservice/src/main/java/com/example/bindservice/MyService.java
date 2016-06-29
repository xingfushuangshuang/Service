package com.example.bindservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class MyService extends Service {
    private MyBind binder = new MyBind();
    private MediaPlayer player = null;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;

    }

    public class MyBind extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    public void play() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.hckz);
        }
        if (player != null && !player.isPlaying()) {
            player.start();
        }
    }

    public void stop() {
        if (player != null && player.isPlaying()) {
            player.stop();
            try {
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player!=null){
            player.stop();
            player.release();
        }
    }
}
