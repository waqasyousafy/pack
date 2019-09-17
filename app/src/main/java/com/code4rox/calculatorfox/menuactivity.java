package com.code4rox.calculatorfox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menuactivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuactivity);
        Button btn_counter=(Button) findViewById(R.id.btn_counter);
        Button btn_cal=(Button) findViewById(R.id.btn_calculator);
        Button btn_contact=(Button) findViewById(R.id.btn_contacts);
        btn_cal.setOnClickListener(this);
        btn_contact.setOnClickListener(this);
        btn_counter.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_counter:
                Intent go=new Intent(menuactivity.this,MainActivity.class);
                startActivity(go);
                break;
            case R.id.btn_calculator:
                Intent togo=new Intent(menuactivity.this,Calculator.class);
                startActivity(togo);
                break;
            case R.id.btn_contacts:
                Intent togo1=new Intent(menuactivity.this,contacts.class);
                startActivity(togo1);
                break;



        }
    }
}
