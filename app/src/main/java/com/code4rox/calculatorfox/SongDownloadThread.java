package com.code4rox.calculatorfox;

import android.os.Looper;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class SongDownloadThread extends Thread {
    public SongDownloaderHandler songdownhandler;


    public SongDownloadThread(){

    }
    @Override
    public void run(){
        Looper.prepare();
        songdownhandler=new SongDownloaderHandler();
        Looper.loop();

    }
}
