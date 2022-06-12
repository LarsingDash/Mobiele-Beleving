package com.example.mobielebeleving.Activities;

import android.os.Build;
import android.os.Bundle;
import android.view.ViewTreeObserver;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mobielebeleving.R;

public class HelpPopup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_popup);

        ConstraintLayout helpPopup = findViewById(R.id.helpPopup);
        helpPopup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                helpPopup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                getWindow().setLayout(helpPopup.getWidth() / 10 * 8, helpPopup.getHeight() / 10 * 7);
            }
        });
    }
}