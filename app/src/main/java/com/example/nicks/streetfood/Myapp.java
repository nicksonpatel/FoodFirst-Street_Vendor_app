package com.example.nicks.streetfood;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Meet on 10-04-2018.
 */

public class Myapp extends Application {

    public static SharedPreferences pref;
    public static String myid;
    public static FirebaseDatabase db;
    public static DatabaseReference ref;
    static Context con;
    @Override
    public void onCreate() {
        super.onCreate();
        db=FirebaseDatabase.getInstance();
        ref=db.getReference();
        con=getApplicationContext();
        pref=getSharedPreferences("myinfo",MODE_PRIVATE);
        myid=pref.getString("myid","");
    }

    static void showMsg(String msg)
    {
        Toast.makeText(con,msg,Toast.LENGTH_LONG).show();
    }
}
