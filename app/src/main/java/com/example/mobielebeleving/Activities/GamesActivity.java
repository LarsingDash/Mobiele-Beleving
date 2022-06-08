package com.example.mobielebeleving.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobielebeleving.R;
import com.example.mobielebeleving.Views.GameViewAdapter;

public class GamesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        getWindow().setWindowAnimations(0);

        //Binding RecyclerView
        RecyclerView recyclerView = findViewById(R.id.gamesView);
        GameViewAdapter adapter = new GameViewAdapter(this, MainActivity.games);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}