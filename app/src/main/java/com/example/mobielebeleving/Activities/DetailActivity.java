package com.example.mobielebeleving.Activities;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobielebeleving.Data.Game;
import com.example.mobielebeleving.Data.User.User;
import com.example.mobielebeleving.MQTT.Messenger;
import com.example.mobielebeleving.MQTT.Settings;
import com.example.mobielebeleving.MQTT.TopicHandler;
import com.example.mobielebeleving.R;

public class DetailActivity extends AppCompatActivity {

    private int itemID;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().setWindowAnimations(0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Settings attributes for all views according to the game
        Game game = MainActivity.games.get(getIntent().getExtras().getInt("index"));
        ((ImageView) findViewById(R.id.detailGamePhoto)).setImageDrawable(game.getImage());
        ((TextView) findViewById(R.id.detailGameAttraction)).setText("Elke tik is 1 punt waard!");

        switch (User.getLand().getName()) {
            case "Fabelwoud":
                ((ImageView) findViewById(R.id.detailPlusOne)).setImageDrawable(getResources().getDrawable(R.drawable.plusonefabel));
                ((ImageView) findViewById(R.id.detailPlusTwo)).setImageDrawable(getResources().getDrawable(R.drawable.plusonefabel));
                ((ImageView) findViewById(R.id.detailPlusThree)).setImageDrawable(getResources().getDrawable(R.drawable.plusonefabel));
                ((ImageView) findViewById(R.id.detailPlusFour)).setImageDrawable(getResources().getDrawable(R.drawable.plusonefabel));
                break;

            case "Legendeland":
                ((ImageView) findViewById(R.id.detailPlusOne)).setImageDrawable(getResources().getDrawable(R.drawable.plusonelegende));
                ((ImageView) findViewById(R.id.detailPlusTwo)).setImageDrawable(getResources().getDrawable(R.drawable.plusonelegende));
                ((ImageView) findViewById(R.id.detailPlusThree)).setImageDrawable(getResources().getDrawable(R.drawable.plusonelegende));
                ((ImageView) findViewById(R.id.detailPlusFour)).setImageDrawable(getResources().getDrawable(R.drawable.plusonelegende));
                break;

            case "Stoerland":
                ((ImageView) findViewById(R.id.detailPlusOne)).setImageDrawable(getResources().getDrawable(R.drawable.plusonestoer));
                ((ImageView) findViewById(R.id.detailPlusTwo)).setImageDrawable(getResources().getDrawable(R.drawable.plusonestoer));
                ((ImageView) findViewById(R.id.detailPlusThree)).setImageDrawable(getResources().getDrawable(R.drawable.plusonestoer));
                ((ImageView) findViewById(R.id.detailPlusFour)).setImageDrawable(getResources().getDrawable(R.drawable.plusonestoer));
                break;
        }

        findViewById(R.id.detailPlusOne).setVisibility(View.INVISIBLE);
        findViewById(R.id.detailPlusTwo).setVisibility(View.INVISIBLE);
        findViewById(R.id.detailPlusThree).setVisibility(View.INVISIBLE);
        findViewById(R.id.detailPlusFour).setVisibility(View.INVISIBLE);

        //Use HTML to get a part of the TextView in Bold and the other not
//        String description = "<b>Verhaal</b><br>" + game.getStory() + "<br><br><b>Uitleg</b><br>" + game.getExplanation();
//        ((TextView) findViewById(R.id.detailGameDescription)).setText(Html.fromHtml(description));

        //Set colors according to land
        int color = MainActivity.getUser().getLand().getColor();
        findViewById(R.id.topPart).setBackgroundColor(color);
//        findViewById(R.id.locationButton).setBackgroundColor(color);

        Drawable gameIcon = game.getGameIcon();
        ((ImageView) findViewById(R.id.locationButton)).setImageDrawable(gameIcon);

        //Location button popup
        findViewById(R.id.locationButton).setOnClickListener(view -> {

            findViewById(R.id.detailPlusOne).setVisibility(View.INVISIBLE);
            findViewById(R.id.detailPlusTwo).setVisibility(View.INVISIBLE);
            findViewById(R.id.detailPlusThree).setVisibility(View.INVISIBLE);
            findViewById(R.id.detailPlusFour).setVisibility(View.INVISIBLE);

            String newPoints = toString().valueOf(User.getPoints() + 1);
            Messenger.publishRetainingMessage(Settings.mqttAndroidClient, "esstelstrijd/users/" + User.getID() + "/points", newPoints );


            int newNumber = TopicHandler.getRandomNumberUsingNextInt(4, 8);
            while (newNumber == itemID) {
                newNumber = TopicHandler.getRandomNumberUsingNextInt(4, 8);
            }
            itemID = newNumber;

            switch (itemID % 4) {
                case 0:
                    findViewById(R.id.detailPlusOne).setVisibility(View.VISIBLE);
                    break;

                case 1:
                        findViewById(R.id.detailPlusTwo).setVisibility(View.VISIBLE);
                    break;

                case 2:
                        findViewById(R.id.detailPlusThree).setVisibility(View.VISIBLE);
                    break;

                case 3:
                        findViewById(R.id.detailPlusFour).setVisibility(View.VISIBLE);
                    break;
            }


        });
    }
}