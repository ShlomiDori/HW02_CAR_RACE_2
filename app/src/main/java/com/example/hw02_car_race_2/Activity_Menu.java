package com.example.hw02_car_race_2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Activity_Menu extends AppCompatActivity {

    private Button menu_BTN_sensors;
    private Button menu_BTN_light;
    private Button menu_BTN_top;
    private EditText edit_TEXT_name;
    private boolean lightFlag = false;
    private double lat, lng;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public static final String BUNDLE = "BUNDLE";
    private Bundle bundle;
    private String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findView();
        setOnClick();
        // Check location permission
        if (ActivityCompat.checkSelfPermission(Activity_Menu.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(Activity_Menu.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void setOnClick() {
        menu_BTN_sensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_TEXT_name.equals("")){
                    Toast.makeText(Activity_Menu.this, "Must Enter Name!", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity("Sensor Version");
                }

            }
        });

        menu_BTN_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_TEXT_name.equals("")){
                    Toast.makeText(Activity_Menu.this, "Must Enter Name!", Toast.LENGTH_SHORT).show();

                }else {
                    startActivity("Light Version");
                }
            }
        });
        menu_BTN_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity("Top 10");
            }
        });
    }

    private void findView() {
        menu_BTN_sensors = findViewById(R.id.menu_BTN_sensors);
        menu_BTN_light = findViewById(R.id.menu_BTN_light);
        menu_BTN_top = findViewById(R.id.menu_BTN_top);
        edit_TEXT_name = findViewById(R.id.edit_TEXT_name);
    }

    private void startActivity(String sns) {
        Intent myIntent;
        allBundles();
        if (sns.equals("Top 10")) {
            myIntent = new Intent(this, Activity_Top10.class);

        } else {
            if(sns.equals("Light Version")){
                lightFlag=true;
            }
            myIntent = new Intent(this, Activity_Game.class);
            bundle.putString(Activity_Game.LIGHT_FLAG, sns);
        }

        myIntent.putExtra(BUNDLE, bundle);
        startActivity(myIntent);
    }


    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();

                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(Activity_Menu.this,
                                Locale.getDefault());
                        List<Address> addressList = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        lat = addressList.get(0).getLatitude();
                        lng = addressList.get(0).getLongitude();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void allBundles() {
        bundle = new Bundle();
        bundle.putString(Activity_Game.NAME, name);
        bundle.putBoolean(Activity_Game.LIGHT_FLAG, lightFlag);
        bundle.putDouble(Activity_Game.LAT, lat);
        bundle.putDouble(Activity_Game.LNG, lng);
    }
}