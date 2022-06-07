package com.example.mobielebeleving.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobielebeleving.R;
import com.example.mobielebeleving.data.Game;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Game> games = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeGames();

        final int[] counter = {0};
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                counter[0] += 1;

                if (counter[0] == 3) {
                    startActivity(new Intent(MainActivity.this, LeaderboardActivity.class));
                    finish();
                }
            }
        }, 0, 1000);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void makeGames() {
        games.clear();

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
}