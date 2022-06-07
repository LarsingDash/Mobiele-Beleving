package com.example.mobielebeleving.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobielebeleving.R;
import com.example.mobielebeleving.Views.DetailViewAdapter;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        RecyclerView recyclerView = findViewById(R.id.detailView);
        DetailViewAdapter adapter = new DetailViewAdapter(this, MainActivity.games.get(getIntent().getExtras().getInt("index")));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}