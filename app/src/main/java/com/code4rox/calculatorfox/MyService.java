package com.code4rox.calculatorfox;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class MyService extends Service {
    private int randomnumber;
    private boolean israndomnumbergeneratoron;
    private int min = 0;
    private int max = 100;
//    private MainActivity mainActivity;
    //IBinder instance of myservicebinder class type that implement the ibinder to the service for returning the ibinder and service to the activity
    private IBinder mbinder = new myservicebinder();

    public void startrandomnumbergenerator() {
        while (israndomnumbergeneratoron) {
            try {
                Thread.sleep(1000);
                if (israndomnumbergeneratoron) {
                    randomnumber = new Random().nextInt(max) + min;
                    Log.i("service", "Thread id:" + Thread.currentThread().getId() + "Random Number: " + randomnumber);
                }

            } catch (InterruptedException e) {
                Log.i("service", "Thread intrupted");
            }

        }

    }

    private void stoprandomnumbergenerator() {
        israndomnumbergeneratoron = false;
    }

    public int getRandomnumber() {
        return randomnumber;
    }

    @Nullable
    @Override
    //OnBind method that a return the object of ibinder to activity for binding with the activity.
    public IBinder onBind(Intent intent) {
        Log.i("service", "in onbind");
        return mbinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stoprandomnumbergenerator();
        Log.i("Service", "Service Destroyed");
    }

    @Override
    //For start the service this function triggered
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("service", "In on start command ,thread id:" + Thread.currentThread().getId());
        israndomnumbergeneratoron = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                startrandomnumbergenerator();
            }
        }).start();
        //Use if the service stopped due some of resources shortage it will continue if the resources available without any intent . we use the "START_STICKY"
        return START_STICKY;
    }

    //Class that helps to implements the Ibinder interface
    class myservicebinder extends Binder {
        //Returning the service to the activity for using the service at the time of connecting to service.
        public MyService getservice() {
            return MyService.this;
        }
    }
}
