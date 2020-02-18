package com.example.datevoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class MainActivity extends Activity {
    ViewPager viewpager;
    String name[]={"Date Voice.","Freely Speak..","Let's Gets Started"};
   Integer []image={R.drawable.first,R.drawable.second,R.drawable.third};
   String []boldheading={" video chating","Voice Messaging","Enjoy Your Date"};
  String text[]={"    Getting       no       Message is \n" +
          "also      A message........      for ME","I smile        whenever\n"
          +
          "I get         A message      from U...... ","My Wife      loves me even \n"
          +

          "though        I FART        in my Chat"};
  SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager=(ViewPager)findViewById(R.id.viewpagerid);
        sharedPreferences=getSharedPreferences("introsliderfile", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("getvalue"))
        {
            Intent loginactivity = new Intent(getApplicationContext(), phonenumberauth.class);
            startActivity(loginactivity);
            overridePendingTransition(0,0);
            finish();
        }
        else
        {
            ImageAdapter imageadapter=new ImageAdapter(this,image,boldheading,text,name);

            viewpager.setAdapter(imageadapter);

        }



    }
}