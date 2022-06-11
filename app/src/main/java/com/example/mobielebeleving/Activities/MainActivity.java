package com.example.mobielebeleving.Activities;


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
import com.example.mobielebeleving.Data.User.Icon;
import com.example.mobielebeleving.Data.User.User;
import com.example.mobielebeleving.MQTT.Messenger;
import com.example.mobielebeleving.MQTT.Settings;
import com.example.mobielebeleving.R;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

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

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    private void makeGames() {
        games.add(new Game("Festival Overal",
                "Festival Overal",
                AppCompatResources.getDrawable(this, R.drawable.game1),
                getResources().getString(R.string.game1story),
                getResources().getString(R.string.game1explanation),
                new Point(0, 0)));

        games.add(new Game("Johan en de Eenhoorn",
                "Johan en de Eenhoorn",
                AppCompatResources.getDrawable(this, R.drawable.game2),
                getResources().getString(R.string.game2story),
                getResources().getString(R.string.game2explanation),
                new Point(0, 0)));

        games.add(new Game("Droomreis",
                "Droomreis",
                AppCompatResources.getDrawable(this, R.drawable.game3),
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
                switch (topic) {
                    //Topic check for each land's points
                    case Settings.topicFabelwoudPoints:
                        System.out.println("TO-DO");
                        break;

                    case Settings.topicLegendelandPoints:
                        System.out.println("TO-DO");
                        break;

                    case Settings.topicStoerlandPoints:
                        System.out.println("TO-DO");
                        break;

                    //Topic check for Droomreis topics
                    case Settings.topicDroomIsAvailable:
                        if (message.toString().equals("yes")) {
                            System.out.println("Entering");
                            Toast toast = Toast.makeText(getApplication().getBaseContext(), "Game is already in use.", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {

                        }
                        break;

                    case "esstelstrijd/users/defaultUser":
                        System.out.println("we in");
                        int points = Integer.parseInt(message.toString());
                        user.setPoints(points);
                        break;
                }




                if (topic.equals(Settings.topicFabelwoudPoints)) {
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

        if(text.equals("spel 1")){
            System.out.println("start spel 1");
            Toast toast= Toast.makeText(this,text,Toast.LENGTH_SHORT);
            toast.show();
            Messenger.publishMessage(Settings.mqttAndroidClient, "esstelstrijd/users/defaultUser", "420");
            System.out.println("it did");
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