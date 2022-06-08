package com.example.mobielebeleving.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobielebeleving.Data.User.Pronoun;
import com.example.mobielebeleving.Data.User.Title;
import com.example.mobielebeleving.Data.User.User;
import com.example.mobielebeleving.R;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private User user;
    public static ArrayList<Pronoun> availablePronouns = new ArrayList<>();
    public static ArrayList<Title> availableTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = MainActivity.getUser();

        ((TextView) findViewById(R.id.userID)).setText(user.getID());

        makePronounSpinner();
        makeTitleSpinner();
    }

    private void makePronounSpinner() {
        String target = user.getPronoun().name();

        Spinner pronounSpinner = findViewById(R.id.profilePronoun);
        ArrayAdapter<Pronoun> pronounAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, availablePronouns);
        pronounAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pronounSpinner.setAdapter(pronounAdapter);

        for (int i = 0; i < availablePronouns.size(); i++) {
            if (((Pronoun) pronounSpinner.getItemAtPosition(i)).name().equals(target)) {
                pronounSpinner.setSelection(i);
            }
        }

        pronounSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user.setPronoun((Pronoun) adapterView.getSelectedItem());
                Log.println(Log.DEBUG, "DEBUG", "Pronoun changed to: " + user.getPronoun().name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.println(Log.DEBUG, "DEBUG", "no new pronoun selected");
            }
        });
    }

    private void makeTitleSpinner() {
        String target = user.getTitle().name();
        
        Spinner titleSpinner = findViewById(R.id.profileTitle);
        ArrayAdapter<Title> titleAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, availableTitles);
        titleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titleSpinner.setAdapter(titleAdapter);

        for (int i = 0; i < availableTitles.size(); i++) {
            if (((Title) titleSpinner.getItemAtPosition(i)).name().equals(target)) {
                titleSpinner.setSelection(i);
            }
        }

        titleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user.setTitle((Title) adapterView.getSelectedItem());
                Log.println(Log.DEBUG, "DEBUG", "Title changed to: " + user.getTitle().name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.println(Log.DEBUG, "DEBUG", "no new title selected");
            }
        });
    }
}