package com.code4rox.calculatorfox;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class MyIntentService extends IntentService {
public String Tag="songservice";

    public MyIntentService() {
        super("MyIntentService");
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String songname=intent.getStringExtra(Song_Downloader.MESSAGE_KEY);
        downloadsong(songname);
    }
    public void downloadsong(final String songName) {
        Log.d(Tag, " " + songName + " Downloading");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(Tag, " " + songName + " Song Downloaded");
    }

}
