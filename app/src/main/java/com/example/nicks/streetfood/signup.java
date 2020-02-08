package com.example.nicks.streetfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    MaterialEditText etname,etphone,etdis;
    ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(!Myapp.myid.equals(""))
        {
            Intent i=new Intent(getApplicationContext(),home.class);
            startActivity(i);
        }
        etname=(MaterialEditText)findViewById(R.id.etname);
        etdis=(MaterialEditText)findViewById(R.id.etdis);
        etphone=(MaterialEditText)findViewById(R.id.etphone);
        btn=(ImageButton)findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etname.getText().toString().equals("")||etdis.getText().toString().equals("")||etphone.getText().toString().equals(""))
                {
                    Myapp.showMsg("Please add all details");
                }
                else
                {
                    Map<String,String> data=new HashMap<>();
                    data.put("name",etname.getText().toString());
                    data.put("dec",etdis.getText().toString());
                    data.put("contact",etphone.getText().toString());
                    final String key=Myapp.ref.child("street").push().getKey();
                    Myapp.ref.child("street").child(key).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Myapp.myid=key;
                            Myapp.showMsg("Successfully Login");
                            SharedPreferences.Editor edit=Myapp.pref.edit();
                            edit.putString("myid",key);
                            edit.commit();

                            Intent i=new Intent(getApplicationContext(),home.class);
                            startActivity(i);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Myapp.showMsg("Try again");
                        }
                    });


                }

            }
        });



    }
}
