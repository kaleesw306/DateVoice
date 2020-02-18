package com.example.datevoice;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.UserHandle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.sinch.android.rtc.SinchClient.*;

public class chatactivity extends Activity {
    Button speakbutton;
LottieAnimationView view;
MediaRecorder mediaRecorder;
File filem;
String file=null;
 FirebaseAuth auth;
 FirebaseUser user;
 FirebaseStorage storage;
 StorageReference storagerefernce;
 DatabaseReference databaseReference,databaseReference1,databaseReference_chat_value;
 FirebaseDatabase firebaseDatabase;
String userid;
String audio;
StorageReference store;
ListView listview;
ArrayAdapter arrayAdapter;
Toolbar toolbar;
ArrayList arrayList=new ArrayList<>();
    ArrayList date_list=new ArrayList();
    ArrayList list1=new ArrayList <>();
    ArrayList list2=new ArrayList <>();
    ArrayList list3=new ArrayList <>();
    ArrayList list4=new ArrayList <>();
    ArrayList list5=new ArrayList <>();
    ArrayList list6=new ArrayList <>();
    ArrayList list_chat=new ArrayList();
    editchatactivity Editchatactivity;
  //  SinchClient client;
    TextView name_text;
    CircleImageView profile_photo;
    String name_string,image_string;
    Button butt_escape;
    ArrayList user_list=new ArrayList();
    TextView online_text_view;
    String date;
    SinchClient sinchClient;
    Call call;
 Button delete,detail,forword;
boolean online_status;
Button button_call;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);
view=(LottieAnimationView)findViewById(R.id.voicelottie);
Intent userintent=getIntent();
toolbar=(Toolbar)findViewById(R.id.toolbar);
 delete=(Button)findViewById(R.id.delete_id);
 detail=(Button)findViewById(R.id.known_id) ;
 forword=(Button)findViewById(R.id.forword_id);
userid=userintent.getStringExtra("anotheruserid");

        if((ContextCompat.checkSelfPermission(chatactivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        &&((ContextCompat.checkSelfPermission(chatactivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)))
        {

        }
        else
        {
            request();
        }
listview=(ListView)findViewById(R.id.listviewid);
file=Environment.getExternalStorageDirectory().getAbsolutePath();
file+="/"+System.currentTimeMillis()+"DateVoice.3gp";
auth=FirebaseAuth.getInstance();
user=auth.getCurrentUser();
firebaseDatabase=FirebaseDatabase.getInstance();
name_text=(TextView)toolbar.findViewById(R.id.name_id) ;
profile_photo=(CircleImageView)toolbar. findViewById(R.id.prodile_id);
butt_escape=(Button)findViewById(R.id.escape_id);
button_call=(Button)findViewById(R.id.call_id);
online_text_view=(TextView)findViewById(R.id.active_id);
butt_escape.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(chatactivity.this,main_activity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
});

storagerefernce=FirebaseStorage.getInstance().getReference();
databaseReference1=firebaseDatabase.getReference("Voice Message");
DatabaseReference databaseReference8=firebaseDatabase.getReference("User");
databaseReference8.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        name_string=dataSnapshot.child("Name").getValue(String.class);
        image_string=dataSnapshot.child("profile").getValue(String.class);
        online_status=dataSnapshot.child("Online").getValue(boolean.class);
        if(online_status==true)
        {
            online_text_view.setText("Online");
        }
        else
        {
            online_text_view.setText("Offline");
        }
        name_text.setText(name_string);
        Picasso.with(chatactivity.this).load(image_string).placeholder(R.drawable.ic_person).into(profile_photo);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

Editchatactivity=new editchatactivity(this,list_chat,user_list,userid,date_list);
listview.setAdapter(Editchatactivity);
listview.setClickable(true);
        listview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(chatactivity.this, "kgygulgugukuyk", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
 databaseReference_chat_value=firebaseDatabase.getReference("Messages").child(user.getUid()).child(userid);
databaseReference_chat_value.keepSynced(true);

view.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
        startrecording();
        view.playAnimation();
        Toast.makeText(chatactivity.this, "record start", Toast.LENGTH_SHORT).show();
        return false;
    }
});

view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try
        {
            stopreoording();

            view.cancelAnimation();
        }catch (Exception i)
        {
            i.printStackTrace();
        }


    }
});
 sinchClient=Sinch.getSinchClientBuilder().context(this).userId(user.getUid())
         .applicationKey("2f39ddc9-e2a5-467b-beca-d1e569c6b21f")
         .applicationSecret("weGebaZBhkWEVmiI0+UX+Q==")
         .environmentHost("clientapi.sinch.com").build();
sinchClient.startListeningOnActiveConnection();
sinchClient.setSupportCalling(true);
sinchClient.getCallClient().addCallClientListener(new sinchCallClientListner()
{

});
 sinchClient.start();
        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(call==null)
                {
                    try
                    {
                        call=sinchClient.getCallClient().callUser(userid);
                        call.addCallListener(new sinchcall());
                        opendialog(call);
                    }
                    catch(IllegalStateException e)
                    {
                        e.printStackTrace();
                    }

                }

            }
        });

databaseReference_chat_value.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        try
        {

            String message=dataSnapshot.child("Message").getValue(String.class);
            list_chat.add(message);
            String name_user=dataSnapshot.child("userid_detail").getValue(String.class);
            user_list.add(name_user);
            date=dataSnapshot.child("time").getValue(String.class);
            date_list.add(date);
            Editchatactivity.notifyDataSetChanged();
            listview.smoothScrollToPosition(list_chat.size()-1);
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
    public void startrecording()
    {
mediaRecorder=new MediaRecorder();
mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
mediaRecorder.setOutputFile(file);
try
{
    mediaRecorder.prepare();
}catch (IOException e)
{
    e.printStackTrace();
}
mediaRecorder.start();
    }
    public void stopreoording()
    {
         mediaRecorder.stop();
        mediaRecorder.release();
        upload();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(chatactivity.this, "Hold to Record Voice", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void upload()
    {
        Uri u=Uri.fromFile(new File(file));
        databaseReference=firebaseDatabase.getReference("Voice Message").child("SentMessage").child(user.getUid()).child(userid).child("Sent").push();
        String pushid=databaseReference.getKey();
        store=storagerefernce.child("Audio").child(pushid+".3gp");
        store.putFile(u).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                store.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        audio=uri.toString();
                        String current_user="Messages"+"/"+user.getUid()+"/"+userid;
                        String another_user="Messages"+"/"+userid+"/"+user.getUid();
                        DatabaseReference databaseReference_user=firebaseDatabase.getReference("Messages").child(user.getUid()).child(userid).push();
                        DatabaseReference databaseReference7=firebaseDatabase.getReference();
                        String push_id=databaseReference_user.getKey();
                        Map all_voice_chat=new HashMap();
                        all_voice_chat.put("Message",audio);
                        String current_time=DateFormat.getDateTimeInstance().format(new Date());
                        all_voice_chat.put("time",current_time);
                        all_voice_chat.put("userid_detail",user.getUid());
                        all_voice_chat.put("another_user",userid);
                        Map message_has=new HashMap();
                        message_has.put(current_user+"/"+push_id,all_voice_chat);
                        message_has.put(another_user+"/"+push_id,all_voice_chat);
                      databaseReference7.updateChildren(message_has, new DatabaseReference.CompletionListener() {
                          @Override
                          public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                              if(databaseError!=null)
                              {
                                  Toast.makeText(chatactivity.this, "Message not Uploaded", Toast.LENGTH_SHORT).show();
                              }
                              else
                              {
                                  Toast.makeText(chatactivity.this, "Message Uploaded", Toast.LENGTH_SHORT).show();
                              }
                          }
                      });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(chatactivity.this, "not uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent backintent=new Intent(chatactivity.this,main_activity.class);
        startActivity(backintent);
        overridePendingTransition(0,0);
        super.onBackPressed();
    }





    public void request()
    {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
        {
            if((ContextCompat.checkSelfPermission(chatactivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&&
                    ((ContextCompat.checkSelfPermission(chatactivity.this, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED))
            &&((ContextCompat.checkSelfPermission(chatactivity.this, Manifest.permission.MODIFY_AUDIO_SETTINGS)== PackageManager.PERMISSION_GRANTED)))
            {
                Toast.makeText(chatactivity.this, "PERMISSION GRANTED FOR ACCESS STORAGE", Toast.LENGTH_SHORT).show();
            }
            else {
                requestInterper();
            }
        }}

    public void requestInterper()
    {
        if((ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission_group.STORAGE))&&((ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECORD_AUDIO)))
        &&(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.MODIFY_AUDIO_SETTINGS)))
        {
            new AlertDialog.Builder(this)
                    .setTitle("NEED PERMISSION")
                    .setMessage("This permission is needed because of Access Storage")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(chatactivity.this, new String[]{Manifest.permission_group.STORAGE},1);
                            ActivityCompat.requestPermissions(chatactivity.this, new String[]{Manifest.permission.RECORD_AUDIO},1);
                            ActivityCompat.requestPermissions(chatactivity.this, new String[]{Manifest.permission.MODIFY_AUDIO_SETTINGS},1);
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
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},1);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.MODIFY_AUDIO_SETTINGS},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1)
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(chatactivity.this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(chatactivity.this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class  sinchCallClientListner implements CallClientListener
    {

        @Override
        public void onIncomingCall(CallClient callClient, Call call) {
            LayoutInflater layoutInflater=getLayoutInflater();
            View view=layoutInflater.inflate(R.layout.call_answer_layout,null);
            new AlertDialog.Builder(chatactivity.this).setView(view).create().show();

        }
    }
    public class sinchcall implements  CallListener
    {

        @Override
        public void onCallProgressing(Call call) {

        }

        @Override
        public void onCallEstablished(Call call) {

        }

        @Override
        public void onCallEnded(Call mcall) {
            Toast.makeText(chatactivity.this, "Call Ended", Toast.LENGTH_SHORT).show();
            call=null;
            mcall.hangup();

        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> list) {

        }
    }
    public void opendialog(Call call)
    {
        LayoutInflater layoutInflater=getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.call_activity,null);
        new AlertDialog.Builder(chatactivity.this).setView(view).create().show();
    }

}

