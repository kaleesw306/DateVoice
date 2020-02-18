package com.example.datevoice;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class sms_cusstom_activity extends ArrayAdapter {
    Activity activity;
    ArrayList arrayList;
    FirebaseAuth mauth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    CircleImageView circleImageView;
    ImageView  circleImageView1;
    TextView textView;
    String uid;
    public sms_cusstom_activity(Activity activity,ArrayList arrayList)
    {
        super(activity,R.layout.custom_sms,arrayList);
        this.activity=activity;
        this.arrayList=arrayList;

    }
    @NonNull
    @Override
    public View getView(int position,@NonNull View convertView,@NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=activity.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.custom_sms,null);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("User");
        circleImageView=(CircleImageView)view.findViewById(R.id.profile_id);
        textView=(TextView)view.findViewById(R.id.name_id_sms);
        String name=arrayList.get(position).toString();
        circleImageView1=(ImageView) view.findViewById(R.id.online_s);
        databaseReference.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String namee=dataSnapshot.child("Name").getValue(String.class);
                String imagee=dataSnapshot.child("profile").getValue(String.class);
                boolean onlines=dataSnapshot.child("Online").getValue(boolean.class);
                textView.setText(namee);
                Picasso.with(activity.getApplicationContext()).load(imagee).placeholder(R.drawable.ic_person).into(circleImageView);
                if (onlines==true)
                {


                }
                else
                {
                    circleImageView1.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}
