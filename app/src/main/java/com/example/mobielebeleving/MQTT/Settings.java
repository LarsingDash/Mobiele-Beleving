package com.example.mobielebeleving.MQTT;
import org.eclipse.paho.android.service.MqttAndroidClient;

import java.util.UUID;

public class Settings {

    // Information to connect the app with the MQTT Server at esstelstrijd/#
    public static final String brokerHostUrl = "tcp://broker.hivemq.com:1883";
    public static final String username = "";
    public static final String password = "";

    // Creation of unique client ID for MQTT Server connection
    public static final String clientID = "esstelstrijd_" + UUID.randomUUID().toString();
    public static final int qualityOfService = 0;

    // MQTT topics to subscribe for (temporarily disabled due to runtime exceptions, cause unknown)
    public static final String topicName = "esstelstrijd/games/";

    public static MqttAndroidClient mqttAndroidClient;
}
