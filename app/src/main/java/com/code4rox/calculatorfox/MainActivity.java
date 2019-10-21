package com.code4rox.calculatorfox;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static String[] songs={"Dil Dil Pakistan","Afreen Afreen","Jazba-e-Janoon"};
    public static final String MESSAGE_KEY="message key";
    TextView tv, randomnumber;
    Button btn_counter, btn_reset, btn_cal, btn_recyclerview, btn_start_service, btn_stop_service, btn_randomnumber;
    int counter = 0;
    String TAG = "MyTag";
    private Intent serviceintent;   //Intent to pass the service to the activity
    private MyService myService;    //MyService instance that is use for using its public methods here in the activity.
    private boolean isservicebound;     //A flag veriable that is used to for check for bound or unbound
    private ServiceConnection serviceConnection;    //ServiceConnection Instance for connecting the activity to the service

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.counter_label);
        btn_counter = (Button) findViewById(R.id.counter_button);
        btn_reset = (Button) findViewById(R.id.reset_button);
        btn_start_service = (Button) findViewById(R.id.btn_start_service);
        btn_stop_service = (Button) findViewById(R.id.btn_stop_service);
        btn_randomnumber = (Button) findViewById(R.id.btn_randomnumber);
        randomnumber = (TextView) findViewById(R.id.text_random_number);
        serviceintent = new Intent(MainActivity.this, Songservice.class);
        Log.i("Service", "MainActivity thread id : " + Thread.currentThread().getId());
        /* btn_cal=(Button) findViewById(R.id.cal_activity_btn);
        btn_recyclerview=(Button) findViewById(R.id.rec_view_activity_btn);*/
        btn_counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                tv.setText("Your Counter is : " + counter);

            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 0;
                tv.setText("Your Counter is : " + counter);

            }
        });
        btn_start_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //For Starting the Service use the startService and intent given for reference to start the specific service
//                startService(serviceintent);
                runCode();
//                bindservice();  //For bind the service at the time of start .
            }
        });
        btn_stop_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(serviceintent);     //For Stop the service
//                unbindservice();        //Also use for unbound the service.
            }
        });
        btn_randomnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetRandomNumber();
            }
        });


    }

    //For making connection to service implementing the two method of connections. And using the objectof the ibinder implementing class for using its getservice() method.
    private void bindservice() {
        if (serviceConnection == null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    MyService.myservicebinder myservicebinder = (MyService.myservicebinder) service;
                    myService = myservicebinder.getservice();
                    isservicebound = true;
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    isservicebound = false;

                }
            };
        }
        //Default method for binding the activity to service with parameters.
        bindService(serviceintent, serviceConnection, getApplicationContext().BIND_AUTO_CREATE);
    }
//For unbind the service
    private void unbindservice() {
        if (isservicebound) {
            //Using the default method for unbinding the service
            unbindService(serviceConnection);
            isservicebound = false;
        }
    }

    private void SetRandomNumber() {
        if (isservicebound) {
            randomnumber.setText("Random number: " + myService.getRandomnumber());
        } else {
            randomnumber.setText("Service not bound");
        }
    }

    public void runCode(){
        Log.i("My Tag","running code");
//        displayProgressBar(true);
        SongserviceResultReceiver resultReceiver=new SongserviceResultReceiver(null);
        for(String song:songs){
            Intent intent=new Intent(MainActivity.this,Songservice.class);
            intent.putExtra(MESSAGE_KEY,song);
            intent.putExtra(Intent.EXTRA_RESULT_RECEIVER,resultReceiver);
            startService(intent);
        }
    }

    private void displayProgressBar(boolean b) {
    }
    public class SongserviceResultReceiver extends ResultReceiver {
        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public SongserviceResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode,Bundle resultData) {
            super.onReceiveResult(resultCode,resultData);
            if(resultCode==RESULT_OK && resultData!=null){
                Log.d(TAG,"on Receive Result: Thread name:"+Thread.currentThread().getName());
                 String songname=resultData.getString(MESSAGE_KEY);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        randomnumber.setText(songname);
                    }
                });
            }
        }


    }

}
