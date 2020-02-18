package com.example.datevoice;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class viewadapter extends PagerAdapter {
    String userr;
    ArrayList status_list;
    Activity context;
    ImageView imageView;
    TextView reply_button;
    LinearLayout linearLayout;
    ImageView imageView1;
    TextView textview1;
    MediaRecorder mediaRecorder;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    FirebaseAuth auth;
    String file=null;
    String audio;
    StorageReference store,storagerefernce;
    DatabaseReference databaseReference,databaseReference1;
    Button viewbutton,replY_button;
    ArrayList cappu;
    TextView temp;
    public viewadapter(Activity context, String userr,ArrayList status_list,ArrayList cappu) {
        this.userr=userr;
        this.context=context;
        this.status_list=status_list;
        this.cappu=cappu;
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       LayoutInflater layoutInflater=context.getLayoutInflater();
       View view=layoutInflater.inflate(R.layout.status_view_page_custom,null);
       imageView=(ImageView)view.findViewById(R.id.status_id);
       reply_button=(TextView)view.findViewById(R.id.reply_id);
       replY_button=(Button)view.findViewById(R.id.second_image_id);
       temp=(TextView)view.findViewById(R.id.temp_caption);
       viewbutton=(Button)view.findViewById(R.id.viewrs);
        file= Environment.getExternalStorageDirectory().getAbsolutePath();
        file+="/"+System.currentTimeMillis()+"DateVoice.3gp";
        auth= FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        firebaseDatabase= FirebaseDatabase.getInstance();

        storagerefernce= FirebaseStorage.getInstance().getReference();
        databaseReference1=firebaseDatabase.getReference("Voice Message");
          if (user.getUid().equals(userr))
          {
              viewbutton.setVisibility(View.VISIBLE);
              reply_button.setVisibility(View.INVISIBLE);
              replY_button.setVisibility(View.INVISIBLE);
          }
               Picasso.with(context.getApplicationContext()).load(status_list.get(position).toString()).placeholder(R.drawable.post_placholder).into(imageView);
               temp.setText(cappu.get(position).toString());
        reply_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try
                {
                    startrecording();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

                return false;
            }
        });
        reply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    stopreoording();
                    Toast.makeText(context, "Hold to Record your voice", Toast.LENGTH_SHORT).show();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

            }
        });
       container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    @Override
    public int getCount() {
        return status_list.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
    public void startrecording()
    {
         mediaRecorder = new MediaRecorder();
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
        Toast.makeText(context, "Record Started", Toast.LENGTH_SHORT).show();
    }
    public void stopreoording()
    {
        mediaRecorder.stop();
        mediaRecorder.release();
        upload();
        reply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( context.getApplicationContext(),"Hold to Record Voice", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void upload()
    {
        Uri u=Uri.fromFile(new File(file));
        databaseReference=firebaseDatabase.getReference("Voice Message").child("SentMessage").child(user.getUid()).child(userr).child("Sent").push();
        String pushid=databaseReference.getKey();
        store=storagerefernce.child("Audio").child(pushid+".3gp");
        store.putFile(u).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                store.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        audio=uri.toString();
                        String current_user="Messages"+"/"+user.getUid()+"/"+userr;
                        String another_user="Messages"+"/"+userr+"/"+user.getUid();
                        DatabaseReference databaseReference_user=firebaseDatabase.getReference("Messages").child(user.getUid()).child(userr).push();
                        DatabaseReference databaseReference7=firebaseDatabase.getReference();
                        String push_id=databaseReference_user.getKey();
                        Map all_voice_chat=new HashMap();
                        all_voice_chat.put("Message",audio);
                        String current_time= DateFormat.getDateTimeInstance().format(new Date());
                        all_voice_chat.put("time",current_time);
                        all_voice_chat.put("userid_detail",user.getUid());
                        all_voice_chat.put("another_user",userr);
                        Map message_has=new HashMap();
                        message_has.put(current_user+"/"+push_id,all_voice_chat);
                        message_has.put(another_user+"/"+push_id,all_voice_chat);
                        databaseReference7.updateChildren(message_has, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if(databaseError!=null)
                                {
                                    Toast.makeText(context.getApplicationContext(), "Message not Uploaded", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(context.getApplicationContext(), "Message Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context.getApplicationContext(), "not uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
/**/