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

        //Actions that will only be performed on a full startup
        if (firstTime) {
            firstTime = false;

            //Path of appFiles, used for saving achievements
            dir = getFilesDir().getAbsolutePath();
            //Using Settings to obtain the deviceID
            user = new User(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            makeGames();
        }

        //Start LeaderboardActivity after all startup tasks are completed
        startActivity(new Intent(MainActivity.this, LeaderboardActivity.class));
        finish();
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