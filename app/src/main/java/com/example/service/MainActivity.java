package com.example.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_play;
    private Button bt_stop;
    private Button bt_pause;
    private Button bt_exit;
    private Button bt_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init(){
        bt_play=(Button)findViewById(R.id.bt_play);
        bt_stop=(Button)findViewById(R.id.bt_stop);
        bt_pause=(Button)findViewById(R.id.bt_pause);
        bt_exit=(Button)findViewById(R.id.bt_exit);
        bt_close=(Button)findViewById(R.id.bt_close);
        bt_play.setOnClickListener(this);
        bt_stop.setOnClickListener(this);
        bt_pause.setOnClickListener(this);
        bt_exit.setOnClickListener(this);
        bt_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        int operate=-1;//定义操作的代码
        switch (arg0.getId()) {
            case R.id.bt_play:
                operate=1;
                startS(operate);
                break;
            case R.id.bt_stop:
                operate=2;
                startS(operate);
                break;
            case R.id.bt_pause:
                operate=3;
                startS(operate);
                break;
            case R.id.bt_exit:
                //停止服务
                stopS(operate);
                this.finish();
                break;
            case R.id.bt_close:
                this.finish();
                break;
            default:
                break;
        }
    }

    private void startS(int operate){
        //启动服务，并把操作的代码operate传给服务
        Intent intent = new Intent(this, MyService.class);
        Bundle bundle  =new Bundle();
        bundle.putInt("operate", operate);
        intent.putExtras(bundle);
        startService(intent);
    }

    private void stopS(int operate){
        //停止服务
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }
}
