package com.example.mobielebeleving.Activities;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobielebeleving.Data.User.User;
import com.example.mobielebeleving.R;

public class HelpPopup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_popup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.infoScroll).setBackgroundColor(User.getLand().getColor());
        findViewById(R.id.helpBackButton).setBackgroundColor(User.getLand().getColor());

        findViewById(R.id.helpBackButton).setOnClickListener(view -> finish());
    }
}