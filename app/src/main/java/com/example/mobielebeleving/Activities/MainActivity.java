package com.example.mobielebeleving.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobielebeleving.Data.Game;
import com.example.mobielebeleving.Data.User;
import com.example.mobielebeleving.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static boolean firstTime = true;
    public static ArrayList<Game> games = new ArrayList<>();
    private static User user;
    public static String dir;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dir = getFilesDir().getAbsolutePath();

        if (firstTime) {
            firstTime = false;

            user = new User(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            makeGames();
        }

        final int[] counter = {0};
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                counter[0] += 1;

                if (counter[0] == 1) {
                    startActivity(new Intent(MainActivity.this, LeaderboardActivity.class));
                    finish();
                }
            }
        }, 0, 1000);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void makeGames() {
        games.add(new Game("Festival Overal",
                "Festival Overal",
                getDrawable(R.drawable.ic_launcher_background),
                "yes",
                10,
                new Point(0, 0)));

        games.add(new Game("Johan en de Eenhoorn",
                "Johan en de Eenhoorn",
                getDrawable(R.drawable.ic_launcher_background),
                "yes",
                10,
                new Point(0, 0)));

        games.add(new Game("Droomreis",
                "Droomreis",
                getDrawable(R.drawable.ic_launcher_background),
                "yes",
                10,
                new Point(0, 0)));
    }

    public static User getUser() {
        return user;
    }
}