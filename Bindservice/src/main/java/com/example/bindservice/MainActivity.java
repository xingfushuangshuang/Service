package com.example.bindservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_play, btn_stop, btn_exit, btn_pause, btn_finish;
    private MyService servcie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }


    public void initView() {
        btn_play = (Button) findViewById(R.id.btn_play);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        btn_play.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        conn();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (servcie != null) {
                    servcie.play();
                }
                break;
            case R.id.btn_pause:
                if (servcie != null) {
                    servcie.pause();
                }
                break;
            case R.id.btn_stop:
                if (servcie != null) {
                    servcie.stop();
                }
                break;
            case R.id.btn_exit:
                finish();
                break;
            case R.id.btn_finish:
                finish();
                break;
        }
    }

    public void conn() {
        Intent intent = new Intent("com.example.myservice");
        intent.setPackage("com.example.bindservice");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection conn = new ServiceConnection() {
        //连接建立成功
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            servcie = ((MyService.MyBind) ((Binder) service)).getService();
        }

        //连接断开
        @Override
        public void onServiceDisconnected(ComponentName name) {
            servcie = null;
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
