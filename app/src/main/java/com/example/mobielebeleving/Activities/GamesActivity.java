package com.example.mobielebeleving.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobielebeleving.Activities.Views.GameViewAdapter;
import com.example.mobielebeleving.R;

public class GamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        createAdapters();
    }

    private void createAdapters() {
        RecyclerView recyclerView = findViewById(R.id.gamesView);
        GameViewAdapter adapter = new GameViewAdapter(this, MainActivity.games);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}