package com.code4rox.calculatorfox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread td=new Thread(){
          public void run(){
              try {
                  sleep(2000);

              }catch (Exception ex){
                  ex.printStackTrace();

              }finally {
                  Intent it=new Intent(Splash_screen.this,menuactivity.class);
                  startActivity(it);
                  finish();
              }
          }
        };td.start();

        }
}
