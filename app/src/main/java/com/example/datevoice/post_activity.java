package com.example.datevoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class post_activity extends Activity {
    GridView gridView;
    FirebaseAuth mauth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,data3;
    String user_name_name;
    ArrayList arrayList=new ArrayList();
    custom_post Custom_post;
    Button escape;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);
        gridView=(GridView)findViewById(R.id.grid_id);
        Custom_post=new custom_post(this,arrayList);
        gridView.setAdapter(Custom_post);
        mauth=FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("StatusStory");
        databaseReference.keepSynced(true);
        data3=FirebaseDatabase.getInstance().getReference("User");
        escape=(Button)findViewById(R.id.escape_id);
        escape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(post_activity.this,main_activity.class);

                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        data3.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_name_name=dataSnapshot.child("Name").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {
                    String pp=dataSnapshot.child("Name").getValue(String.class);
                    if (user_name_name.equals(pp))
                    {
                        String post=dataSnapshot.child("status").getValue(String.class);
                        arrayList.add(post);
                        Custom_post.notifyDataSetChanged();
                    }

                }catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent backintent=new Intent(post_activity.this,main_activity.class);
        startActivity(backintent);
        overridePendingTransition(0,0);
        super.onBackPressed();
    }
}
