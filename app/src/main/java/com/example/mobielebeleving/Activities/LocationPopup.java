package com.example.mobielebeleving.Activities;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mobielebeleving.R;

import java.util.Objects;

public class LocationPopup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_popup);

        Objects.requireNonNull(MainActivity.getUser().getAchievements().get("Locatie")).collect(false);

        ImageView image = findViewById(R.id.location);
        switch (getIntent().getExtras().getString("game")) {
            case "Smiley's feest":
                image.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.festival_location));
                break;

            case "Epische strijd":
                image.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.johan_location));
                break;

            case "Magische verdediging":
                image.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.droomreis_location));
                break;
        }

        ConstraintLayout locationPopup = findViewById(R.id.locationPopup);
        locationPopup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                locationPopup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int dimension;

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    dimension = (int) (locationPopup.getWidth() / 10 * 9.5d);
                } else {
                    dimension = (int) (locationPopup.getHeight() / 10 * 9.5d);
                }

                getWindow().setLayout(dimension, dimension);
            }
        });
    }
}