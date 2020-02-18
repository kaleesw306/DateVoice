package com.example.datevoice;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Story_adapter extends ArrayAdapter {
    ArrayList list;
    Activity activity;
    ArrayList namea;
    ArrayList imagea;
    TextView textView;
    Button iaage;
    String proifile_string;
    ImageView imageView1;
    TextView date;
    ArrayList dateelist;
    ArrayList caption_list;
    TextView text;
    Button menu_butt;
    LikeButton likeButton;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    public Story_adapter(Activity activity,ArrayList list,ArrayList namea,ArrayList imagea,ArrayList dateelist,ArrayList caption_list)
    {
        super(activity,R.layout.storylayout,namea);
        this.list=list;
        this.activity=activity;
        this.namea=namea;
        this.imagea=imagea;
        this.dateelist=dateelist;
        this.caption_list=caption_list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layout=activity.getLayoutInflater();
        View view=layout.inflate(R.layout.storylayout,null);
       PhotoView imageView=(PhotoView)view.findViewById(R.id.storyid);
        textView=(TextView)view.findViewById(R.id.user_name_id);
         imageView1=(ImageView)view.findViewById(R.id.profileimage);
         text=(TextView)view.findViewById(R.id.caption_id);
        menu_butt=(Button)view.findViewById(R.id.menu_story);
       databaseReference=FirebaseDatabase.getInstance().getReference("StatusStory");

        final String datee=dateelist.get(position).toString();
        menu_butt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 PopupMenu popupMenu=new PopupMenu(activity.getApplicationContext(),menu_butt,Gravity.CENTER);
                popupMenu.getMenuInflater().inflate(R.menu.story_menu_items,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                      int id= item.getItemId();
                      if (id==R.id.info_id)
                      {
                          Toast.makeText(activity,datee, Toast.LENGTH_SHORT).show();
                      }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        try {
            String namee=namea.get(position).toString();
            String image=list.get(position).toString();
            Picasso.with(activity.getApplicationContext()).load(image).placeholder(R.drawable.post_placholder).into(imageView);
            textView.setText(namee);
            proifile_string=imagea.get(position).toString();
            text.setText("# "+caption_list.get(position).toString());
            Picasso.with(activity.getApplicationContext()).load(proifile_string).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.ic_person).into(imageView1, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(activity.getApplicationContext()).load(proifile_string).placeholder(R.drawable.ic_person).into(imageView1);
                }
            });
        }
        catch (NullPointerException e)
        {
            Intent intent=new Intent(activity.getApplicationContext(),main_activity.class);
            activity.overridePendingTransition(0,0);
            activity.startActivity(intent);
           activity.overridePendingTransition(0,0);
            e.printStackTrace();
        }


        return view;
    }

}
