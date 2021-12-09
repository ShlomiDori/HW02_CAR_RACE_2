package com.example.hw02_car_race_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.hw02_car_race_2.objects.MyDatabase;
import com.example.hw02_car_race_2.objects.Record;
import com.example.hw02_car_race_2.objects.SortRecordByScore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fragment_List extends Fragment {


    private AppCompatActivity activity;
    private Button[] list_TXT_arr;
    private CallBack_List callBackList;
    private List<Record> topPlayers;
    private final String MY_DB_NAME = "Halloween Game";
    private final String defDbVal = "{\"records\":[]}";
    private final int NUM_OF_PLAYER = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        findViews(view);
        fillList();
        showList();
        initViews();

        return view;
    }

    private void initViews() {
        int size = topPlayers.size();
        switch (size) {
            case 0:
                list_TXT_arr[0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBackList.setMapLocation(topPlayers.get(0).getLat(), topPlayers.get(0).getLng());
                    }
                });
                break;
            case 1:
                list_TXT_arr[1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBackList.setMapLocation(topPlayers.get(1).getLat(), topPlayers.get(1).getLng());
                    }
                });                break;
            case 2:
                list_TXT_arr[2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBackList.setMapLocation(topPlayers.get(2).getLat(), topPlayers.get(2).getLng());
                    }
                });
                break;
            case 3:
                list_TXT_arr[3].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBackList.setMapLocation(topPlayers.get(3).getLat(), topPlayers.get(3).getLng());
                    }
                });                break;
            case 4:
                list_TXT_arr[4].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBackList.setMapLocation(topPlayers.get(4).getLat(), topPlayers.get(4).getLng());
                    }
                });
                break;
            case 5:
                list_TXT_arr[5].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBackList.setMapLocation(topPlayers.get(5).getLat(), topPlayers.get(5).getLng());
                    }
                });
                break;
            case 6:
                list_TXT_arr[6].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBackList.setMapLocation(topPlayers.get(6).getLat(), topPlayers.get(6).getLng());
                    }
                });
                break;
            case 7:
                list_TXT_arr[7].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBackList.setMapLocation(topPlayers.get(7).getLat(), topPlayers.get(7).getLng());
                    }
                });
                break;
            case 8:
                list_TXT_arr[8].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBackList.setMapLocation(topPlayers.get(8).getLat(), topPlayers.get(8).getLng());
                    }
                });
                break;
            case 9:
                list_TXT_arr[9].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBackList.setMapLocation(topPlayers.get(9).getLat(), topPlayers.get(9).getLng());
                    }
                });
                break;
        }
    }

    private void showList() {
        int listSize = topPlayers.size();
        for (int i = 0; i < listSize; i++) {
            list_TXT_arr[i].setText((i + 1) + ") " + (topPlayers.get(i).toString()));
        }

        // If there are less than 10 records --> fill empty lines
        for (int i = listSize; i < NUM_OF_PLAYER; i++) {
            list_TXT_arr[i].setText((i +1 ) + ")\t-----");
        }
    }

    private void fillList() {
        // Fetch database
        String str_db = MSPV3.getMe().getString(MY_DB_NAME, defDbVal);
        MyDatabase my_db = new Gson().fromJson(str_db, MyDatabase.class);

        // Fetch records from database and sort them by score
        ArrayList<Record> records = my_db.getRecords();
        int arrSize = records.size();
        Collections.sort(records, new SortRecordByScore());

        // Get the top ten scores
        int from = 0;
        if (records.size() >= NUM_OF_PLAYER) {
            from = arrSize - 10;
        }
        topPlayers = records.subList(from, arrSize);

        // Reverse list
        Collections.reverse(topPlayers);
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCallBackList(CallBack_List callBackList) {
        this.callBackList = callBackList;
    }

    private void findViews(View view) {
        list_TXT_arr = new Button[] {
                view.findViewById(R.id.list_TXT_1),
                view.findViewById(R.id.list_TXT_2),
                view.findViewById(R.id.list_TXT_3),
                view.findViewById(R.id.list_TXT_4),
                view.findViewById(R.id.list_TXT_5),
                view.findViewById(R.id.list_TXT_6),
                view.findViewById(R.id.list_TXT_7),
                view.findViewById(R.id.list_TXT_8),
                view.findViewById(R.id.list_TXT_9),
                view.findViewById(R.id.list_TXT_10)
        };
    }
}