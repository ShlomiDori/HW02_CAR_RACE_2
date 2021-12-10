package com.example.hw02_car_race_2;

import android.app.Application;

import com.example.hw02_car_race_2.objects.MSPV3;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MSPV3.initHelper(this);
    }
}
