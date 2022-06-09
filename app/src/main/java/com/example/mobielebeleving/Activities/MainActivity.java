package com.example.mobielebeleving.Activities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.mobielebeleving.Data.Game;
import com.example.mobielebeleving.Data.User.Icon;
import com.example.mobielebeleving.Data.User.User;
import com.example.mobielebeleving.MQTT.Settings;
import com.example.mobielebeleving.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static boolean firstTime = true;
    public static ArrayList<Game> games = new ArrayList<>();
    private static User user;
    public static String dir;
    public static HashMap<Integer, Icon> icons = new HashMap<>();

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setWindowAnimations(0);

        MQTTConnect();

        context = getApplication().getBaseContext();
        //Actions that will only be performed on a full startup
        if (firstTime) {
            firstTime = false;

            //Path of appFiles, used for saving achievements
            dir = getFilesDir().getAbsolutePath();

            makeIcons();
            makeGames();

            //Using Settings to obtain the deviceID
            user = new User(android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID));
        }

        //Start LeaderboardActivity after all startup tasks are completed
        startActivity(new Intent(MainActivity.this, LeaderboardActivity.class));
        finish();
    }

    private void makeIcons() {
        icons.put(1, new Icon(1, AppCompatResources.getDrawable(MainActivity.context, R.drawable.test_icon)));
        icons.put(2, new Icon(2, AppCompatResources.getDrawable(MainActivity.context, R.drawable.games_icon)));
        icons.put(3, new Icon(3, AppCompatResources.getDrawable(MainActivity.context, R.drawable.ic_launcher_background)));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void makeGames() {
        games.add(new Game("Festival Overal",
                "Festival Overal",
                getDrawable(R.drawable.games_icon),
                "yes",
                10,
                new Point(0, 0)));

        games.add(new Game("Johan en de Eenhoorn",
                "Johan en de Eenhoorn",
                getDrawable(R.drawable.leaderboard_icon),
                "yes",
                10,
                new Point(0, 0)));

        games.add(new Game("Droomreis",
                "Droomreis",
                getDrawable(R.drawable.profile_icon),
                "yes",
                10,
                new Point(0, 0)));
    }

    private void MQTTConnect() {
        Settings.mqttAndroidClient =
                new MqttAndroidClient(this.getApplicationContext(), Settings.brokerHostUrl,
                        Settings.clientID);

        try {
            IMqttToken token = Settings.mqttAndroidClient.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(Settings.TAG, "onSuccess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(Settings.TAG, "onFailure" + exception);

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        Settings.mqttAndroidClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                // Check what topic the message is for and handle accordingly
                if (topic.equals(Settings.topicFabelwoud)) {
                    System.out.println(message.toString());
                } else if (topic.equals("larstest/lars")) {
                    System.out.println(message.toString());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
    }

    public static User getUser() {
        return user;
    }
}