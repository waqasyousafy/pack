package com.code4rox.calculatorfox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class SongDownloaderHandler extends Handler {

    public SongsDownloader songsDownloader;
    public String Tag = "SongService";
    public final static String ServiceMessage="Service Message";
    private Context mContext;
//    public ResultReceiver resultReceiver;

    public SongDownloaderHandler() {
        super();
    }

    public void handleMessage(Message msg) {

        downloadsong(msg.obj.toString());
//        Bundle bundle = new Bundle();
//        bundle.putString(Song_Downloader.MESSAGE_KEY, msg.obj.toString());
//        resultReceiver.send(Song_Downloader.RESULT_OK,bundle);
        songsDownloader.stopSelf(msg.arg1);
        sendMessageToUi(msg.obj.toString());
    }

    private void sendMessageToUi(String song) {
        Intent intent = new Intent(ServiceMessage);
        intent.putExtra(Song_Downloader.MESSAGE_KEY,song);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
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

    public void setSongsDownloader(SongsDownloader songsDownloader) {
        this.songsDownloader = songsDownloader;
    }
//    public void  setResultReceiver(ResultReceiver resultReceiver)
//    {
//        this.resultReceiver=resultReceiver;
//    }
    public void setContext(Context context){
        this.mContext=context;
    }


}
