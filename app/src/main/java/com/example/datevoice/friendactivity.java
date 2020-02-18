package com.example.datevoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class friendactivity extends Activity {
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference data,data1;
    ArrayList imagelist=new ArrayList();
    ArrayList namelist=new ArrayList();
    ArrayList userid=new ArrayList();
    ArrayList status=new ArrayList();
    ListView listview;
    Coustomadapter coustomadapter;
    Button escape;
    SearchView searchView;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendlayout);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        data=firebaseDatabase.getReference("User");
        listview=(ListView)findViewById(R.id.friendslist);
        searchView=(SearchView)findViewById(R.id.search_friends);
         coustomadapter=new Coustomadapter(this,imagelist,namelist);
    listview.setAdapter(coustomadapter);
        escape=(Button)findViewById(R.id.escape_id);
        escape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(friendactivity.this,main_activity.class);
                overridePendingTransition(0,0);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(namelist.contains(query))
                {
                    coustomadapter.getFilter().filter(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String i=parent.getItemAtPosition(position).toString();

            for(int ii=0;ii<namelist.size();ii++)
            {
                if(i==namelist.get(ii))
                {
                    String sg=userid.get(position).toString();
                    String name=namelist.get(position).toString();
                    String statussent=status.get(position).toString();
                    String profilephoto=imagelist.get(position).toString();
                        Intent uidintent=new Intent(friendactivity.this,friendrequest.class);
                        uidintent.putExtra("id",sg);
                        uidintent.putExtra("name",name);
                        uidintent.putExtra("status",statussent);
                    uidintent.putExtra("image",profilephoto);
                        startActivity(uidintent);
                    overridePendingTransition(0,0);


                }

            }
        }
    });



        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
               for(DataSnapshot data:dataSnapshot.getChildren()) {


                       String image = data.child("profile").getValue(String.class);
                       String getkey = data.getKey();
                       String name = data.child("Name").getValue(String.class);
                       String statusget = data.child("Status").getValue(String.class);
                       imagelist.add(image);
                       namelist.add(name);
                       userid.add(getkey);
                       status.add(statusget);
                       coustomadapter.notifyDataSetChanged();
                   }

               } catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent backintent=new Intent(friendactivity.this,main_activity.class);
        startActivity(backintent);
        overridePendingTransition(0,0);
        super.onBackPressed();
    }
}
