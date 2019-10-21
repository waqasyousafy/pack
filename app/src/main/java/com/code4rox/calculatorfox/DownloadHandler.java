package com.code4rox.calculatorfox;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;

public class DownloadHandler extends Handler {
    private static final String TAG = "MyTag";
    private Songservice songservice;
private ResultReceiver mResultReceiver;
    @Override
    public void handleMessage(Message msg) {

        downloadSong(msg.obj.toString());
        Log.d("MyTag","value of startid"+msg.arg1);
        songservice.stopSelf(msg.arg1);
        Bundle bundle=new Bundle();
        bundle.putString(MainActivity.MESSAGE_KEY,msg.obj.toString());
        mResultReceiver.send(MainActivity.RESULT_OK,bundle);

    }

    private void downloadSong(final String songName) {
        Log.d(TAG, "run: staring download");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "downloadSong: " + songName + " Downloaded...");
    }

    public void setSongservice(Songservice songservice) {

        this.songservice=songservice;
    }
    public void setResultReceiver(ResultReceiver mresultReceiver) {

        this.mResultReceiver=mresultReceiver;
    }
}
