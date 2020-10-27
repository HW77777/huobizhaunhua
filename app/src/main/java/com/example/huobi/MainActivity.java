package com.example.huobi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements Runnable{
    Handler handler;
    Float dollor;
    Float yin;
    Float euro;
    String updateDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        dollor = sharedPreferences.getFloat("美元", 0.0f);
        yin = sharedPreferences.getFloat("英镑", 0.0f);
        euro = sharedPreferences.getFloat("欧元", 0.0f);
        updateDate = sharedPreferences.getString("update_date","");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr = sdf.format(today);
        if(!todayStr.equals(updateDate)){
            //开启子线程
            Thread t = new Thread(this);
            t.start();
        }
        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                //if(msg.what==5){
                  //  String str = (String) msg.obj;
                    //show.setText(str);
               // }
                if(msg.what==5){
                    Bundle bdl = (Bundle) msg.obj;
                    dollor = bdl.getFloat("美元");
                    yin = bdl.getFloat("英镑");
                    euro = bdl.getFloat("欧元");
                    SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putFloat("美元",dollor);
                    editor.putFloat("英镑",yin);
                    editor.putFloat("欧元",euro);
                    editor.putString("update_date",todayStr);
                    //editor.putString("update_date",todayStr);
                    editor.apply();
                    Toast.makeText(MainActivity.this, "汇率已更新", Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.amenu,menu);
        return true;
    }
    public void Button2(View view){
        EditText tx= findViewById(R.id.EditText);
        Float a=Float.valueOf(tx.getText().toString());
        Float b=yin*a;
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
            dollor=data.getFloatExtra("美元",0.0f);
            yin=data.getFloatExtra("英镑",0.0f);
            euro=data.getFloatExtra("欧元",0.0f);
            SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("美元",dollor);
            editor.putFloat("英镑",yin);
            editor.putFloat("欧元",euro);
            editor.commit();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void run() {
 //       for(int i=1;i<3;i++)
        //Log.i(TAG, "run: i=" + i);
          //  try {
            //    Thread.sleep(2000);
            //} catch (InterruptedException e) {
              //  e.printStackTrace();
            //}
        //}
       // URL url = null;
        Bundle bundle = new Bundle();
        Document doc = null;
        try {
            String url = "http://www.usd-cny.com/bankofchina.htm";
            doc = Jsoup.connect(url).get();
            Elements tables = doc.getElementsByTag("table");
            Element table6 = tables.get(0);
            //Log.i(TAG, "run: table6=" + table6);
            //获取TD中的数据
            Elements tds = table6.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                String str1 = td1.text();
                String val = td2.text();


                float v = 100f / Float.parseFloat(val);
                if("美元".equals(str1)){
                    bundle.putFloat("美元", v);
                }else if("英镑".equals(str1)){
                    bundle.putFloat("英镑", v);
                }else if("欧元".equals(str1)){
                    bundle.putFloat("欧元", v);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(5);
        msg.obj = bundle;
        handler.sendMessage(msg);

       // try {
           // url = new URL("http://www.usd-cny.com/bankofchina.htm");
           // HttpURLConnection http = (HttpURLConnection) url.openConnection();
           // InputStream in = http.getInputStream();

          //  String html = inputStream2String(in);

       // } catch (MalformedURLException e) {
        //    e.printStackTrace();
       // } catch (IOException e) {
        //    e.printStackTrace();
      //  }
        //Message msg = handler.obtainMessage(5);
//msg.what = 5;
       // msg.obj = "Hello from run()";
       // handler.sendMessage(msg);
    }
    //private String inputStream2String(InputStream inputStream) throws IOException {
     //   final int bufferSize = 1024;
      //  final char[] buffer = new char[bufferSize];
     //   final StringBuilder out = new StringBuilder();
     //   Reader in = new InputStreamReader(inputStream, "gb2312");
     //   while (true) {
      //      int rsz = in.read(buffer, 0, buffer.length);
      //      if (rsz < 0)
       //         break;
      //      out.append(buffer, 0, rsz);
      //  }
     //   return out.toString();
   // }
}