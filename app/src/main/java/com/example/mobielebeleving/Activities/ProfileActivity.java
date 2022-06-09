package com.example.mobielebeleving.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobielebeleving.Data.Land;
import com.example.mobielebeleving.Data.User.Icon;
import com.example.mobielebeleving.Data.User.Pronoun;
import com.example.mobielebeleving.Data.User.Title;
import com.example.mobielebeleving.Data.User.User;
import com.example.mobielebeleving.R;
import com.example.mobielebeleving.Views.AchievementsViewAdapter;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private User user;
    public static ArrayList<Icon> availableIcons = new ArrayList<>();
    public static ArrayList<Pronoun> availablePronouns = new ArrayList<>();
    public static ArrayList<Title> availableTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setWindowAnimations(0);

        user = MainActivity.getUser();

        ((TextView) findViewById(R.id.userID)).setOnClickListener(view -> user.setLand(new Land("null")));

        //Set initial values for Icon and UserID
        ImageView icon = findViewById(R.id.profileIcon);
        icon.setImageDrawable(user.getIcon().getIcon());
        ((TextView) findViewById(R.id.userID)).setText(user.getID());

        makeIconButtons();
        makePronounSpinner();
        makeTitleSpinner();

        //Binding RecyclerView for the achievementView
        RecyclerView recyclerView = findViewById(R.id.achievementView);
        AchievementsViewAdapter adapter = new AchievementsViewAdapter(this, new ArrayList<>(user.getAchievements().values()));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void makeIconButtons() {
        ImageView icon = findViewById(R.id.profileIcon);

        //Previous button
        findViewById(R.id.profileIconPrevious).setOnClickListener(view -> {
            //Get index of currently selected Icon + 1
            int i = availableIcons.indexOf(user.getIcon()) - 1;

            //Make sure the index loops in the array
            if (i < 0) i = availableIcons.size() - 1;

            //Set the new Icon
            Log.println(Log.DEBUG, "DEBUG", i + "");
            user.setIcon(availableIcons.get(i));
            icon.setImageDrawable(user.getIcon().getIcon());
        });

        //Next button
        findViewById(R.id.profileIconNext).setOnClickListener(view -> {
            //Get index of currently selected Icon - 1
            int i = availableIcons.indexOf(user.getIcon()) + 1;

            Log.println(Log.DEBUG, "DEBUG", "Size: " + availableIcons.size());
            //Make sure the index loops in the array
            if (i == availableIcons.size()) i = 0;

            //Set the new Icon
            Log.println(Log.DEBUG, "DEBUG", "I: " + i);
            user.setIcon(availableIcons.get(i));
            icon.setImageDrawable(user.getIcon().getIcon());
        });
    }

    private void makePronounSpinner() {
        //Because binding the adapter will override the data with a default value, we will need to save the original value as a target to set it to afterwards
        String target = user.getPronoun().name();

        //Creating and binding the Adapter for the Spinner
        Spinner pronounSpinner = findViewById(R.id.profilePronoun);
        ArrayAdapter<Pronoun> pronounAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, availablePronouns);
        pronounAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pronounSpinner.setAdapter(pronounAdapter);

        //Setting the selected value to the target that was saved before creating the Adapter
        for (int i = 0; i < availablePronouns.size(); i++) {
            if (((Pronoun) pronounSpinner.getItemAtPosition(i)).name().equals(target)) {
                pronounSpinner.setSelection(i);
            }
        }

        //Action event for when an item is selected
        pronounSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Set the pronoun to the new item
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
        //SAME THING AS THE PRONOUN SPINNER, BUT THEN WITH TITLES

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