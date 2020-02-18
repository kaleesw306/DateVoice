package com.example.datevoice;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class editchatactivity extends ArrayAdapter {
    Activity activity;
    ArrayList text;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    ArrayList type;
    LottieAnimationView lottie;
    TextView name_textView;
    Button playbutton_black,playbutton_white;
    String name_string;
    String image_string,image_string1;
    String user_another;
    String date_black;
    String date_white;
    ArrayList date_list;
    TextView black_text,white_text;
    RelativeLayout relativeLayout;
    public editchatactivity(Activity activity,ArrayList text,ArrayList type,String user_another,ArrayList date_list)
    {
        super(activity,R.layout.editchatactivitylayout,text);
        this.activity=activity;
        this.text=text;
        this.type=type;
        this.user_another=user_another;
        this.date_list=date_list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutinflater=activity.getLayoutInflater();
        View view=layoutinflater.inflate(R.layout.editchatactivitylayout,null);
       final String audio=text.get(position).toString();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
      firebaseDatabase=FirebaseDatabase.getInstance();
        String user_id=user.getUid();
        CardView cardView=(CardView)view.findViewById(R.id.card_id);
        CardView cardView1=(CardView)view.findViewById(R.id.card_id1);
        name_textView=(TextView)view.findViewById(R.id.text_name_id);
        relativeLayout=(RelativeLayout)view.findViewById(R.id.cardview55);
        black_text=(TextView)view.findViewById(R.id.black_date);
        white_text=(TextView)view.findViewById(R.id.white_date);
        String id=type.get(position).toString();
        final CircleImageView circleImageView=(CircleImageView)view.findViewById(R.id.you_id);
        final CircleImageView circleImageView1=(CircleImageView)view.findViewById(R.id.you_id1);
        DatabaseReference databaseReference8=firebaseDatabase.getReference("User");
        Animation animation= AnimationUtils.loadAnimation(activity.getApplicationContext(),R.anim.chatblack);
        Animation animation1= AnimationUtils.loadAnimation(activity.getApplicationContext(),R.anim.buttonanimation);
        databaseReference8.child(user_another).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name_string=dataSnapshot.child("Name").getValue(String.class);
                image_string=dataSnapshot.child("profile").getValue(String.class);
                name_textView.setText(name_string);
                Picasso.with(activity.getApplicationContext()).load(image_string).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.ic_person).into(circleImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(activity.getApplicationContext()).load(image_string).placeholder(R.drawable.ic_person).into(circleImageView);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference8.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                image_string1=dataSnapshot.child("profile").getValue(String.class);
                Picasso.with(activity.getApplicationContext()).load(image_string1).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.ic_person).into(circleImageView1, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(activity.getApplicationContext()).load(image_string1).placeholder(R.drawable.ic_person).into(circleImageView1);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(id.equals(user_id))
        {
            cardView1.setVisibility(View.INVISIBLE);
            cardView.setBackgroundResource(R.drawable.chat_res);
            white_text.setVisibility(View.GONE);
            black_text.setText(date_list.get(position).toString());
            cardView.startAnimation(animation);
        }
        else
        {
            cardView.setVisibility(View.INVISIBLE);
            black_text.setVisibility(View.GONE);
            white_text.setText(date_list.get(position).toString());
            cardView1.startAnimation(animation1);
        }
        playbutton_black=(Button)view.findViewById(R.id.playbutton_black);
        playbutton_white=(Button)view.findViewById(R.id.playbutton_white) ;
        playbutton_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
try
{
    Uri uri=Uri.parse(audio);
  final MediaPlayer  mediaPlayer=MediaPlayer.create(activity.getApplicationContext(),uri);
    if(!mediaPlayer.isPlaying())
    {


        playy(mediaPlayer);
    }
    else
    {


        stop(mediaPlayer);
    }
}
catch(NullPointerException e)
{
    e.printStackTrace();
}



            }
        });
        playbutton_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Uri uri=Uri.parse(audio);
                  final MediaPlayer  mediaPlayer=MediaPlayer.create(activity.getApplicationContext(),uri);
                    if(!mediaPlayer.isPlaying())
                    {


                        playy(mediaPlayer);
                    }
                    else
                    {

                        stop(mediaPlayer);
                    }
                }
                catch(NullPointerException e)
                {
                    e.printStackTrace();
                }



            }
        });

        return view;
    }
    public void playy(final MediaPlayer mediaPlayer)
    {


        try
        {
            mediaPlayer.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }
    public void stop(final MediaPlayer mediaPlayer)
    {
        mediaPlayer.stop();

    }
}
