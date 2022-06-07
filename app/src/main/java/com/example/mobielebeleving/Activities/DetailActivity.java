package com.example.mobielebeleving.Activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobielebeleving.R;
import com.example.mobielebeleving.Data.Game;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Settings attributes for all views according to the game
        Game game = MainActivity.games.get(getIntent().getExtras().getInt("index"));
        ((ImageView) findViewById(R.id.detailGamePhoto)).setImageDrawable(game.getImage());
        ((TextView) findViewById(R.id.detailGameName)).setText(game.getName());
        ((TextView) findViewById(R.id.detailGameAttraction)).setText(game.getAttraction());
        ((TextView) findViewById(R.id.detailGameDescription)).setText(game.getDescription());
    }
}