package com.example.bindmusic;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bt_play;
    private Button bt_stop;
    private Button bt_pause;
    private Button bt_exit;

    private MusicService musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();



        connection();
    }

    private void init(){
        bt_play=(Button)findViewById(R.id.bt_play);
        bt_stop=(Button)findViewById(R.id.bt_stop);
        bt_pause=(Button)findViewById(R.id.bt_pause);
        bt_exit=(Button)findViewById(R.id.bt_exit);
        bt_play.setOnClickListener(this);
        bt_stop.setOnClickListener(this);
        bt_pause.setOnClickListener(this);
        bt_exit.setOnClickListener(this);
    }

    /**
     * 6、Activity的onCreate方法里绑定musicService
     */
    private void connection() {
        //6-1、使用服务绑定标示实例化Intent对象
        Intent intent = new Intent(MusicService.ACTION);
        intent.setPackage("com.example.bindmusic");
        //6-2、使用bindService(Intent对象,ServiceConnection接口对象,标志位)绑定musicService。
        /**
         * 注：标志位Context.BIND_AUTO_CREATE，
         * 说明：表示收到绑定请求的时候，如果服务尚未创建，则即刻创建，
         * 在系统内存不足需要先摧毁优先级组件来释放内存，
         * 且只有驻留该服务的进程成为被摧毁对象时，服务才被摧毁
         */
        bindService(intent, conn, Context.BIND_AUTO_CREATE);// bindService

    }

    /**
     * 5、Activity里定义ServiceConnection接口对象并实现ServiceConnection接口，
     * 重写onServiceConnected方法进行Service连接
     * 重写onServiceDisconnected方法进行Service销毁
     */
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {//connect Service
            musicService = ((MusicService.MyBinder)(service)).getService();
            if (musicService != null) {
                musicService.play();// play music
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {//disconnect Service
            musicService = null;
        }
    };

    @Override
    public void onClick(View arg0) {
        //7、使用musicService
        switch (arg0.getId()) {
            case R.id.bt_play:
                musicService.play();
                break;
            case R.id.bt_stop:
                musicService.stop();
                break;
            case R.id.bt_pause:
                musicService.pause();
                break;
            case R.id.bt_exit:
                this.finish();
                break;
            default:
                break;
        }
    }

    /**
     * 8、Activity的onDestroy方法里使用unbindService解除服务绑定
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止服务
        if(conn != null){
            unbindService(conn);
        }
    }
}
