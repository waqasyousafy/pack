package com.code4rox.calculatorfox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.code4rox.calculatorfox.MainActivity.MESSAGE_KEY;

public class Song_Downloader extends AppCompatActivity {
    public String  Tag="song service";
    public String[] Songs= {"Dill Dill Pakistan", "Afreen Afreen","Dil Diya Gllan"};
    public static  String MESSAGE_KEY="message";
public ImageView img;
public TextView txview1,txview2;
public Button btn_download,btn_stop_download_service;
public Intent songserviceintent;
public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song__downloader);
        img=(ImageView) findViewById(R.id.song_image);
        txview1=(TextView) findViewById(R.id.banner_title);
        txview2=(TextView) findViewById(R.id.download_status);
        btn_download=(Button) findViewById(R.id.btn_download);
        btn_stop_download_service=(Button) findViewById(R.id.btn_stop_download_service);
        songserviceintent=new Intent(Song_Downloader.this,SongsDownloader.class);
        handler=new Handler();
    btn_download.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runCode();
        }
    });
        btn_stop_download_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(songserviceintent);
            }
        });


    }
    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String songname=intent.getStringExtra(MESSAGE_KEY);
            txview2.setText(songname);
            Log.d(Tag,songname+"Downloaded");
            Log.d(Tag,Thread.currentThread().getName());
        }
    };
    private void runCode(){
        Log.i(Tag,"running code");
//        SongDownloaderResultRecieiver resultRecieiver=new SongDownloaderResultRecieiver(null);
        for(String song:Songs){
            Intent intent=new Intent(Song_Downloader.this,SongsDownloader.class);
            intent.putExtra(MESSAGE_KEY,song);
//            intent.putExtra(Intent.EXTRA_RESULT_RECEIVER,resultRecieiver);
            startService(intent);
        }


    }
    @Override
    protected  void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver,new IntentFilter(SongDownloaderHandler.ServiceMessage));

    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);
    }
    //    public class SongDownloaderResultRecieiver extends ResultReceiver{
//
//        /**
//         * Create a new ResultReceive to receive results.  Your
//         * {@link #onReceiveResult} method will be called from the thread running
//         * <var>handler</var> if given, or from an arbitrary thread if null.
//         *
//         * @param handler
//         */
//        public SongDownloaderResultRecieiver(Handler handler) {
//            super(handler);
//        }
//
//        @Override
//        protected void onReceiveResult(int resultCode, Bundle resultData) {
//            super.onReceiveResult(resultCode, resultData);
//            if(resultCode==RESULT_OK && resultData!=null){
//                String songname=resultData.getString(MESSAGE_KEY);
////                Song_Downloader.this.runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                            txview2.setText(songname+"Downloading");
////                    }
////                });
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        txview2.setText(songname+"Downloading");
//                    }
//                });
//
//            }
//
//        }
//    }

}
