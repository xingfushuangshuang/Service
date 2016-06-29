package com.example.bindmusic;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


public class MusicService extends Service {
	/**定义绑定标示*/
	public static final String ACTION = "com.zdsoft.bindService";

	/**2、定义IBinder接口的对象*/
	private final IBinder binder = new MyBinder();

	//使用MediaPlayer进行媒体播放
	private MediaPlayer mediaPlayer;

	/**
	 *1、定义内部类MyBinder继承Binder实现IBinder接口，并提供方法返回Service实例。
	 */
	public class MyBinder extends Binder {
		/**
		 * 获取 Service实例
		 * @return
		 */
		public MusicService getService() {
			return MusicService.this;
		}
	}

	/**
	 * 3、实现 Service的抽象方法onBind，并返回一个实现 IBinder接口的对象
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}

	@Override
	public void onCreate() {
		Log.i("MusicService:", "onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("MusicService:", "onStartCommand");

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.i("MusicService:", "onDestroy");

		if(mediaPlayer !=null){
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}

	///////////////////4、提供媒体播放的方法////////////////////
	/**
	 * 播放
	 */
	public void play() {
		if(mediaPlayer ==null){
			mediaPlayer = MediaPlayer.create(this, R.raw.hckz);
			mediaPlayer.setLooping(false);
		}
		if(!mediaPlayer.isPlaying()) {
			mediaPlayer.start();
		}
	}

	/**
	 * 暂停
	 */
	public void pause(){
		if(mediaPlayer !=null&& mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
	}

	/**
	 * 停止
	 */
	public void stop() {
		if(mediaPlayer !=null) {
			mediaPlayer.stop();
			try{
				//在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
				mediaPlayer.prepare();
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
