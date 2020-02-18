package com.example.datevoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class view_page_status extends Activity {
    ViewPager viewPager;
    String user_id;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList user_list=new ArrayList();
    ArrayList status_list=new ArrayList();
    viewadapter Viewadapter;
    Button back;
    CircleImageView circleImageView;
    TextView textView;
    String profile,name;
    ArrayList temp_cap;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_view_page);
        back=(Button)findViewById(R.id.temp_back);
        circleImageView=(CircleImageView)findViewById(R.id.temp_profile);
        textView=(TextView)findViewById(R.id.temp_name);
        Intent intent=getIntent();
        user_id=intent.getStringExtra("UserName");
        profile=intent.getStringExtra("Profile");
        name=intent.getStringExtra("Name");
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Temp_status").child(user_id);
        viewPager=(ViewPager)findViewById(R.id.status_view_page_id);
        Viewadapter=new viewadapter(this,user_id,status_list,temp_cap);
        viewPager.setAdapter(Viewadapter);
        Picasso.with(this).load(profile).fit().centerCrop().placeholder(R.drawable.love_kiss).into(circleImageView);
        textView.setText(name);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view_page_status.this,main_activity.class);

                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    try
                    {
                        String status=dataSnapshot1.child("Status").getValue(String.class);
                        status_list.add(status);
                        String caption_text=dataSnapshot1.child("Caption").getValue(String.class);
                        temp_cap.add(caption_text);

                    }

                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                    Viewadapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
