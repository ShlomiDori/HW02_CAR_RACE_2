package com.example.hw01_car_race;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Activity_Panel extends AppCompatActivity {

    private ImageView[][] path;
    private int[][] vals;
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

        ImageView demo_IMG_back = findViewById(R.id.demo_IMG_back);
      /*  Glide
                .with(this)
                .load(R.drawable.img_background)
                .centerCrop()
                .placeholder()
                .into(demo_IMG_back);

*/
        for (int i = 0; i < vals.length; i++) {
            for (int j = 0; j < vals[i].length; j++) {
                vals[i][j] = 0;
            }
        }

        checkCrash();
        runlogic();
        updateUI();
    }

    private void findViews() {
        panel_BTN_right = findViewById(R.id.panel_BTN_right);
        panel_BTN_left = findViewById(R.id.panel_BTN_left);

        panel_IMG_hearts = new ImageView[]{
                findViewById(R.id.panel_IMG_heart),
                findViewById(R.id.panel_IMG_heart2),
                findViewById(R.id.panel_IMG_heart3)
        };

        path = new ImageView[][]{
                {findViewById(R.id.demo_IMG_00), findViewById(R.id.demo_IMG_01), findViewById(R.id.demo_IMG_02)},
                {findViewById(R.id.demo_IMG_10), findViewById(R.id.demo_IMG_11), findViewById(R.id.demo_IMG_12)},
                {findViewById(R.id.demo_IMG_20), findViewById(R.id.demo_IMG_21), findViewById(R.id.demo_IMG_22)},
                {findViewById(R.id.demo_IMG_30), findViewById(R.id.demo_IMG_31), findViewById(R.id.demo_IMG_32)}
        };
        vals = new int[path.length][path[0].length];
    }

    private void checkCrash() {

    }

    private void runlogic() {

    }

    private void updateUI() {
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[i].length; j++) {
                ImageView im = path[i][j];
                if (vals[i][j] == 0) {
                    im.setVisibility(View.INVISIBLE);
                } else if (vals[i][j] == 1) {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.img_rock);
                } else if (vals[i][j] == 2) {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.img_rock);
                }
            }
        }
    }


