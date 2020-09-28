package com.example.huobi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {



    Double dollor=0.1465;
    Double yin=0.1151;
    Double euro=0.1259;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.amenu,menu);
        return true;
    }
    public void Button2(View view){
        EditText tx= findViewById(R.id.EditText);
        Double a=Double.valueOf(tx.getText().toString());
        Double b=yin*a;
        tx.setText(String.valueOf(b));
    }
    public void Button(View view){

        EditText tx= findViewById(R.id.EditText);
        Double a=Double.valueOf(tx.getText().toString());
        Double b=dollor*a;
        tx.setText(String.valueOf(b));
    }
    public void Button3(View view){
        EditText tx= findViewById(R.id.EditText);
        Double a=Double.valueOf(tx.getText().toString());
        Double b=euro*a;
        tx.setText(String.valueOf(b));
    }
    public void Button4(View btn){
        Intent next=new Intent(this,MainActivity2.class);
         next.putExtra("美元",dollor);
         next.putExtra("英镑",yin);
         next.putExtra("欧元",euro);
        startActivityForResult(next,1);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {

        if (requestCode == 1 && resultCode == 2) {
            dollor= data.getDoubleExtra("美元",0.0);
            yin=data.getDoubleExtra("英镑",0.0);
            euro=data.getDoubleExtra("欧元",0.0);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}