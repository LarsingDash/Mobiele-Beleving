package com.example.mobielebeleving.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobielebeleving.Data.Land;
import com.example.mobielebeleving.Data.User.User;
import com.example.mobielebeleving.MQTT.TopicHandler;
import com.example.mobielebeleving.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int color = User.getLand().getColor();

        findViewById(R.id.infoScroll).setBackgroundColor(color);
        findViewById(R.id.helpBackButton).setBackgroundColor(color);
        findViewById(R.id.helpBackButton).setOnClickListener(view -> finish());

        findViewById(R.id.debugData).setOnClickListener(view -> {
            Log.println(Log.DEBUG, "DEBUG", "UserData file deleted: " + MainActivity.getUser().getUserDataFile().delete());
            Log.println(Log.DEBUG, "DEBUG", "Achievements file deleted: " + MainActivity.getUser().getAchievementsFile().delete());
            Toast.makeText(this, "[DEBUG]: Data reset.]", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.debugLand).setOnClickListener(view -> {
            MainActivity.getUser().setLand(new Land("null"));
            Toast.makeText(this, "[DEBUG] Land reset.]", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.debugDay).setOnClickListener(view -> {
            TopicHandler.dayCycle();
            MainActivity.getUser().setLand(new Land("null"));
            MainActivity.getUser().setPoints(0);
            Toast.makeText(this, "[DEBUG] Dag reset.", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.helpDebug).setBackgroundColor(color);

        findViewById(R.id.helpLink).setOnClickListener(view -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.freepik.com/vectors/castle-background%27%3ECastle")));
        });
    }
}