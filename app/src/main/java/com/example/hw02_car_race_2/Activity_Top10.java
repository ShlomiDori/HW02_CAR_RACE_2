package com.example.hw02_car_race_2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;


public class Activity_Top10 extends AppCompatActivity {

    private Fragment_List fragmentList;
    private Fragment_Map fragmentMap;
    private Button list_BTN_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_10);
        findViews();
        list_BTN_main.setOnClickListener(mainButtonListener);
        fragmentList = new Fragment_List();
        fragmentList.setActivity(Activity_Top10.this);
        fragmentList.setCallBackList(callBack_list);
        getSupportFragmentManager().beginTransaction().add(R.id.frame1, fragmentList).commit();

        //map
        fragmentMap = new Fragment_Map();
        fragmentMap.setActivity(this);
        getSupportFragmentManager().beginTransaction().add(R.id.frame2,fragmentMap).commit();

        Bundle bundle = getIntent().getBundleExtra(Activity_Menu.BUNDLE);
        double lat = bundle.getDouble(Activity_Game.LAT);
        double lng = bundle.getDouble(Activity_Game.LNG);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    View.OnClickListener mainButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    CallBack_List callBack_list;

    {
        callBack_list = new CallBack_List() {
            @Override
            public void setMapLocation(double lat, double lng) {
                fragmentMap.setFocusOnMapByLocation(new LatLng(lat, lng));
            }
        };
    }

    private void findViews() {
        list_BTN_main = findViewById(R.id.list_BTN_main);
    }
}