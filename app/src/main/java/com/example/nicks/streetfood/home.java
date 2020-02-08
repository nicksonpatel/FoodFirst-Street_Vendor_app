package com.example.nicks.streetfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class home extends AppCompatActivity {
    private FusedLocationProviderClient mFusedLocationClient;
    double lati,longi;
    ImageView lgout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getCurrLocation();

        lgout=(ImageView)findViewById(R.id.logout);
        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLogout();
            }
        });
    }
    void getCurrLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            try {
                                String fulladd="";
                                lati = location.getLatitude();
                                longi = location.getLongitude();
                                Myapp.ref.child("street").child(Myapp.myid).child("lati").setValue(""+lati);

                                Myapp.ref.child("street").child(Myapp.myid).child("longi").setValue(""+longi);
                                Geocoder code = new Geocoder(getApplicationContext(), Locale.ENGLISH);
                                List<Address> list = code.getFromLocation(lati, longi, 10);
                                Address add;
                                String myadd[];
                                add = list.get(0);
                                myadd = new String[add.getMaxAddressLineIndex()];
                                //fulladd=fulladd+"\nlati"+lati+" longi:"+longi;


                                String postalcode = add.getPostalCode();
                                String city = add.getLocality();
                                String area = add.getSubLocality();
                                String adds = add.getThoroughfare() + " , " + area + " , " + postalcode;








                                // Toast.makeText(getApplicationContext(), "geting :"+fulladd, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Error 1 :" + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                });
    }


    void getLogout()
    {
        Myapp.ref.child("street").child(Myapp.myid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
           Myapp.myid="";
                SharedPreferences.Editor e=Myapp.pref.edit();
                e.clear();
                e.commit();
                Intent i=new Intent(getApplicationContext(),signup.class);
                startActivity(i);
                Myapp.showMsg("Successfully logout");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Myapp.showMsg("try again");
            }
        });
    }

}
