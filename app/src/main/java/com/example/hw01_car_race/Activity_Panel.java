
package com.example.hw01_car_race;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Activity_Panel extends AppCompatActivity {

    private static final int VIBRATE_TIME = 500;
    private ImageView[][] path;
    private int[][] vals ;
    private int playerPos;
    private final int MAX_LIVES = 3 , COLS = 3 , ROWS =5 , PLAYER = 3 ;
    private ImageView[] panel_IMG_hearts,panel_IMG_witchs;
    private ImageButton panel_BTN_right,panel_BTN_left;
    private Timer timer = new Timer();
    private int clock = 0 , lives=MAX_LIVES;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        runGame();

    }
    @Override
    protected void onStart() {
        super.onStart();
        startTicker();
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
                {findViewById(R.id.demo_IMG_30), findViewById(R.id.demo_IMG_31), findViewById(R.id.demo_IMG_32)},
                {findViewById(R.id.panel_IMG_witch), findViewById(R.id.panel_IMG_witch2), findViewById(R.id.panel_IMG_witch3)}
        };
        vals = new int[ROWS][COLS];
        //init vals
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                vals[i][j] = 0;
            }
        }
    }


    private void initViews() {
        panel_BTN_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playerPos == 0) {//player on the left move to mid
                    panel_IMG_witchs[0].setVisibility(View.INVISIBLE);
                    panel_IMG_witchs[1].setImageResource(R.drawable.img_white_witch);
                    panel_IMG_witchs[1].setVisibility(View.VISIBLE);
                    playerPos = 1;
                } else if (playerPos == 1) {//player on the mid move to right
                    panel_IMG_witchs[1].setVisibility(View.INVISIBLE);
                    panel_IMG_witchs[2].setImageResource(R.drawable.img_white_witch);
                    panel_IMG_witchs[2].setVisibility(View.VISIBLE);
                    playerPos = 2;
                } else if (playerPos == 2) { //player on the right cant move
                    panel_IMG_witchs[2].setImageResource(R.drawable.img_white_witch);
                    panel_IMG_witchs[2].setVisibility(View.VISIBLE);
                    playerPos = 2;
                }
            }
        });

        panel_BTN_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playerPos == 2) {//player on the right move to mid
                    panel_IMG_witchs[2].setVisibility(View.INVISIBLE);
                    panel_IMG_witchs[1].setImageResource(R.drawable.img_white_witch);
                    panel_IMG_witchs[1].setVisibility(View.VISIBLE);
                    playerPos = 1;
                } else if (playerPos == 1) {//player on the mid move to left
                    panel_IMG_witchs[1].setVisibility(View.INVISIBLE);
                    panel_IMG_witchs[0].setImageResource(R.drawable.img_white_witch);
                    panel_IMG_witchs[0].setVisibility(View.VISIBLE);
                    playerPos= 0;
                } else if (playerPos== 0) { //player on the right cant move
                    panel_IMG_witchs[0].setImageResource(R.drawable.img_white_witch);
                    panel_IMG_witchs[0].setVisibility(View.VISIBLE);
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runGame();
                    }
                });
            }
        }, 0, 1000);
    }
    private void runGame()
    {
        checkCrash();
        runLogic();
        updateView();
        moveRock();

    }
    private void stopTicker() {
        timer.cancel();
    }

    private void checkCrash() {
        for (int i = 0; i < vals[vals.length - 1].length; i++) {
            if(vals[vals.length- 1][i] == 1 && playerPos == i){
                for (int j = panel_IMG_hearts.length - 1; j >= 0; j--) {
                    if(panel_IMG_hearts[j].getVisibility() == View.VISIBLE){
                        panel_IMG_hearts[j].setVisibility(View.INVISIBLE);
                        Toast.makeText(Activity_Panel.this, "HIT!", Toast.LENGTH_SHORT).show();
                        vibrate(VIBRATE_TIME);
                        return;
                    }if (panel_IMG_hearts[0].getVisibility() == View.INVISIBLE){
                        Toast.makeText(Activity_Panel.this, "Game Over!", Toast.LENGTH_LONG).show();
                        vibrate(VIBRATE_TIME);
                        stopTicker();
                        return;

                    }
                }
            }
        }
    }

    private void runLogic() {

        if(clock++ % 2 == 0)
        {
            Random r = new Random();
            vals[0][r.nextInt(vals[0].length)] =1 ;

        }else{
            for (int j = 0; j < COLS; j++) {
                vals[0][j] = 0 ;
            }
        }
    }
    private void updateView()
    {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                ImageView im = path[i][j];
                if (vals[i][j] == 1) {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.img_rock);
                    panel_IMG_witchs[playerPos].setImageResource(R.drawable.img_white_witch);
                    panel_IMG_witchs[playerPos].setVisibility(View.VISIBLE);
                } else if (vals[i][j] == 0) {
                    im.setVisibility(View.INVISIBLE);
                    panel_IMG_witchs[playerPos].setImageResource(R.drawable.img_white_witch);
                    panel_IMG_witchs[playerPos].setVisibility(View.VISIBLE);
                }
            }
        }
    }
    private void moveRock() {

        for (int i = ROWS - 1; i >= 0; i--) {
            for (int j = 0; j <COLS; j++){
                if(i>0)
                {
                    vals[i][j] = vals[i-1][j];
                }

            }
        }

    }

    private void vibrate(int timer) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(VIBRATE_TIME, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(timer);
        }
    }
}

