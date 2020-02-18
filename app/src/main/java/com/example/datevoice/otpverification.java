package com.example.datevoice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class otpverification extends Activity {
  FirebaseAuth auth;
  Button verify;
  String receive;
  EditText verifyeditext;
  FirebaseUser user;
  TextView terms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otplayout);

        verify=(Button)findViewById(R.id.verifyid) ;
        verifyeditext=(EditText)findViewById(R.id.verifyedittextid) ;
        terms=(TextView)findViewById(R.id.textterms) ;
        Intent intent=getIntent();
        receive=intent.getStringExtra("code");
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                codsent();
            }
        });
        terms.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new AlertDialog.Builder(otpverification.this).setMessage("Tis application access your sms and internet and Storage " +
                        "\n" +
                        "and more because these want it"
                        ).setNegativeButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                return true;
            }
        });

    }
   private void codsent()
    {
        String verifycode=verifyeditext.getText().toString();

        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(receive,verifycode);
        signin(credential);
    }
    public void signin(PhoneAuthCredential credential)
    {
        auth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(otpverification.this, "success", Toast.LENGTH_SHORT).show();
                Intent profileintent=new Intent(otpverification.this,profile.class);
                startActivity(profileintent);
                overridePendingTransition(0,0);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(otpverification.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
