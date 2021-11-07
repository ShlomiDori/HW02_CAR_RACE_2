package com.example.hw01_car_race;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Activity_Panel extends AppCompatActivity {

    private ImageView[][] path;
    private int[][] vals ;
    private int playerPos;
    private final int MAX_LIVES = 3;
    private ImageView[] panel_IMG_hearts ,panel_IMG_witchs;
    private ImageView panel_IMG_witch ,panel_IMG_witch2,panel_IMG_witch3;
    private ImageButton panel_BTN_right,panel_BTN_left;
    private Timer timer = new Timer();
    private int clock = 2 , lives=MAX_LIVES;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

 //       ImageView demo_IMG_back = findViewById(R.id.demo_IMG_back);
      /*  Glide
                .with(this)
                .load(R.drawable.img_background)
                .centerCrop()
                .placeholder()
                .into(demo_IMG_back);

*/


       checkCrash();
        runlogic();
        updateUI();
    }
    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTicker();
    }

    private void findViews() {
        panel_BTN_right = findViewById(R.id.panel_BTN_right);
        panel_BTN_left = findViewById(R.id.panel_BTN_left);
        panel_IMG_witchs = new ImageView[]{
                findViewById(R.id.panel_IMG_witch),
                findViewById(R.id.panel_IMG_witch2),
                findViewById(R.id.panel_IMG_witch3)
        };
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
        //init vals
        for (int i = 0; i < vals.length; i++) {
            for (int j = 0; j < vals[i].length; j++) {
                vals[i][j] = 0;
            }
        }
        initViews();
//        panel_IMG_witch.setVisibility(View.VISIBLE);
//        panel_IMG_witch2.setVisibility(View.INVISIBLE);
//        panel_IMG_witch3.setVisibility(View.INVISIBLE);
//        playerPos =0;

    }


    private void initViews() {
        panel_BTN_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerPos == 0) {//player on the left move to mid
                    panel_IMG_witchs[0].setVisibility(View.INVISIBLE);
                    panel_IMG_witchs[1].setVisibility(View.VISIBLE);
                    playerPos = 1;
                } else if (playerPos == 1) {//player on the mid move to right
                    panel_IMG_witchs[1].setVisibility(View.INVISIBLE);
                    panel_IMG_witchs[2].setVisibility(View.VISIBLE);
                    playerPos = 2;
                } else if (playerPos == 2) { //player on the right cant move
                    playerPos = 2;
                }
            }
        });

        panel_BTN_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerPos == 2) {//player on the right move to mid
                    panel_IMG_witchs[2].setVisibility(View.INVISIBLE);
                    panel_IMG_witchs[1].setVisibility(View.VISIBLE);
                    playerPos = 1;
                } else if (playerPos == 1) {//player on the mid move to left
                    panel_IMG_witchs[1].setVisibility(View.INVISIBLE);
                    panel_IMG_witchs[0].setVisibility(View.VISIBLE);
                    playerPos = 0;
                } else if (playerPos == 0) { //player on the right cant move
                    playerPos = 0;
                }
            }
        });
    }


    private void startTicker() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d("pttt", "A clock=" + clock + " Thread=" + Thread.currentThread().getName());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("pttt", "B clock=" + clock + " Thread=" + Thread.currentThread().getName());
                        updateClockView();
                    }
                });
            }
        }, 0, 2000);
    }

    private void stopTicker() {
        timer.cancel();
    }

//    private void updateClockView() {
//        //update every 2 sec the matrix.
//        for (int i = vals.length - 1; i >= 0; i--) {
//            for (int j = 0; j < vals[i].length; j++){
//                if(i > 0){
//                    vals[i][j] = vals[i-1][j];
//                }
//            }
//        }
//    }
    private void updateClock() {
        clock—-;
       // moveEnemy();
        if(clock==0){
            vals[0][(int) (Math.random() * (3) + 0)] = 1;
            clock=5;
        }
    }

    private void checkCrash() {

        for (int i = 0; i < vals[vals.length - 1].length; i++) {
            if(vals[vals.length- 1][i] == 1 && playerPos == i){
                updateLivesViews();
            }
        }
    }
    private void updateLivesViews() {
        for (int i = panel_IMG_hearts.length-1; i >= 0; i--) {
            if ((i+1) >= lives) {
                panel_IMG_hearts[i].setVisibility(View.VISIBLE);
            } else {
                panel_IMG_hearts[i].setVisibility(View.INVISIBLE);
            }
        }
    }
    private void runlogic() {
        for (int j = 0; j < vals[0].length; j++) {
            if(j == 0) {
                vals[0][j] = 1;
            } else {
                vals[0][j] = 0;
            }
        }
    //    shuffleArray(vals);
    }

    private void shuffleArray(int[][] vals) {
        for (int i = 0; i < path.length; i++) {
         //   Collections.shuffle(vals[i]);

        }
    }


//    private void shuffleVals()
//    {
//        Random r = new Random();
//        int min =0 ,max=3;
//        for (int i = 0; i < path.length; i++) {
//            for (int j = 0; j < path[i].length; j++) {
//                vals[i][j] = r.nextInt((max-min)+1);
//
//            }
//        }
//    }
    private void updateUI() {
       // shuffleVals();
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[i].length; j++) {
                ImageView im = path[i][j];
                if (vals[i][j] == 0 ) {
                    im.setVisibility(View.INVISIBLE);
                } else if (vals[i][j] == 1) {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.img_rock);
                }
                //else if (vals[i][j] == 2) {
                  //  im.setVisibility(View.VISIBLE);
                  //  im.setImageResource(R.drawable.img_rock);
                  //  im.setVisibility(View.INVISIBLE);
                //}
            }
        }
    }
}

