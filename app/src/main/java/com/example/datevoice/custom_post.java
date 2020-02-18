package com.example.datevoice;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class custom_post extends ArrayAdapter {
    ArrayList imagelist;
    Activity activity;
    String im;
    ImageView imageView;
    public custom_post(Activity activity, ArrayList imagelist)
    {
        super(activity,R.layout.custom_grid,imagelist);
        this.activity=activity;
        this.imagelist=imagelist;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=activity.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.custom_grid,null);
        imageView=(ImageView)view.findViewById(R.id.image_grid_id);
        im=imagelist.get(position).toString();
        Picasso.with(activity.getApplicationContext()).load(im).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.post_placholder).into(imageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(activity.getApplicationContext()).load(im).placeholder(R.drawable.ic_person_white).into(imageView);
            }
        });
        return view;
    }
}
