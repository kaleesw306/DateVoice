package com.example.datevoice;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Coustomadapter extends ArrayAdapter {
    Activity activity;
    ArrayList imagelist;
    ArrayList namelist;
    ArrayList status;
    CircleImageView circleImageView;
    TextView textView;
    public Coustomadapter(Activity activity,ArrayList imagelist,ArrayList namelist)
    {
        super(activity,R.layout.friendlistcardview,namelist);
        this.activity=activity;
       this.imagelist=imagelist;
        this.namelist=namelist;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=activity.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.friendlistcardview,null);
        circleImageView=(CircleImageView)view.findViewById(R.id.imageviewcard);
        textView=(TextView)view.findViewById(R.id.textviewcard);
        String imagevieww=imagelist.get(position).toString();
        Picasso.with(activity.getApplicationContext()).load(imagevieww).placeholder(R.drawable.ic_person).into(circleImageView);
        String textviewlayout=namelist.get(position).toString();
        textView.setText(textviewlayout);

        return view;
    }
}
