package com.code4rox.calculatorfox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static java.sql.Types.NULL;

public class Calculator extends AppCompatActivity implements View.OnClickListener {

    EditText et1,et2;
    TextView tv1,tv2;
    Button b1,b2,b3,b4;
    float first,second,result=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        et1=(EditText) findViewById(R.id.first_number);
        et2=(EditText) findViewById(R.id.second_number);
        tv1=(TextView) findViewById(R.id.resultView);
        b1=(Button) findViewById(R.id.btn_plus);
        b2=(Button) findViewById(R.id.btn_minus);
        b3=(Button) findViewById(R.id.btn_multiply);
        b4=(Button) findViewById(R.id.btn_divide);
        //listener actions
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
    }

    public Boolean value_check_zero(float a,float b){
        if(b==0 && a>0)
        {
            Toast.makeText(getApplicationContext(),"Value Should be greater than zero",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(b==0 && a==0)
        {
            return false;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_plus:
                if(et1.getText().length()==0 || et2.getText().length()==0) {
                Toast.makeText(getApplicationContext(),"Some Value is missing",Toast.LENGTH_SHORT).show();
                    tv1.setText("Result");
                }
                else {
                    first = Float.valueOf(et1.getText().toString());
                    second = Float.valueOf(et2.getText().toString());
                    result=first+second;
                    tv1.setText(" "+result);
                    et1.setText(null);
                    et2.setText(null);
                    et1.requestFocus();
                    break;
                }

            case R.id.btn_minus:
                if(et1.getText().length()==0 || et2.getText().length()==0){
                    Toast.makeText(getApplicationContext(),"Some Value is missing",Toast.LENGTH_SHORT).show();
                    tv1.setText("Result");

               }
                else {
                    first = Float.valueOf(et1.getText().toString());
                    second = Float.valueOf(et2.getText().toString());
                    result=first-second;
                    tv1.setText(""+result);
                    et1.setText(null);
                    et2.setText(null);
                    et1.requestFocus();
                    break;
                }

            case R.id.btn_multiply:
                if(et1.getText().length()==0 || et2.getText().length()==0){
                    Toast.makeText(getApplicationContext(),"Some Value is missing",Toast.LENGTH_SHORT).show();
                    tv1.setText("Result");
                }
                else {
                    first = Float.valueOf(et1.getText().toString());
                    second = Float.valueOf(et2.getText().toString());
                    result=first*second;
                    tv1.setText(""+result);
                    et1.setText(null);
                    et2.setText(null);
                    et1.requestFocus();
                    break;
                }

            case R.id.btn_divide:
                if(et1.getText().length()==0 || et2.getText().length()==0){
                    Toast.makeText(getApplicationContext(),"Some Value is missing",Toast.LENGTH_SHORT).show();
                    tv1.setText("Result");
                }else {
                    first = Float.valueOf(et1.getText().toString());
                    second = Float.valueOf(et2.getText().toString());

                if(!value_check_zero(first,second)) {
                    if (first == 0 && second == 0) {
                        result = 1;
                    } else {
                        result = first / second;
                    }
                    tv1.setText("" + result);
                    et1.requestFocus();
                    et1.setText(null);
                    et2.setText(null);
                    break;

                }
                }
        }

    }
}
