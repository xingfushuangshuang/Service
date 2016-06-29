package com.example.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_play, btn_stop, btn_exit, btn_pause, btn_finish;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                startMusic(1);
                break;
            case R.id.btn_stop:
                startMusic(3);
                break;
            case R.id.btn_pause:
                startMusic(2);
                break;
            case R.id.btn_finish:
                this.finish();
                break;
            case R.id.btn_exit:
                stopMusic();
                this.finish();
                break;
        }
    }

    public void startMusic(int command){
        Intent intent=new Intent(this,MusicService.class);
        intent.putExtra("command",command);
        startService(intent);
    }
    public void stopMusic(){
        Intent intent=new Intent(this,MusicService.class);
        stopService(intent);
    }
}
