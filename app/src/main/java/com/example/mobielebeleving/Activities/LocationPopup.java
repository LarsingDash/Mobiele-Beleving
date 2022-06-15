package com.example.mobielebeleving.Activities;

import android.os.Build;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mobielebeleving.R;

public class LocationPopup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_popup);

        ImageView image = findViewById(R.id.location);
        //Todo change to new names
        switch (getIntent().getExtras().getString("game")) {
            case "Festival Overal":
                image.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon1));
                break;

            case "Johan en de Eenhoorn":
                image.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon2));
                break;

            case "Droomreis":
                image.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon3));
        }

        ConstraintLayout locationPopup = findViewById(R.id.locationPopup);
        locationPopup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                locationPopup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int dimension = locationPopup.getWidth() / 10 * 9;
                getWindow().setLayout(dimension, dimension);
            }
        });
    }
}