package com.example.mobielebeleving.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobielebeleving.Data.Land;
import com.example.mobielebeleving.R;
import com.example.mobielebeleving.Views.LandAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class LandActivity extends AppCompatActivity {
    private final ArrayList<Land> lands = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land);
        getWindow().setWindowAnimations(0);

        //Adding all Lands
        Collections.addAll(lands, new Land(Land.LegendeLand), new Land(Land.StoerLand), new Land(Land.Fabelwoud));

        //Binding Adapter
        LandAdapter adapter = new LandAdapter(this, lands);
        ViewPager2 pager = findViewById(R.id.landPager);
        pager.setAdapter(adapter);
    }
}