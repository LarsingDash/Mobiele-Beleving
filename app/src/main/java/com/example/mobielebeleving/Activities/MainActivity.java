package com.example.mobielebeleving.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.mobielebeleving.Data.Game;
import com.example.mobielebeleving.Data.User.Icon;
import com.example.mobielebeleving.Data.User.User;
import com.example.mobielebeleving.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static boolean firstTime = true;
    public static ArrayList<Game> games = new ArrayList<>();
    private static User user;
    public static String dir;
    public static HashMap<Integer, Icon> icons = new HashMap<>();

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setWindowAnimations(0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = getApplication().getBaseContext();
        //Actions that will only be performed on a full startup
        if (firstTime) {
            firstTime = false;

            //Path of appFiles, used for saving achievements
            dir = getFilesDir().getAbsolutePath();

            makeIcons();
            makeGames();

            //Using Settings to obtain the deviceID
            user = new User(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        }

        //Start LandActivity after all startup tasks are completed, unless a Land has already been chosen
        if (user.getLand().getName().equals("null")) {
            startActivity(new Intent(MainActivity.this, LandActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, LeaderboardActivity.class));
        }
        finish();
    }

    private void makeIcons() {
        icons.put(0, new Icon(1, AppCompatResources.getDrawable(MainActivity.context, R.drawable.test_icon)));
        icons.put(1, new Icon(2, AppCompatResources.getDrawable(MainActivity.context, R.drawable.profile_icon)));
        icons.put(2, new Icon(3, AppCompatResources.getDrawable(MainActivity.context, R.drawable.leaderboard_icon)));
        icons.put(3, new Icon(3, AppCompatResources.getDrawable(MainActivity.context, R.drawable.games_icon)));
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