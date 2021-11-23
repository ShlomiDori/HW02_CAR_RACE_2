package com.example.hw02_car_race_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class Activity_Menu extends AppCompatActivity {

    private MaterialButton menu_BTN_sensors;
    private MaterialButton menu_BTN_light;
    private MaterialButton menu_BTN_top;
    private MaterialButton menu_BTN_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menu_BTN_sensors = findViewById(R.id.menu_BTN_sensors);
        menu_BTN_light = findViewById(R.id.menu_BTN_light);
        menu_BTN_top = findViewById(R.id.menu_BTN_top);
        menu_BTN_exit = findViewById(R.id.menu_BTN_exit);

        menu_BTN_sensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame("Sensor Version");
            }
        });

        menu_BTN_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame("Light Version");
            }
        });
        menu_BTN_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        menu_BTN_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void startGame(String sns) {
        Intent myIntent = new Intent(this, Activity_Panel.class);

        Bundle bundle = new Bundle();
      //  bundle.putString(Activity_Panel.SENSOR_TYPE,sns);
      //  bundle.putString(Activity_Summary.NAME, "Guy");
      //  bundle.putString(Activity_Summary.GENDER, "Female");

        myIntent.putExtra("Bundle", bundle);
        startActivity(myIntent);
    }


}