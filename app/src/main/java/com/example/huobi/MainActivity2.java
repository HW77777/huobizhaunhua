package com.example.huobi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();
        Double a=intent.getDoubleExtra("美元",0.0);
        Double b=intent.getDoubleExtra("英镑",0.0);
        Double c=intent.getDoubleExtra("欧元",0.0);
        EditText tx=findViewById(R.id.editText);
        EditText tx2=findViewById(R.id.editText2);
        EditText tx3=findViewById(R.id.editText3);
        tx.setText("dollor:"+a);tx2.setText("yin:"+b);tx3.setText("euro"+c);
    }

}