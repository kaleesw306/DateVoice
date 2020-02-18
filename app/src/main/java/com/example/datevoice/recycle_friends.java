package com.example.datevoice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class recycle_friends extends RecyclerView.Adapter<recycle_friends.ViewHolder>
{
    ArrayList iamge_list;
    Context context;
    public recycle_friends(ArrayList image_list,Context context)
    {
        this.iamge_list=image_list;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycle_main_friends,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String iamge=iamge_list.get(position).toString();
        Picasso.with(context.getApplicationContext()).load(iamge).placeholder(R.drawable.ic_person).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return iamge_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.imageid);
        }
    }
}
