package com.example.aidlservertest;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;

import java.io.IOException;

public class MyService extends Service {
    private MediaPlayer player = null;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    MusicInterface.Stub binder = new MusicInterface.Stub() {
        @Override
        public void play() throws RemoteException {
            if (player == null) {
                player = MediaPlayer.create(MyService.this, R.raw.lemonree);
            }
            if (player != null && !player.isPlaying()) {
                player.start();
            }
        }

        @Override
        public void pause() throws RemoteException {
            if (player != null && player.isPlaying()) {
                player.pause();
            }
        }

        @Override
        public void stop() throws RemoteException {
            if (player != null && player.isPlaying()) {
                player.stop();
                try {
                    player.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player.release();
        }
    }
}
