package com.example.huobi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
        tx.setText(String.valueOf(a));tx2.setText(String.valueOf(b));tx3.setText(String.valueOf(c));
    }
    public void Button5(View view){
        Intent data=getIntent();
        EditText tx=findViewById(R.id.editText);
        EditText tx2=findViewById(R.id.editText2);
        EditText tx3=findViewById(R.id.editText3);
        Double dollor=Double.valueOf(tx.getText().toString());
        Double yin=Double.valueOf(tx2.getText().toString());
        Double euro=Double.valueOf(tx3.getText().toString());
        data.putExtra("美元",dollor);
        data.putExtra("英镑",yin);
        data.putExtra("欧元",euro);
        setResult(2,data);
        finish();
    }

}