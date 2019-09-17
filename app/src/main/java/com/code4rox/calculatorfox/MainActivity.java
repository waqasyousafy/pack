package com.code4rox.calculatorfox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    TextView tv;
    Button btn_counter,btn_reset,btn_cal,btn_recyclerview ;
    int counter=0;
    String TAG="Testing";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         tv=(TextView) findViewById(R.id.counter_label);
         btn_counter=(Button) findViewById(R.id.counter_button);
         btn_reset=(Button) findViewById(R.id.reset_button);
        /* btn_cal=(Button) findViewById(R.id.cal_activity_btn);
        btn_recyclerview=(Button) findViewById(R.id.rec_view_activity_btn);*/
         btn_counter.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 counter++;
                 tv.setText("Your Counter is : "+counter);

             }
         });
         btn_reset.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 counter=0;
                 tv.setText("Your Counter is : "+ counter);

             }
         });



    }


}
