package com.example.hw01_car_race;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Panel extends AppCompatActivity {

    private final int MAX_LIVES = 3;

    private ImageView panel_IMG_stone;
    private ImageView[] panel_IMG_hearts;
    private TextView panel_LBL_score;
    private Button panel_BTN_right;
    private Button panel_BTN_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

    }

    private void findViews() {
        panel_IMG_stone = findViewById(R.id.panel_IMG_stone);
        panel_BTN_right = findViewById(R.id.panel_BTN_right);
        panel_BTN_left = findViewById(R.id.panel_BTN_left);

        panel_IMG_hearts = new ImageView[]{
                findViewById(R.id.panel_IMG_heart),
                findViewById(R.id.panel_IMG_heart2),
                findViewById(R.id.panel_IMG_heart3)
        };
    }
}


