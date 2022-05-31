package com.example.mobielebeleving.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobielebeleving.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void test() {
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
    }
}