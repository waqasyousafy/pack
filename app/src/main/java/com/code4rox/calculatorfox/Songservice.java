package com.code4rox.calculatorfox;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;

public class Songservice extends Service {
    public static final String TAG = "MyTag";
    private DownloadThread mdownloadthread;

    public Songservice() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate called");
        mdownloadthread = new DownloadThread();
        mdownloadthread.start();
        while (mdownloadthread.mHandler == null) {

        }
        mdownloadthread.mHandler.setSongservice(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "On start command" + Thread.currentThread().getName());
        String songname = intent.getStringExtra(MainActivity.MESSAGE_KEY);
        mdownloadthread.mHandler.setResultReceiver((ResultReceiver) intent.getParcelableExtra(Intent.EXTRA_RESULT_RECEIVER));
        Message message = Message.obtain();
        message.obj = songname;
        message.arg1 = startId;
        mdownloadthread.mHandler.sendMessage(message);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service Stoped");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    private void downloadsong(final String songName) {

        Log.d(TAG, "Run Starting Download");
        try {
            Thread.sleep(4000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "downloaded song " + songName + "Downloaded");
    }
}
