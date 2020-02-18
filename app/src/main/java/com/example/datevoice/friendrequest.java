package com.example.datevoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class friendrequest extends Activity {
    String userid;
    String name;
    String status;
    String profilepic;
    CircleImageView imageview;
    TextView nametext,statustext;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference data,data1;
    Button requestbutton;
    String currentstate;
    String m="Sent";
    String all_name;
    String profile_photo;
    DatabaseReference data_friends,data99;
    RecyclerView recyclerView;
    recycle_friends Recycle_friends;
    ArrayList image_list_friends=new ArrayList();
    Button escape;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendrequestlayout);
        Intent intent=getIntent();
        userid=  intent.getStringExtra("id");
        name=intent.getStringExtra("name");
        status=intent.getStringExtra("status");
        profilepic=intent.getStringExtra("image");
        escape=(Button)findViewById(R.id.escape_id);
        escape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(friendrequest.this,friendactivity.class);
                overridePendingTransition(0,0);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        imageview=(CircleImageView)findViewById(R.id.anotheruserphoto);
        nametext=(TextView)findViewById(R.id.anotherusername);
        statustext=(TextView)findViewById(R.id.anotheruserstatus) ;
        requestbutton=(Button)findViewById(R.id.currentstatebutton);
        recyclerView=(RecyclerView)findViewById(R.id.friends_recycler);
        Recycle_friends=new recycle_friends(image_list_friends,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(Recycle_friends);
        Picasso.with(friendrequest.this).load(profilepic).placeholder(R.drawable.ic_person).into(imageview);
        nametext.setText(name);
        statustext.setText(status);
      auth=FirebaseAuth.getInstance();
      user=auth.getCurrentUser();
      firebaseDatabase=FirebaseDatabase.getInstance();
      all_name=name;
      profile_photo=profilepic;
      data=firebaseDatabase.getReference("Friend State");
      data1=firebaseDatabase.getReference("Friend State").child(user.getUid()).child(userid);
      data99=firebaseDatabase.getReference("User");
      data1.keepSynced(true);
      currentstate="NotFriend";
        data99.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    String image=data.child("profile").getValue(String.class);
                    image_list_friends.add(image);
                    Recycle_friends.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        data_friends=firebaseDatabase.getReference("Friends");
        data_friends.keepSynced(true);
      data.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              if(dataSnapshot.hasChild(userid))
              {
                  String value=dataSnapshot.child(userid).child("State").getValue(String.class);
                  if(value.equals(m))
                  {
                      requestbutton.setText("request sent");
                      currentstate="Sent";
                  }
                  if(value.equals("Friends"))
                  {
                      Intent intent=new Intent(friendrequest.this,chatactivity.class);
                      intent.putExtra("anotheruserid",userid);
                      startActivity(intent);
                      overridePendingTransition(0,0);
                      currentstate="Friends";
                  }
                  if(value.equals("Receive"))
                  {
                      requestbutton.setText("Accept it");
                      currentstate="Receive";
                      requestbutton.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              currentstate="Friends";
                              data.child(user.getUid()).child(userid).child("State").setValue("Friends").addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                      if(task.isSuccessful())
                                      {
                                          data.child(userid).child(user.getUid()).child("State").setValue("Friends").addOnCompleteListener(new OnCompleteListener<Void>() {
                                              @Override
                                              public void onComplete(@NonNull Task<Void> task) {
                                                  if(task.isSuccessful())
                                                  {
                                                     // Toast.makeText(friendrequest.this, "yes", Toast.LENGTH_SHORT).show();
                                                      Intent intent=new Intent(friendrequest.this,chatactivity.class);
                                                      intent.putExtra("anotheruserid",userid);
                                                      String num=String.valueOf(System.currentTimeMillis());
                                                      data_friends.child(user.getUid()).child(num).child("others").setValue(userid);
                                                      startActivity(intent);
                                                      overridePendingTransition(0,0);
                                                  }
                                                  else
                                                  {
                                                      Toast.makeText(friendrequest.this, "no", Toast.LENGTH_SHORT).show();
                                                  }

                                              }
                                          });
                                      }
                                      else
                                      {
                                          Toast.makeText(friendrequest.this, "noooo", Toast.LENGTH_SHORT).show();
                                      }
                                  }
                              });

                          }
                      });
                  }
              }

          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
          });
      requestbutton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(currentstate.equals("NotFriend"))
              {
                  data.child(user.getUid()).child(userid).child("State").setValue("Sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          if(task.isSuccessful())
                          {
                              requestbutton.setText("Request sent");
                              currentstate="Sent";
                              data.child(userid).child(user.getUid()).child("State").setValue("Receive");
                          }
                          else
                          {
                              Toast.makeText(friendrequest.this, "Rerquest Falied", Toast.LENGTH_SHORT).show();
                          }
                  }
                  });
              }
      }
    });
}
    @Override
    public void onBackPressed() {
        Intent backintent=new Intent(friendrequest.this,friendactivity.class);
        startActivity(backintent);
        overridePendingTransition(0,0);
        super.onBackPressed();
    }
}
