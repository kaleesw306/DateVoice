package com.example.datevoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageAdapter extends PagerAdapter {
    Activity context;
    Integer []imageadapter;
    String []boldheading;
    String text[];
    View view;
    String name[];
    public ImageAdapter(Activity context,Integer []imageadapter,String []boldheading,String text[],String name[])
    {
        this.context=context;
        this.imageadapter=imageadapter;
        this.text=text;
        this.boldheading=boldheading;
        this.name=name;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutinflater=context.getLayoutInflater();
        view=layoutinflater.inflate(R.layout.imageadapterlayout,null);
        Button button=(Button)view.findViewById(R.id.buttonid);

        LinearLayout linearLayout=(LinearLayout)view.findViewById(R.id.cardlayout);
       Button button1=(Button) view.findViewById(R.id.button1);
        Button button2=(Button)view.findViewById(R.id.button2);
        Button button3=(Button)view.findViewById(R.id.button3);
        if(position==0)
        {

         button1.setBackgroundResource(R.drawable.roundbutton);
            button2.setBackgroundResource(R.drawable.roundbutton1);
            button3.setBackgroundResource(R.drawable.roundbutton1);

            button.setVisibility(View.INVISIBLE);

        }
        if(position==1)
        {
            button1.setBackgroundResource(R.drawable.roundbutton);
            button2.setBackgroundResource(R.drawable.roundbutton);
            button3.setBackgroundResource(R.drawable.roundbutton1);
            button.setVisibility(View.INVISIBLE);

        }
        if (position==2)
        {
            Animation animation= AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.buttonanimation);
            button1.setBackgroundResource(R.drawable.roundbutton);
            button2.setBackgroundResource(R.drawable.roundbutton);
            button3.setBackgroundResource(R.drawable.roundbutton);
            button.setVisibility(View.VISIBLE);
            button.startAnimation(animation);


        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedprefernce = context.getSharedPreferences("introsliderfile", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedprefernce.edit();
                edit.putInt("getvalue", 1);
                edit.commit();
                Intent loginactivity = new Intent(context.getApplicationContext(), phonenumberauth.class);
                context.startActivity(loginactivity);

            }
        });
        ImageView imageview=(ImageView)view.findViewById(R.id.imageview);
        imageview.setImageResource(imageadapter[position]);
        TextView textview =(TextView)view.findViewById(R.id.textid) ;
        TextView textviewboldhead=(TextView)view.findViewById(R.id.textidbold) ;
        TextView texthead=(TextView)view.findViewById(R.id.textidhead) ;
        texthead.setText(name[position]);
        textviewboldhead.setText(boldheading[position]);
        textview.setText(text[position]);
        container.addView(view);

        return view;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }

    @Override
    public int getCount() {
        return imageadapter.length ;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }
}
