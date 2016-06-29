package com.example.aidlclienttest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aidlservertest.MusicInterface;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_play;
    private Button bt_stop;
    private Button bt_pause;
    private Button bt_exit;
    private Button bt_close;
    MusicInterface inferface = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        bt_play = (Button) findViewById(R.id.bt_play);
        bt_stop = (Button) findViewById(R.id.bt_stop);
        bt_pause = (Button) findViewById(R.id.bt_pause);
        bt_exit = (Button) findViewById(R.id.bt_exit);
        bt_close = (Button) findViewById(R.id.bt_close);
        bt_play.setOnClickListener(this);
        bt_stop.setOnClickListener(this);
        bt_pause.setOnClickListener(this);
        bt_exit.setOnClickListener(this);
        bt_close.setOnClickListener(this);
        conn();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.bt_play:
                    inferface.play();//远程调用
                    break;
                case R.id.bt_pause:
                    inferface.pause();
                    break;
                case R.id.bt_stop:
                    inferface.stop();
                    break;
                case R.id.bt_close:
                case R.id.bt_exit:
                    finish();
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void conn() {
        Intent intent = new Intent("com.example.aidlservertest");
        intent.setPackage("com.example.aidlservertest");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            inferface = MusicInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            inferface = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            unbindService(conn);
        }
    }
}
