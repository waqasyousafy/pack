package com.code4rox.calculatorfox;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;

public class SongsDownloader extends Service {
    public String Tag = "SongService";
    public SongDownloadThread songDownloadThread;
    public static boolean flag=false;

    public SongsDownloader() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Tag, "onCreate called");
        songDownloadThread = new SongDownloadThread();
        songDownloadThread.start();
        while (songDownloadThread.songdownhandler == null) {

        }
        songDownloadThread.songdownhandler.setSongsDownloader(this);
        songDownloadThread.songdownhandler.setContext(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d(Tag, "On start command  " + Thread.currentThread().getName());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(Tag, "On start command  " + Thread.currentThread().getName());
                Message msg = Message.obtain();
//                 songDownloadThread.songdownhandler.setResultReceiver(intent.getParcelableExtra(Intent.EXTRA_RESULT_RECEIVER));
                msg.obj = intent.getStringExtra(Song_Downloader.MESSAGE_KEY);
                msg.arg1=startId;
                songDownloadThread.songdownhandler.sendMessage(msg);
            }
        }).start();

        return START_REDELIVER_INTENT;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Tag, "Service Stoped");

    }
}
