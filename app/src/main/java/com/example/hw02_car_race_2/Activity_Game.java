
package com.example.hw02_car_race_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hw02_car_race_2.objects.MSPV3;
import com.example.hw02_car_race_2.objects.MyDatabase;
import com.example.hw02_car_race_2.objects.Record;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



public class Activity_Game extends AppCompatActivity {

    public static final String LAT = "LAT";
    public static final String LNG = "LNG";
    public static final String NAME = "name";
    public static final String LIGHT_FLAG = "LIGHT_FLAG";
    private static final String DATE_PATTERN ="dd/mm/yyyy" ;
    private final String DB_NAME = "HALLOWEEN_GAME_DB";
    private final String DBVal = "{\"records\":[]}";
    private static Sensor accSensor;
    private static SensorManager sensorManager;

    private static final int VIBRATE_TIME = 500;
    private final int MAX_LIVES = 3 , COLS = 5 , ROWS =5;
    private int clock = 0 , lives=MAX_LIVES;
    private int scoreCount = 0,playerPos;
    private double lat = 0, lng = 0;

    private ImageView[][] path;
    private int[][] vals ;
    private ImageView[] panel_IMG_hearts,panel_IMG_witchs;
    private ImageButton panel_BTN_right,panel_BTN_left;
    private Timer timer = new Timer();
    private TextView score;
    private static Bundle bundle;
    private String name = "";
    private MediaPlayer crashSound , coinSound;
    private boolean lightFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        findViews();

        openBundles();
        if(lightFlag){
            initViews();
        }else{
            removeButtons();
            initSensor();
        }

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
        //updateDatabase();
    }
    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public static Bundle getBundles() {
        return bundle;
    }

    private void openBundles() {
        bundle = getIntent().getBundleExtra(Activity_Menu.BUNDLE);
        name = bundle.getString(NAME);
        lightFlag = bundle.getBoolean(LIGHT_FLAG);
        lat = bundle.getDouble(LAT);
        lng = bundle.getDouble(LNG);
    }

    private void findViews() {
        panel_BTN_right = findViewById(R.id.panel_BTN_right);
        panel_BTN_left = findViewById(R.id.panel_BTN_left);
        panel_IMG_witchs = new ImageView[]{
                findViewById(R.id.panel_IMG_witch),
                findViewById(R.id.panel_IMG_witch2),
                findViewById(R.id.panel_IMG_witch3),
                findViewById(R.id.panel_IMG_witch4),
                findViewById(R.id.panel_IMG_witch5)
        };
        panel_IMG_hearts = new ImageView[]{
                findViewById(R.id.panel_IMG_heart),
                findViewById(R.id.panel_IMG_heart2),
                findViewById(R.id.panel_IMG_heart3)
        };

        path = new ImageView[][]{
        {findViewById(R.id.demo_IMG_00), findViewById(R.id.demo_IMG_01), findViewById(R.id.demo_IMG_02),findViewById(R.id.demo_IMG_03),findViewById(R.id.demo_IMG_04)},
        {findViewById(R.id.demo_IMG_10), findViewById(R.id.demo_IMG_11), findViewById(R.id.demo_IMG_12),findViewById(R.id.demo_IMG_13),findViewById(R.id.demo_IMG_14)},
        {findViewById(R.id.demo_IMG_20), findViewById(R.id.demo_IMG_21), findViewById(R.id.demo_IMG_22),findViewById(R.id.demo_IMG_23),findViewById(R.id.demo_IMG_24)},
        {findViewById(R.id.demo_IMG_30), findViewById(R.id.demo_IMG_31), findViewById(R.id.demo_IMG_32),findViewById(R.id.demo_IMG_33),findViewById(R.id.demo_IMG_34)},
        {findViewById(R.id.demo_IMG_40), findViewById(R.id.demo_IMG_41), findViewById(R.id.demo_IMG_42),findViewById(R.id.demo_IMG_43),findViewById(R.id.demo_IMG_44)},
        {findViewById(R.id.panel_IMG_witch), findViewById(R.id.panel_IMG_witch2), findViewById(R.id.panel_IMG_witch3), findViewById(R.id.panel_IMG_witch4), findViewById(R.id.panel_IMG_witch5)}
        };
        vals = new int[ROWS][COLS];
        //init vals
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                vals[i][j] = 0;
            }
        }
        score = findViewById(R.id.duaration);
        crashSound = MediaPlayer.create(this,R.raw.crash_sound);
        coinSound = MediaPlayer.create(this,R.raw.coins_sound);
    }


    private void initViews() {
//        remove the player
        panel_BTN_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerPos == 0) {//player on the left move to left mid
                    setVisibleFromTo(panel_IMG_witchs,0,1);

                } else if (playerPos == 1) {//player on the left mid move to mid
                    setVisibleFromTo(panel_IMG_witchs,1,2);

                } else if (playerPos == 2) { //player on the mid move to right mid
                    setVisibleFromTo(panel_IMG_witchs,2,3);

                } else if (playerPos == 3) { //player on the right mid move to right
                    setVisibleFromTo(panel_IMG_witchs,3,4);

                }else if (playerPos == 4) { //player on the right move cant move
                    setVisibleFromTo(panel_IMG_witchs,4,4);
                }
            }
        });

        panel_BTN_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerPos == 4) {//player on the right move to right mid
                    setVisibleFromTo(panel_IMG_witchs,4,3);

                } else if (playerPos == 3) {//player on the right mid move to mid
                    setVisibleFromTo(panel_IMG_witchs,3,2);

                } else if (playerPos == 2) { //player on the mid move to left mid
                    setVisibleFromTo(panel_IMG_witchs,2,1);
                }

                else if (playerPos == 1) { //player on the  lrft mid move to left
                    setVisibleFromTo(panel_IMG_witchs,1,0);
                }

                else if (playerPos == 0) { //player on the left cant move
                    setVisibleFromTo(panel_IMG_witchs,0,0);
                }
            }
        });
    }

    private void setVisibleFromTo(ImageView[] witchs, int from, int to) {
        //        move the player from current location to new location
        if(to != from){
            witchs[from].setVisibility(View.INVISIBLE);
        }
        witchs[to].setImageResource(R.drawable.img_white_witch);
        witchs[to].setVisibility(View.VISIBLE);
        playerPos = to;
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
                scoreCount -=500;
                for (int j = panel_IMG_hearts.length - 1; j >= 0; j--) {
                    if(panel_IMG_hearts[j].getVisibility() == View.VISIBLE){
                        panel_IMG_hearts[j].setVisibility(View.INVISIBLE);
                        Toast.makeText(Activity_Game.this, "HIT!", Toast.LENGTH_SHORT).show();
                        vibrate(VIBRATE_TIME);
                        crashSound.start();
                        return;
                    }if (panel_IMG_hearts[0].getVisibility() == View.INVISIBLE){
                        crashSound.start();
                        Toast.makeText(Activity_Game.this, "Game Over!", Toast.LENGTH_LONG).show();
                        vibrate(VIBRATE_TIME);
                        stopTicker();
                        return;

                    }

                }
            }else if (vals[vals.length- 1][playerPos] == 2 ){
                coinSound.start();
                scoreCount +=1000;
                Toast.makeText(Activity_Game.this, "WOW!", Toast.LENGTH_SHORT).show();
                vibrate(VIBRATE_TIME);
                return;
            }
        }

    }

    private void runLogic() {

        if(clock++ % 2 == 0)
        {
            Random r = new Random();
            vals[0][r.nextInt(vals[0].length)] =1 ;
            int randomCoin = r.nextInt(vals[0].length-1);
            if(randomCoin >=  vals[0][r.nextInt(vals[0].length)])
            {
                randomCoin+=1;
            }
            vals[0][randomCoin] =2;
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
                    setVisibleFromTo(panel_IMG_witchs,playerPos,playerPos);

                } else if (vals[i][j] == 0) {
                    im.setVisibility(View.INVISIBLE);
                    setVisibleFromTo(panel_IMG_witchs,playerPos,playerPos);

                }
                else if (vals[i][j] == 2) {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.img_coin_bitcoin);
                    setVisibleFromTo(panel_IMG_witchs,playerPos,playerPos);
                }
            }
        }
    }
    private void moveRock() {
        scoreCount +=100;
        score.setText(""+ scoreCount);
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
//    private void updateDatabase() {
//        // Fetch database as json
//        String str_db = MSPV3
//                .getMe()
//                .getString(
//                        DB_NAME, // key
//                        DBVal    // def value
//                );
//        // Convert json to object
//        MyDatabase my_db = new Gson()
//                .fromJson(
//                        str_db,
//                        MyDatabase.class
//                );
//
//        DateFormat date = new SimpleDateFormat(DATE_PATTERN);
//        String tempDate= date.format(Calendar.getInstance().getTime());
//        // Create a record and store it in my_db
//        Record record = new Record()
//                .setDate(tempDate)
//                .setName(name)
//                .setLat(lat)
//                .setLng(lng)
//                .setScore(scoreCount);
//
//        cleanRecordsBelowOneThousandScore(my_db);
//        my_db.getRecords().add(record);
//
//        // Store my_db in app shared preferences
//        String json = new Gson().toJson(my_db);
//        MSPV3
//                .getMe()
//                .putString(
//                        DB_NAME,
//                        json
//                );
//    }

    private void cleanRecordsBelowOneThousandScore(@NonNull MyDatabase my_db) {
        ArrayList<Record> top3 = new ArrayList<>();
        ArrayList<Record> newRecords = new ArrayList<>();
        ArrayList<Record> records = my_db.getRecords();
        for (Record r:
                records) {
            if (r.getScore() >= 1000) {
                newRecords.add(r);
            }
        }
        my_db.setRecords(newRecords);
    }
    public void removeButtons() {
        panel_BTN_left.setVisibility(View.INVISIBLE);
        panel_BTN_right.setVisibility(View.INVISIBLE);
    }
}

