package com.example.datevoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

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

import java.util.ArrayList;


public class sms_activity extends Activity {
    FirebaseAuth mauth;
    FirebaseUser muser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference_chat_value;
    ArrayList user_list=new ArrayList();
    sms_cusstom_activity Sms_custom_activity;
    ListView listView;
    Button escape;
    SearchView searchView;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase firebaseDdatabase;
    DatabaseReference data,data1;
    ArrayList imagelist=new ArrayList();
    ArrayList namelist=new ArrayList();
    ArrayList userid=new ArrayList();
    ArrayList status=new ArrayList();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_layout);
        mauth=FirebaseAuth.getInstance();
        muser=mauth.getCurrentUser();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        firebaseDdatabase=FirebaseDatabase.getInstance();
//        data=firebaseDatabase.getReference("User");
        listView=(ListView)findViewById(R.id.message_list_id);
        firebaseDatabase=FirebaseDatabase.getInstance();
        escape=(Button)findViewById(R.id.escape_id);
        searchView=(SearchView)findViewById(R.id.search);
        escape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(sms_activity.this,main_activity.class);
                overridePendingTransition(0,0);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        Sms_custom_activity=new sms_cusstom_activity(sms_activity.this,user_list);
        listView.setAdapter(Sms_custom_activity);

        databaseReference=firebaseDatabase.getReference("Friends").child(muser.getUid());
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try
                {
                    String uid_another=dataSnapshot.child("others").getValue(String.class);
                    user_list.add(uid_another);
                    Sms_custom_activity.notifyDataSetChanged();
                }
                catch(NullPointerException e)
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name=parent.getItemAtPosition(position).toString();
                for (int ii=0;ii<user_list.size();ii++)
                {
                    if(name.equals(user_list.get(position).toString()))
                    {
                        Intent intent=new Intent(sms_activity.this,chatactivity.class);
                        intent.putExtra("anotheruserid",user_list.get(position).toString());
                        startActivity(intent);
                        overridePendingTransition(0,0);
                    }
                }
            }
        });
      /*  data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for(DataSnapshot data:dataSnapshot.getChildren()) {


                        String image = data.child("profile").getValue(String.class);
                        String getkey = data.getKey();
                        String name = data.child("Name").getValue(String.class);
                        String statusget = data.child("Status").getValue(String.class);
                        imagelist.add(image);
                        namelist.add(name);
                        userid.add(getkey);
                        status.add(statusget);
                    }

                } catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


    }
    @Override
    public void onBackPressed() {
        Intent backintent=new Intent(sms_activity.this,main_activity.class);
        startActivity(backintent);
        overridePendingTransition(0,0);
        super.onBackPressed();
    }
}
