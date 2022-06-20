package com.example.mobielebeleving.Activities;


import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.mobielebeleving.Data.Game;
import com.example.mobielebeleving.Data.Land;
import com.example.mobielebeleving.Data.User.Icon;
import com.example.mobielebeleving.Data.User.User;
import com.example.mobielebeleving.MQTT.Settings;
import com.example.mobielebeleving.MQTT.TopicHandler;
import com.example.mobielebeleving.R;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import info.mqtt.android.service.Ack;
import info.mqtt.android.service.MqttAndroidClient;

public class MainActivity extends AppCompatActivity {
    private static boolean firstTime = true;
    public static ArrayList<Game> games = new ArrayList<>();
    private static User user;
    public static String dir;
    public static HashMap<Integer, Icon> icons = new HashMap<>();

    public static NfcAdapter nfcAdapter;
    public static PendingIntent pendingIntent;
    public static IntentFilter writingTagFilters[];
    public static Tag myTag;
    public static String nfcTag;

    public static boolean canSubscribe = false;

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setWindowAnimations(0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        NFCStart();
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

            new Thread(() -> {
                try {
                    while (!canSubscribe) {
                        Thread.sleep(1500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                TopicHandler.runStartupSubscriptions();
            }).start();
        }

        //Start LandActivity after all startup tasks are completed, unless a Land has already been chosen
        if (user.getLand().getName().equals("null")) {
            startActivity(new Intent(MainActivity.this, LandActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, LeaderboardActivity.class));
        }
        finish();
    }

    private void makeIcons() {
        icons.put(0, new Icon(0, AppCompatResources.getDrawable(MainActivity.context, R.drawable.icon1)));
        icons.put(1, new Icon(1, AppCompatResources.getDrawable(MainActivity.context, R.drawable.icon2)));
        icons.put(2, new Icon(2, AppCompatResources.getDrawable(MainActivity.context, R.drawable.icon3)));
        icons.put(3, new Icon(3, AppCompatResources.getDrawable(MainActivity.context, R.drawable.icon4)));
        icons.put(4, new Icon(3, AppCompatResources.getDrawable(MainActivity.context, R.drawable.icon5)));
        icons.put(5, new Icon(3, AppCompatResources.getDrawable(MainActivity.context, R.drawable.icon6)));
        icons.put(6, new Icon(3, AppCompatResources.getDrawable(MainActivity.context, R.drawable.icon7)));
        icons.put(7, new Icon(3, AppCompatResources.getDrawable(MainActivity.context, R.drawable.icon8)));
        icons.put(8, new Icon(3, AppCompatResources.getDrawable(MainActivity.context, R.drawable.icon9)));
        icons.put(9, new Icon(3, AppCompatResources.getDrawable(MainActivity.context, R.drawable.icon91)));
    }

    private void makeGames() {
        games.add(new Game("Smiley's feest",
                "Festival Overal",
                AppCompatResources.getDrawable(this, R.drawable.festivaloveral),
                getResources().getString(R.string.game1story),
                getResources().getString(R.string.game1explanation),
                new Point(0, 0)));

        games.add(new Game("Epische strijd",
                "Johan en de Eenhoorn",
                AppCompatResources.getDrawable(this, R.drawable.johanendeeenhoorn),
                getResources().getString(R.string.game2story),
                getResources().getString(R.string.game2explanation),
                new Point(0, 0)));

        games.add(new Game("Magische verdediging",
                "Droomreis",
                AppCompatResources.getDrawable(this, R.drawable.droomreis),
                getResources().getString(R.string.game3story),
                getResources().getString(R.string.game3explanation),
                new Point(0, 0)));
    }

    private void MQTTConnect() {
        Settings.mqttAndroidClient =
                new MqttAndroidClient(this.getApplicationContext(), Settings.brokerHostUrl,
                        Settings.clientID, Ack.AUTO_ACK);

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
        Settings.mqttAndroidClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                // Check what topic the message is for and handle accordingly

                System.out.println("Topic: " + topic + "\t|\t" + "Message: " + message.toString());

                if (topic.equals("esstelstrijd/users/" + User.getID() + "/points")) {
                    System.out.println("we got here");
                    Toast toastPoints = Toast.makeText(getApplication().getBaseContext(), "Points added to user", Toast.LENGTH_SHORT);
                    toastPoints.show();
                    int points = parseInt(message.toString());
                    user.setPoints(points);
                }

                switch (topic) {
                    //Topic check for each land's points
                    case Settings.topicFabelwoudPoints:
                        LeaderboardActivity.setBar(Land.Legendeland, LeaderboardActivity.legendeland);
                        LeaderboardActivity.setBar(Land.Stoerland, LeaderboardActivity.stoerland);
                        LeaderboardActivity.setBar(Land.Fabelwoud, Integer.parseInt(message.toString()));
                        break;

                    case Settings.topicLegendelandPoints:
                        LeaderboardActivity.setBar(Land.Legendeland, Integer.parseInt(message.toString()));
                        LeaderboardActivity.setBar(Land.Stoerland, LeaderboardActivity.stoerland);
                        LeaderboardActivity.setBar(Land.Fabelwoud, LeaderboardActivity.fabelwoud);
                        break;

                    case Settings.topicStoerlandPoints:
                        LeaderboardActivity.setBar(Land.Legendeland, LeaderboardActivity.legendeland);
                        LeaderboardActivity.setBar(Land.Stoerland, Integer.parseInt(message.toString()));
                        LeaderboardActivity.setBar(Land.Fabelwoud, LeaderboardActivity.fabelwoud);
                        break;

                    //Topic check for Droomreis topics
                    case Settings.topicDroomIsAvailable:
                        if (message.toString().equals("yes")) {
                            Toast toastDroom = Toast.makeText(getApplication().getBaseContext(), "Connecting to game", Toast.LENGTH_SHORT);
                            toastDroom.show();

                            Objects.requireNonNull(MainActivity.getUser().getAchievements().get("Magische Verdediging")).collect(false);

                            TopicHandler.linkToDroom();
                        } else {
                            Toast toastDroom = Toast.makeText(getApplication().getBaseContext(), "Game already in use, try again later.", Toast.LENGTH_SHORT);
                            toastDroom.show();
                        }
                        break;

                        //Topic check for Johan en de Eenhoorn topics
                    case Settings.topicJedeIsAvailable:
                        if (message.toString().equals("yes")) {
                            Toast toastJede = Toast.makeText(getApplication().getBaseContext(), "Connecting to game", Toast.LENGTH_SHORT);
                            toastJede.show();

                            Objects.requireNonNull(MainActivity.getUser().getAchievements().get("Epische Strijd")).collect(false);

                            TopicHandler.linkToJede();
                        } else {
                            Toast toastJede = Toast.makeText(getApplication().getBaseContext(), "Game already in use, try again later.", Toast.LENGTH_SHORT);
                            toastJede.show();
                        }
                        break;

                        //Topic check for Festival topics
                    case Settings.topicFestIsAvailable:
                        if (message.toString().equals("yes")) {
                            Toast toastFest = Toast.makeText(getApplication().getBaseContext(), "Connecting to game", Toast.LENGTH_SHORT);
                            toastFest.show();

                            Objects.requireNonNull(MainActivity.getUser().getAchievements().get("Smiley's feest")).collect(false);

                            TopicHandler.linkToFest();
                        } else {
                            Toast toastFest = Toast.makeText(getApplication().getBaseContext(), "Game already in use, try again later.", Toast.LENGTH_SHORT);
                            toastFest.show();
                        }
                        break;
                }
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
    }

    public void NFCStart(){
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "this device does not support NFC", Toast.LENGTH_SHORT).show();
            finish();
        }

        readformIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_IMMUTABLE);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writingTagFilters = new IntentFilter[]{tagDetected};
    }

    private void readformIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }
    // zorgt voor het uitprinten van de nfc context
    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) {
            return;
        }
        String text = "";
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        int lanquageCodeLenth = payload[0] & 0063;

        try {
            text = new String(payload, lanquageCodeLenth + 1, payload.length - lanquageCodeLenth - 1, textEncoding);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        nfcTag= text;


        switch (text) {
            case "Droomreis Activation TAG":
                TopicHandler.connectToDroom();
                Toast toastDroom = Toast.makeText(getApplication().getBaseContext(), "Connecting to game", Toast.LENGTH_SHORT);
                toastDroom.show();
                break;

            case "Johan en de Eenhoorn Activation TAG":
                TopicHandler.connectToJede();
                Toast toastJede = Toast.makeText(getApplication().getBaseContext(), "Connecting to game", Toast.LENGTH_SHORT);
                toastJede.show();
                break;

            case "Festival Activation TAG":
                TopicHandler.connectToFest();
                Toast toastFest = Toast.makeText(getApplication().getBaseContext(), "Connecting to game", Toast.LENGTH_SHORT);
                toastFest.show();
                break;

        }
    }
    //belangeerijk voor afdrukken;
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        readformIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();}

    public static User getUser() {
        return user;
    }
}