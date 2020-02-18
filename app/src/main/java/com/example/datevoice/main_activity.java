package com.example.datevoice;
import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import static com.example.datevoice.R.drawable.second;

public class main_activity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {
  FirebaseAuth auth;
  FirebaseUser user;
  Spinner spinner;
  FirebaseDatabase firebaseDatabase,database,database1;
  DatabaseReference databaseReference5,data,data1,data2,data3,data22,database_story,database_name,post_data,friends_data,temp_status_data,temp_status_retreive,friend_list_database;
  StorageReference storageReference1,storageReference2;
String photo;
String name;
String spinner_value;
EditText status;
int i=0;
CircleImageView imageview;
ImageView img;
    ListView storylist;
    Button floatingbutton;
    ArrayList list=new ArrayList();
    Story_adapter storyadapter;
    Toolbar toolbar;
    NavigationView navigationView;
    String user_name_name,profile_photo_photo;
    String ll;
    ArrayList namea=new ArrayList();
    ArrayList imagea=new ArrayList();
    ArrayList count=new ArrayList();
    ArrayList count1=new ArrayList();
    ArrayList date_list=new ArrayList();
    ArrayList user_status_list=new ArrayList();
    ArrayList friend_list=new ArrayList();
    ArrayList caption_list=new ArrayList();
    ArrayList temp_caption_list=new ArrayList();
    TextView textView,textView5,textView6,textView90;
    RecyclerView recyclerView_status;
    temp_status_recycle Temp_status_recycle;
    ArrayList user_name=new ArrayList();
    CardView cardView;
    EditText editText252;
    RelativeLayout relativeLayout;
    Button but_search_post;
    String catego[]={"Gaming","Food","TV & Movies","Science & Tech","Animals","Travel","Style","Music","Comics","Sports"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        storylist=(ListView)findViewById(R.id.Storylist) ;
        database1=FirebaseDatabase.getInstance();
        database_story=database1.getReference("StatusStory");
        database_name=database1.getReference("StatusNames");
        post_data=database1.getReference("StatusStory");
        friends_data=database1.getReference("Friends");
        textView90=(TextView)findViewById(R.id.statussss_id);
        cardView=(CardView)findViewById(R.id.card_id) ;
        storyadapter=new Story_adapter(main_activity.this,list,namea,imagea,date_list,caption_list);
        storylist.setAdapter(storyadapter);
        relativeLayout=(RelativeLayout)findViewById(R.id.relative);
        status=(EditText)findViewById(R.id.statusid) ;
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.buttonanimation);
        cardView.startAnimation(animation);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        recyclerView_status=(RecyclerView)findViewById(R.id.status_vertical_id);
        Temp_status_recycle =new temp_status_recycle(this,user_status_list,user_name,friend_list);
        recyclerView_status.setHasFixedSize(true);
        recyclerView_status.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView_status.setAdapter(Temp_status_recycle);
        but_search_post=(Button)findViewById(R.id.search_post);
        but_search_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(main_activity.this,post_search.class);
                overridePendingTransition(0,0);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        if(ContextCompat.checkSelfPermission(main_activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {

        }
        else
        {
            request();
        }
        imageview=(CircleImageView) findViewById(R.id.profilpicid);
        textView5=(TextView)findViewById(R.id.text5);
        textView6=(TextView)findViewById(R.id.friendd_id);
        Button button=(Button)toolbar.findViewById(R.id.comment_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent message_intent=new Intent(main_activity.this,sms_activity.class);
                startActivity(message_intent);
                overridePendingTransition(0,0);
            }
        });

        floatingbutton=(Button) findViewById(R.id.floatbuttonid) ;
        floatingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                floatbutton();
            }
        });
 auth=FirebaseAuth.getInstance();
 user=auth.getCurrentUser();
 database=FirebaseDatabase.getInstance();
 storageReference1=FirebaseStorage.getInstance().getReference();

 data=database.getReference("User").child(user.getUid());
 data2=database.getReference("User").child(user.getUid());
textView6.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intt=new Intent(main_activity.this,friends.class);
        startActivity(intt);
        overridePendingTransition(0,0);
    }
});
        friend_list_database=FirebaseDatabase.getInstance().getReference("Friends");
 data2.addValueEventListener(new ValueEventListener() {
     @Override
     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
         if(dataSnapshot!=null)
         {
             data2.child("Online").onDisconnect().setValue(false);
             data2.child("Online").setValue(true);
         }
     }

     @Override
     public void onCancelled(@NonNull DatabaseError databaseError) {

     }
 });
 temp_status_retreive=FirebaseDatabase.getInstance().getReference("Temp_status");
 temp_status_retreive.addValueEventListener(new ValueEventListener() {
     @Override
     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

         for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
         {
             try
             {
                 String user_name_string_status=dataSnapshot1.child("photo").getValue(String.class);
                 user_status_list.add(user_name_string_status);
                 String user_id=dataSnapshot1.child("UserId").getValue(String.class);
                 user_name.add(user_id);


             }
             catch (NullPointerException e)
             {
                 e.printStackTrace();
             }


         }
     }

     @Override
     public void onCancelled(@NonNull DatabaseError databaseError) {

     }
 });
 friend_list_database.child(user.getUid()).addValueEventListener(new ValueEventListener() {
     @Override
     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
         for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
         {
             try
             {
                 String data_list_friends=dataSnapshot1.child("others").getValue(String.class);
                 friend_list.add(data_list_friends);
             }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
         }

     }

     @Override
     public void onCancelled(@NonNull DatabaseError databaseError) {

     }
 });
        Temp_status_recycle.notifyDataSetChanged();
        data3=FirebaseDatabase.getInstance().getReference("User");
        data3.keepSynced(true);
        data3.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_name_name=dataSnapshot.child("Name").getValue(String.class);
                profile_photo_photo=dataSnapshot.child("profile").getValue(String.class);
                status.setText(user_name_name);
                String status=dataSnapshot.child("Status").getValue(String.class);
                textView90.setText(status);
                Picasso.with(main_activity.this).load(profile_photo_photo).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.ic_person).into(imageview, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(main_activity.this).load(profile_photo_photo).into(imageview);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        post_data.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try
                {
                    String nameee=dataSnapshot.child("Name").getValue(String.class);

                    if(nameee.equals(user_name_name))
                    {

                        count.add("yes");
                    }
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
                textView=(TextView)findViewById(R.id.post_num);
                textView.setText(String.valueOf(count.size()));
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
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(main_activity.this,post_activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
  friends_data.child(user.getUid()).addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
          try
          {
              String frei=dataSnapshot.child("others").getValue(String.class);
              count1.add("yes");
          }
          catch(NullPointerException e)
          {
              e.printStackTrace();
          }
          TextView textView11=(TextView)findViewById(R.id.fre_num);
          textView11.setText(String.valueOf(count1.size()));
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
  database_story.keepSynced(true);
 database_story.addChildEventListener(new ChildEventListener() {
     @Override
     public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
         try
         {
             String status_string=dataSnapshot.child("status").getValue(String.class);
             list.add(status_string);
             String name_data=dataSnapshot.child("Name").getValue(String.class);
             namea.add(name_data);
             String image_data=dataSnapshot.child("Photo").getValue(String.class);
             imagea.add(image_data);
             String date=dataSnapshot.child("Date").getValue(String.class);
             date_list.add(date);
             String caption_text=dataSnapshot.child("Caption").getValue(String.class);
             caption_list.add(caption_text);
             storylist.smoothScrollToPosition(namea.size()-1);

         }
         catch (NullPointerException e)
         {
             e.printStackTrace();
         }
         storyadapter.notifyDataSetChanged();
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

   DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setHomeAsUpIndicator(R.drawable.ic_format_align_justify);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            overridePendingTransition(0,0);
            finish();
            super.onBackPressed();
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

 if(id==R.id.nav_home)
 {
     Intent friendintent=new Intent(main_activity.this,friendactivity.class);
     startActivity(friendintent);
     overridePendingTransition(0,0);
 }
        if(id==R.id.nav_profile)
        {
            Intent friendintent=new Intent(main_activity.this,profile_activity.class);
            startActivity(friendintent);
            overridePendingTransition(0,0);
        }
        if(id==R.id.exit)
        {
         overridePendingTransition(0,0);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void floatbutton()
    {
        Intent floatintent=new Intent(Intent.ACTION_PICK);
        floatintent.setType("image/*");
        startActivityForResult(Intent.createChooser(floatintent,"Pick image For STORY"),1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if((requestCode==1)&&(resultCode==RESULT_OK))
        {
            LayoutInflater layoutInflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=layoutInflater.inflate(R.layout.status_upload_layout,null,false);

            Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.buttonanimation);
            Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.chatblack);
            final PopupWindow popupWindow=new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
            popupWindow.showAtLocation(relativeLayout, Gravity.RIGHT | Gravity.BOTTOM,0,50);
            Button upload=(Button)view.findViewById(R.id.upload_button);
            final Uri uri=data.getData();
            String image_data=uri.toString();
            ImageView imageView=(ImageView)view.findViewById(R.id.image_ID);
            Button button=(Button)view.findViewById(R.id.cancel_buttt);
           editText252=(EditText)view.findViewById(R.id.edit_text_caption);
            spinner= view.findViewById(R.id.spinner_category);
         //  ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,catego);
          // arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          // spinner.setAdapter(arrayAdapter);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            upload.startAnimation(animation);
            imageView.startAnimation(animation1);
            editText252.startAnimation(animation1);
            Picasso.with(this).load(image_data).fit().centerCrop().into(imageView);
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    Toast.makeText(main_activity.this, "Wait Your Story Upload", Toast.LENGTH_LONG).show();
                    String mp=uri.toString();
                    databaseReference5=FirebaseDatabase.getInstance().getReference("Statusstory").push();
                    String lll=String.valueOf(System.currentTimeMillis());
                    temp_status_data=FirebaseDatabase.getInstance().getReference("Temp_status").child(user.getUid()).child(lll);
                    String pushid=databaseReference5.getKey();
                    storageReference2=storageReference1.child("images").child(pushid+".jpg");
                    storageReference2.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(main_activity.this, "Upload preparing", Toast.LENGTH_LONG).show();
                                    String mp=uri.toString();
                                    Intent in=getIntent();
                                    ll=String.valueOf(System.currentTimeMillis());
                                    data22=database1.getReference("StatusStory").child(ll);
                                    final String profi=in.getStringExtra("profile");
                                    data22.child("Name").setValue(user_name_name);
                                    data22.child("Photo").setValue(profile_photo_photo);
                                    final String text=editText252.getText().toString();
                                    data22.child("Caption").setValue(text);
                                    data22.child("Category").setValue(spinner_value);
                                    SimpleDateFormat sdf=new SimpleDateFormat("dd MM yyyy");
                                    final String date=sdf.format(System.currentTimeMillis());
                                    data22.child("Date").setValue(date);
                                    temp_status_data.child("photo").setValue(profile_photo_photo);
                                    temp_status_data.child("Name").setValue(user_name_name);
                                    temp_status_data.child("Date").setValue(date);
                                    temp_status_data.child("UserId").setValue(user.getUid());
                                    temp_status_data.child("Status").setValue(mp);
                                    temp_status_data.child("Caption").setValue(text);
                                    data22.child("status").setValue(mp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            Toast.makeText(main_activity.this, "Story Uploaded", Toast.LENGTH_SHORT).show();

                                        }
                                    });


                                }
                            });
                        }
                    });
                }
            });
            popupWindow.update();


        }
    }

    public void request()
    {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
        {
            if(ContextCompat.checkSelfPermission(main_activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(main_activity.this, "PERMISSION GRANTED FOR ACCESS STORAGE", Toast.LENGTH_SHORT).show();
            }
            else {
                requestInterper();
            }
        }}
    public void requestInterper()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission_group.STORAGE))
        {
            new AlertDialog.Builder(this)
                    .setTitle("NEED PERMISSION")
                    .setMessage("This permission is needed because of Access Storage")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(main_activity.this, new String[]{Manifest.permission_group.STORAGE},1);
                        }

                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission_group.STORAGE},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1)
        {
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(main_activity.this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(main_activity.this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
