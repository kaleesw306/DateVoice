package com.example.datevoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class profile_activity extends Activity {
    ImageView iamge;
    TextView textView,textView1;
    FirebaseAuth auth;
    FirebaseUser user;
    String user_name_name;
    String profile_photo_photo;
    FirebaseDatabase firebaseDatabase,database,database1;
    DatabaseReference databaseReference5,data,data1,data2,data3,data22,database_story,database_name,post_data,friends_data;
    StorageReference storageReference1,storageReference2;
    Button escape;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        iamge=(ImageView)findViewById(R.id.profile_id_profile);
        textView=(TextView)findViewById(R.id.profile_name);
        textView1=(TextView)findViewById(R.id.profile_status);
        auth=FirebaseAuth.getInstance();
        escape=(Button)findViewById(R.id.escape_id);
        escape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(profile_activity.this,main_activity.class);

                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        user=auth.getCurrentUser();
        data3=FirebaseDatabase.getInstance().getReference("User");
        data3.keepSynced(true);
        data3.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_name_name=dataSnapshot.child("Name").getValue(String.class);
                profile_photo_photo=dataSnapshot.child("profile").getValue(String.class);
                textView.setText(user_name_name);
               String status=dataSnapshot.child("Status").getValue(String.class);
               textView1.setText(status);
                Picasso.with(profile_activity.this).load(profile_photo_photo).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.ic_person).into(iamge, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(profile_activity.this).load(profile_photo_photo).placeholder(R.drawable.ic_person).into(iamge);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent backintent=new Intent(profile_activity.this,main_activity.class);
        startActivity(backintent);
        overridePendingTransition(0,0);
        super.onBackPressed();
    }
}
