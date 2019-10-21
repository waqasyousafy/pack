package com.code4rox.calculatorfox;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static com.code4rox.calculatorfox.BinderService.MUSIC_PAUSE;

public class MusicPlayer extends AppCompatActivity {
    //Component Declaration
    public static Button btn_music_action;
    public String AsyncTag="Task";
    public  TextView textview;
    public final static String Tag = "Music player";
    public BinderService musicplayer;
    private static final String MESSAGE_KEY="MESSAGE";
    private boolean mBound = false;
    public static int id=R.raw.abc;
    public  String songname;
    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BinderService.Servicebinder mservicebinder =
                    (BinderService.Servicebinder) service;
            musicplayer = mservicebinder.getService();
            mBound = true;
            Log.d(Tag, "Service Connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result=intent.getStringExtra(MESSAGE_KEY);
            if(result=="Complete"){
                btn_music_action.setBackgroundResource(R.drawable.pause);
            }
        }

    };
    public BroadcastReceiver pause_broadcastreceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result=intent.getStringExtra(MESSAGE_KEY);
            if(result=="pause"){
                btn_music_action.setBackgroundResource(R.drawable.pause);
            }
        }
    };
    public BroadcastReceiver play_broadcastreceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result=intent.getStringExtra(MESSAGE_KEY);
            if(result=="play"){
                btn_music_action.setBackgroundResource(R.drawable.play);
            }
        }
    };
    public BroadcastReceiver songname_broadcastreceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result=intent.getStringExtra(MESSAGE_KEY);
            if(result=="songname"){
                textview.setText(songname);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag,"onCreate");
        ActionBar abr = getSupportActionBar();
        abr.setTitle("Music Player");
        songname=this.getResources().getResourceEntryName(id);
        setContentView(R.layout.activity_music_player);
        btn_music_action=(Button) findViewById(R.id.btn_music_action);
        textview=(TextView) findViewById(R.id.Current_Song_Name);
        textview.setText(songname);
        btn_music_action.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(mBound){
                    textview.setText(songname);
                    if(musicplayer.isplaying()){
                        musicplayer.pause();
                        btn_music_action.setBackgroundResource(R.drawable.pause);
                        musicplayer.isplay=false;
                        musicplayer.updateNotification();
                    }
                    else{
                        Intent intent=new Intent(MusicPlayer.this,BinderService.class);
                                startService(intent);

                        musicplayer.play();
                        textview.setText(songname);
                        btn_music_action.setBackgroundResource(R.drawable.play);

                    }
                }
            }
        });
        new longrunningtask().execute();
    }

    @Override
    protected void onStart() {
        Log.d(Tag,"On Start");
        super.onStart();
        Log.d(Tag, "On start");
        Intent intent = new Intent(MusicPlayer.this, BinderService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver,new IntentFilter(BinderService.MUSIC_COMPLETE));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(pause_broadcastreceiver,new IntentFilter(MUSIC_PAUSE));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(play_broadcastreceiver,new IntentFilter(BinderService.MUSIC_PLAY));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(play_broadcastreceiver,new IntentFilter(BinderService.SONG_NAME));
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(mBound){
            unbindService(serviceConnection);
            mBound=false;
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);
    }
    public class longrunningtask extends AsyncTask<Void,Void,Void>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(AsyncTag,"Pre Execute");
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Log.d(AsyncTag,"do in background");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(AsyncTag,"Post Execute");
        }

    }
}
