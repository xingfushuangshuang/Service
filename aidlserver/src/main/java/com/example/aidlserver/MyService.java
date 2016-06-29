package com.example.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {
    private MediaPlayer player;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }


    MusicInterface.Stub stub = new MusicInterface.Stub() {
        @Override
        public void play() throws RemoteException {
            player = MediaPlayer.create(MyService.this, R.raw.hckz);
            player.start();
        }

        @Override
        public void pause() throws RemoteException {

        }

        @Override
        public void stop() throws RemoteException {

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
