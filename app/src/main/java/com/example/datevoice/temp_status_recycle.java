package com.example.datevoice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public  class temp_status_recycle extends RecyclerView.Adapter<temp_status_recycle.ViewHolder> {
    Context context;
    ArrayList images;
    ArrayList username;
    int num=0;
    ArrayList friendlist;
    FirebaseAuth mauth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String imagee;
    String nname;
    String name;
    public temp_status_recycle(Context context, ArrayList images,ArrayList username,ArrayList friendlist) {
        this.context=context;
        this.images=images;
        this.username=username;
        this.friendlist=friendlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.temp_status,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        mauth=FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("User");
       name =friendlist.get(position).toString();
        user=mauth.getCurrentUser();

        databaseReference.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imagee=dataSnapshot.child("profile").getValue(String.class);
                 nname=dataSnapshot.child("Name").getValue(String.class);
                if (name.equals(user.getUid()))
                {
                    Picasso.with(context.getApplicationContext()).load(imagee).fit().centerCrop().placeholder(R.drawable.ic_person).into(holder.circleImageView1);

                }
                else
                {
                    Picasso.with(context.getApplicationContext()).load(imagee).fit().centerCrop().placeholder(R.drawable.ic_person).into(holder.circleImageView);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(name.equals(user.getUid()))
        {
            holder.circleImageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Picasso.with(context.getApplicationContext()).load(imge).placeholder(R.drawable.love_kiss).into(holder.circleImageView);
                    Intent intent=new Intent(context.getApplicationContext(),view_page_status.class);
                    intent.putExtra("UserName",friendlist.get(position).toString());
                    intent.putExtra("Profile",imagee);
                    intent.putExtra("Name",nname);
                    context.startActivity(intent);
                }
            });
        }

if(!name.equals(user.getUid()))
{


        holder.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                       // Picasso.with(context.getApplicationContext()).load(imge).placeholder(R.drawable.love_kiss).into(holder.circleImageView);
                        Intent intent=new Intent(context.getApplicationContext(),view_page_status.class);
                        intent.putExtra("UserName",friendlist.get(position).toString());
                        intent.putExtra("Profile",imagee);
                        intent.putExtra("Name",nname);
                        context.startActivity(intent);
            }
        });
    }}

    @Override
    public int getItemCount() {
        return friendlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
      CircleImageView circleImageView,circleImageView1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=(CircleImageView)itemView.findViewById(R.id.temp_status_id);
            circleImageView1=(CircleImageView)itemView.findViewById(R.id.your_temp_id);
        }


    }

}
