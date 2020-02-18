package com.example.datevoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class friends extends Activity {
    FirebaseAuth mauth;
    FirebaseUser muser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference_chat_value;
    ArrayList user_list=new ArrayList();
    friend_cusstom_activity Friend_custom_activity;
    ListView listView;
    Button escape;
    SearchView searchView;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendscount_layout);
        mauth=FirebaseAuth.getInstance();
        muser=mauth.getCurrentUser();
        searchView=(SearchView)findViewById(R.id.search_friends);
        listView=(ListView)findViewById(R.id.message_list_id);
        firebaseDatabase=FirebaseDatabase.getInstance();
        Friend_custom_activity=new friend_cusstom_activity(friends.this,user_list);
        listView.setAdapter(Friend_custom_activity);
        escape=(Button)findViewById(R.id.escape_id);
        escape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(friends.this,main_activity.class);
                overridePendingTransition(0,0);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        databaseReference=firebaseDatabase.getReference("Friends").child(muser.getUid());
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try
                {
                    String uid_another=dataSnapshot.child("others").getValue(String.class);
                    user_list.add(uid_another);
                    Friend_custom_activity.notifyDataSetChanged();
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


    }
    @Override
    public void onBackPressed() {
        Intent backintent=new Intent(friends.this,main_activity.class);
        startActivity(backintent);
        overridePendingTransition(0,0);
        super.onBackPressed();
    }
}
