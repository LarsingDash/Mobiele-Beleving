package com.example.mobielebeleving.Activities;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobielebeleving.Data.Game;
import com.example.mobielebeleving.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().setWindowAnimations(0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Settings attributes for all views according to the game
        Game game = MainActivity.games.get(getIntent().getExtras().getInt("index"));
        ((ImageView) findViewById(R.id.detailGamePhoto)).setImageDrawable(game.getImage());
        ((TextView) findViewById(R.id.detailGameName)).setText(game.getName());
        ((TextView) findViewById(R.id.detailGameAttraction)).setText(game.getAttraction());
        ((TextView) findViewById(R.id.detailGameDescription)).setText(game.getDescription());

        int color = MainActivity.getUser().getLand().getColor();
        findViewById(R.id.topPart).setBackgroundColor(color);
        findViewById(R.id.locationButton).setBackgroundColor(color);
    }
}