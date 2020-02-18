package com.example.datevoice;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends Activity {
    Button savebutton;
    String name;
    String status;
    EditText nameedit,statusedit;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference databaserefernce,databaserefernce1;
    CircleImageView profileimage;
    String image;
    StorageReference storagerefence;
    StorageReference refernce;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepiclayout);
        savebutton=(Button)findViewById(R.id.savebuttonid);
        profileimage=(CircleImageView)findViewById(R.id.profilpicid);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.profilebutton);
        savebutton.startAnimation(animation);
        nameedit=(EditText)findViewById(R.id.nameid);
        statusedit=(EditText)findViewById(R.id.statusid);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        database=FirebaseDatabase.getInstance();
        databaserefernce1=database.getReference("User");
        storagerefence= FirebaseStorage.getInstance().getReference("User");
        if(ContextCompat.checkSelfPermission(profile.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {

        }
        else
        {
            requestpermission();
        }
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageintent=new Intent(Intent.ACTION_PICK);
                imageintent.setType("image/*");
                startActivityForResult(Intent.createChooser(imageintent,"select"),1);
            }
        });
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValue();
            }
        });



    }
    public void setValue() {

        name = nameedit.getText().toString();
        status=statusedit.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
        }
        if (status.isEmpty()) {
            Toast.makeText(this, "Enter your status", Toast.LENGTH_SHORT).show();
        }
        if(image.isEmpty())
        {
            Toast.makeText(this, "Set your photo", Toast.LENGTH_SHORT).show();
        }
        if ((!name.isEmpty())&&(!status.isEmpty())&&(!image.isEmpty()))
        {


            databaserefernce1.child(user.getUid()).child("Uid").setValue(user.getUid());
            databaserefernce1.child(user.getUid()).child("Name").setValue(name);
            databaserefernce1.child(user.getUid()).child("Status").setValue(status);
            Intent openintent=new Intent(profile.this,main_activity.class);
            openintent.putExtra("profile",image);
            openintent.putExtra("name",name);
            startActivity(openintent);
            overridePendingTransition(0,0);
        }
    }
    public void requestpermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(profile.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {



            new AlertDialog.Builder(this).setTitle("Permission Required")
                    .setMessage("This permission needed for verification code")
                    .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(profile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        }
                    }).setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();

                }
            }).create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1)
        {
            if(grantResults.length>0 &&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode==1)&&(resultCode==RESULT_OK))
        {
            Uri u=data.getData();
            profileimage.setImageURI(u);
            databaserefernce=database.getReference("User").child(user.getUid()).child("profie").push();
            String pushvalue=databaserefernce.getKey();
            refernce=storagerefence.child("profile images").child(pushvalue+".jpg");
            refernce.putFile(u).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    refernce.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                             image=uri.toString();
                            databaserefernce1.child(user.getUid()).child("profile").setValue(image);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(profile.this, "pic not upload", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }
    }}

