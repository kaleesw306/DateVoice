package com.example.datevoice;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class phonenumberauth extends Activity {
    LottieAnimationView a;
    EditText phonenumberedittext;
    Button number1,number2,number3,number4,number5,number6,number7,number8,number9,number0,clear,register;
    String storevalue=new String();
    CardView clearcardview,registercard;
PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
String CODE;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phoneauthlayout);
        phonenumberedittext =(EditText)findViewById(R.id.phonenumberedittextid);
       number1=(Button)findViewById(R.id.number1id);
        number2=(Button)findViewById(R.id.number2id);
        number3=(Button)findViewById(R.id.number3id);
        number4=(Button)findViewById(R.id.number4id);
        number5=(Button)findViewById(R.id.number5id);
        number6=(Button)findViewById(R.id.number6id);
        number7=(Button)findViewById(R.id.number7id);
        number8=(Button)findViewById(R.id.number8id);
        number9=(Button)findViewById(R.id.number9id);
        number0=(Button)findViewById(R.id.number0id);
        clear=(Button)findViewById(R.id.clearid);
        clearcardview=(CardView)findViewById(R.id.clearcardviewid);
        register=(Button)findViewById(R.id.registerid) ;
        registercard=(CardView)findViewById(R.id.registercardview);
        if(ContextCompat.checkSelfPermission(phonenumberauth.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
        {

        }
        else
        {
          request();
        }
       numbers();
       clearcardview.setVisibility(View.INVISIBLE);
       clear.setVisibility(View.INVISIBLE);
       register.setVisibility(View.INVISIBLE);
       registercard.setVisibility(View.INVISIBLE);
     register.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

         }
     });
   callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
       @Override
       public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

       }

       @Override
       public void onVerificationFailed(FirebaseException e) {
           Toast.makeText(phonenumberauth.this, "failed", Toast.LENGTH_SHORT).show();
       }


       @Override
       public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
           super.onCodeSent(s, forceResendingToken);
           CODE=s;
           Toast.makeText(phonenumberauth.this, "code sent successfully", Toast.LENGTH_SHORT).show();
           Intent intent =new Intent(phonenumberauth.this,otpverification.class);
           intent.putExtra("code",CODE);
           startActivity(intent);
           overridePendingTransition(0,0);
           finish();
       }
   };
   register.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           sentcode();
       }
   });
    }
    public void numbers()
    {
        number1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storevalue=storevalue+""+1;
                phonenumberedittext.setText(storevalue);
                if(storevalue.length()>=1)
                {
                    clearcardview.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                }
                if(storevalue.length()==10)
                {
                    registercard.setVisibility(View.VISIBLE);
                    register.setVisibility(View.VISIBLE);
                }
            }
        });
        number2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storevalue=storevalue+""+2;
                phonenumberedittext.setText(storevalue);
                if(storevalue.length()>=1)
                {
                    clearcardview.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                }
                if(storevalue.length()==10)
                {
                    registercard.setVisibility(View.VISIBLE);
                    register.setVisibility(View.VISIBLE);
                }
            }
        });
        number3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storevalue=storevalue+""+3;
                phonenumberedittext.setText(storevalue);
                if(storevalue.length()>=1)
                {
                    clearcardview.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                }
                if(storevalue.length()==10)
                {
                    registercard.setVisibility(View.VISIBLE);
                    register.setVisibility(View.VISIBLE);
                }
            }
        });
        number4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storevalue=storevalue+""+4;
                phonenumberedittext.setText(storevalue);
                if(storevalue.length()>=1)
                {
                    clearcardview.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                }
                if(storevalue.length()==10)
                {
                    registercard.setVisibility(View.VISIBLE);
                    register.setVisibility(View.VISIBLE);
                }
            }
        });
        number5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storevalue=storevalue+""+5;
                phonenumberedittext.setText(storevalue);
                if(storevalue.length()>=1)
                {
                    clearcardview.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                }
                if(storevalue.length()==10)
                {
                    registercard.setVisibility(View.VISIBLE);
                    register.setVisibility(View.VISIBLE);
                }
            }
        });
        number6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storevalue=storevalue+""+6;
                phonenumberedittext.setText(storevalue);
                if(storevalue.length()>=1)
                {
                    clearcardview.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                }
                if(storevalue.length()==10)
                {
                    registercard.setVisibility(View.VISIBLE);
                    register.setVisibility(View.VISIBLE);
                }
            }
        });
        number7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storevalue=storevalue+""+7;
                phonenumberedittext.setText(storevalue);
                if(storevalue.length()>=1)
                {
                    clearcardview.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                }
                if(storevalue.length()==10)
                {
                    registercard.setVisibility(View.VISIBLE);
                    register.setVisibility(View.VISIBLE);
                }
            }
        });
        number8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storevalue=storevalue+""+8;
                phonenumberedittext.setText(storevalue);
                if(storevalue.length()>=1)
                {
                    clearcardview.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                }
                if(storevalue.length()==10)
                {
                    registercard.setVisibility(View.VISIBLE);
                    register.setVisibility(View.VISIBLE);
                }
            }
        });
        number9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storevalue=storevalue+""+9;
                phonenumberedittext.setText(storevalue);
                if(storevalue.length()==10)
                {
                    registercard.setVisibility(View.VISIBLE);
                    register.setVisibility(View.VISIBLE);
                }
                if(storevalue.length()>=1)
                {
                    clearcardview.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                }
            }
        });
        number0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storevalue=storevalue+""+0;
                phonenumberedittext.setText(storevalue);
                if(storevalue.length()==10)
                {
                    registercard.setVisibility(View.VISIBLE);
                    register.setVisibility(View.VISIBLE);
                }
                if(storevalue.length()>=1)
                {
                    clearcardview.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                }
            }
        });
  clear.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          storevalue=new String();
          phonenumberedittext.setText("");
          Animation animation= AnimationUtils.loadAnimation(phonenumberauth.this,R.anim.animation2);
          clearcardview.setAnimation(animation);
          clear.startAnimation(animation);

      }
  });


    }

    public void sentcode()
    {
        String phonenumber=phonenumberedittext.getText().toString();
        String indianumber="+91"+phonenumber;
      //  Toast.makeText(this, indianumber, Toast.LENGTH_SHORT).show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(indianumber,60, TimeUnit.SECONDS,phonenumberauth.this,callbacks);
        LayoutInflater layoutinflater=getLayoutInflater();
        View view=layoutinflater.inflate(R.layout.phonenumberanimation,null);
        new AlertDialog.Builder(this).setView(view).create().show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            Intent openintent=new Intent(phonenumberauth.this,main_activity.class);
            startActivity(openintent);
            overridePendingTransition(0,0);
        }
        else {
                }
    }
    public void request()
    {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
        {
            if((ContextCompat.checkSelfPermission(phonenumberauth.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)&&
                    ((ContextCompat.checkSelfPermission(phonenumberauth.this, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED)))
            {
                Toast.makeText(phonenumberauth.this, "PERMISSION GRANTED FOR ACCESS STORAGE", Toast.LENGTH_SHORT).show();
            }
            else {
                requestInterper();
            }
        }}
    public void requestInterper()
    {
        if((ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS))&&((ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECORD_AUDIO))))
        {
            new AlertDialog.Builder(this)
                    .setTitle("NEED PERMISSION")
                    .setMessage("This permission is needed because of Access Storage")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(phonenumberauth.this, new String[]{Manifest.permission.SEND_SMS},1);
                            ActivityCompat.requestPermissions(phonenumberauth.this, new String[]{Manifest.permission.RECORD_AUDIO},1);
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
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1)
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(phonenumberauth.this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(phonenumberauth.this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
