package com.example.hw02_car_race_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class Fragment_List extends Fragment {

    private MaterialTextView frame1_LBL_title;
    private TextInputLayout frame1_EDT_name;
    private MaterialButton frame1_BTN_refresh;

    private AppCompatActivity activity;

    private CallBack_List callBackList;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCallBackList(CallBack_List callBackList) {
        this.callBackList = callBackList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews();


        return view;
    }

    private String getName() {
        return frame1_EDT_name.getEditText().getText().toString();
    }

    public void setTitle(String str) {
        MSPV3.getMe().putInt("NUM_OF", 1);

        frame1_LBL_title.setText(str);
    }

    private void initViews() {
        frame1_BTN_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBackList != null) {
                    callBackList.setMainTitle(getName());
                }
            }
        });
    }

    private void findViews(View view) {
        frame1_LBL_title = view.findViewById(R.id.frame1_LBL_title);
        frame1_EDT_name = view.findViewById(R.id.frame1_EDT_name);
        frame1_BTN_refresh = view.findViewById(R.id.frame1_BTN_refresh);
    }
}