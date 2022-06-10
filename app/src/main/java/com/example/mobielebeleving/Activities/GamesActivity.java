package com.example.mobielebeleving.Activities;

import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Binding RecyclerView
        RecyclerView recyclerView = findViewById(R.id.gamesView);
        GameViewAdapter adapter = new GameViewAdapter(this, MainActivity.games);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Getting the total height of the recycler view so the items can be spread out
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                adapter.spreadOut(recyclerView.getHeight());
            }
        });

        //Change color of topBar to color of Land
        findViewById(R.id.topBar).setBackgroundColor(MainActivity.getUser().getLand().getColor());
    }
}