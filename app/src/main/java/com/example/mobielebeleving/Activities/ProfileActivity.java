package com.example.mobielebeleving.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobielebeleving.Data.Achievement.Pronoun;
import com.example.mobielebeleving.Data.Achievement.Title;
import com.example.mobielebeleving.Data.User;
import com.example.mobielebeleving.R;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private User user;
    private ArrayList<Pronoun> availablePronouns = new ArrayList<>();
    private ArrayList<Title> availableTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = MainActivity.getUser();

        ((TextView) findViewById(R.id.userID)).setText(user.getID());
    }
}