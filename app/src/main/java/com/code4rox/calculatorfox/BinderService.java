package com.code4rox.calculatorfox;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BinderService extends Service {
    @Nullable
    public static final String MUSIC_COMPLETE = "MUSIC COMPLETED";
    public static final String MUSIC_PAUSE="MUSIC PAUSE";
    public static final String MUSIC_PLAY="MUSIC PLAY";
    public static String SONG_NAME="SONG NAME";
    public static final String MESSAGE_KEY = "MESSAGE";
    public final static String Tag = "Music player";
    public static final String ACTION_PAUSE = "com.code4rox.calculatorfox.BinderService.pause";
    public static final String ACTION_PLAY = "com.code4rox.calculatorfox.BinderService.play";
    public final Binder mbinder = new Servicebinder();
    public NotificationCompat.Builder builder1;
    public NotificationManager mNotificationManager;
    public static final int FOREGROUND_SERVICE = 101;
    public boolean isplay=false;
    public Intent intent;
    public static  String song_status=null;
    RemoteViews views;
    private MediaPlayer mediaPlayer;
    private Object status;
    public MusicPlayer musicPlayer=new MusicPlayer();
    public IBinder onBind(Intent intent) {
        Log.d(Tag, "On Bind");
        return mbinder;
    }

    public void onCreate() {

        Log.d(Tag, "On Create");
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.abc);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(MUSIC_COMPLETE);
                intent.putExtra(MESSAGE_KEY, "Complete");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                stopSelf();
                stopForeground(true);
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            showNotification();
        }
        updateNotification();
        if(intent.getAction()==ACTION_PLAY){
            if(isplay){
                isplay=false;
                intent=new Intent(MUSIC_PAUSE);
                intent.putExtra(MESSAGE_KEY,"pause");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
//                musicPlayer.textview.setText(musicPlayer.songname);
                updateNotification();
                pause();
            }else{

                isplay= true;
                intent=new Intent(MUSIC_PLAY);
                intent.putExtra(MESSAGE_KEY,"play");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
//                musicPlayer.textview.setText(musicPlayer.songname);
                play();
                updateNotification();
            }
        }

        return START_REDELIVER_INTENT;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotification() {
// Using RemoteViews to bind custom layouts into Notification
        mNotificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        views = new RemoteViews(getPackageName(),
                R.layout.status_bar);
        Intent playIntent = new Intent(this, BinderService.class);
        playIntent.putExtra(MESSAGE_KEY,"play");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(playIntent);
        playIntent.setAction(ACTION_PLAY);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);
        builder1 = new NotificationCompat.Builder(this, "Channel1")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Song Notification")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCustomContentView(views)
                .setAutoCancel(true)
                .setColorized(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationManager builder = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
//            NotificationChannel mChannel = new NotificationChannel("Channel1", "chanel_1", builder.IMPORTANCE_DEFAULT);
//        }
        startForeground(FOREGROUND_SERVICE, builder1.build());


    }

    public void updateNotification() {
        // update the icon

        if (isplay) {
            views.setImageViewResource(R.id.status_bar_play, R.drawable.apollo_holo_dark_pause);
            // update the notification
            mNotificationManager.notify(FOREGROUND_SERVICE, builder1.build());
        }
        else{
            views.setImageViewResource(R.id.status_bar_play, R.drawable.apollo_holo_dark_play);
            // update the notification
            mNotificationManager.notify(FOREGROUND_SERVICE, builder1.build());

        }
    }

    @Override
    public void onDestroy() {
        Log.d(Tag, "On Destroy");
        super.onDestroy();
        mediaPlayer.release();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(Tag, "On Unbind");
        return true;
    }

    //public client methods
    public boolean isplaying() {
        return mediaPlayer.isPlaying();
    }

    public void play() {
        mediaPlayer.start();
        isplay=true;
    }

    public int currentpositoin() {
        return mediaPlayer.getCurrentPosition();
    }

    public void pause() {
        mediaPlayer.pause();

    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        musicPlayer=new MusicPlayer();
        if(isplay)
        {
            musicPlayer.btn_music_action.setBackgroundResource(R.drawable.play);
            Intent SONG_INTENT=new Intent(SONG_NAME);
            SONG_INTENT.putExtra(MESSAGE_KEY,"songname");
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(SONG_INTENT);
        }
        Log.d(Tag, "Rebind");
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public class Servicebinder extends Binder {
        public BinderService getService() {
            return BinderService.this;
        }

    }
}
