package com.example.mobielebeleving.MQTT;

import java.util.UUID;

import info.mqtt.android.service.MqttAndroidClient;

public class Settings {

    // Information to connect the app with the MQTT Server at esstelstrijd/#
    public static final String brokerHostUrl = "tcp://broker.hivemq.com:1883";
    public static final String username = "";
    public static final String password = "";

    public static final String TAG = "MQTT Debug";

    // Creation of unique client ID for MQTT Server connection
    public static final String clientID = "esstelstrijd_" + UUID.randomUUID().toString();
    public static final int qualityOfService = 0;

    // MQTT Topics every device needs to connect to.
    //Topics related to Droomreis (short version "droom"
    public static final String topicDroomIsAvailable = "esstelstrijd/games/droom/isAvailable";
    public static final String topicDroomCurrentUser = "esstelstrijd/games/droom/currentUser";

    //Topics related to Johan en de Eenhoorn (short version "Jede")
    public static final String topicJedeIsAvailable = "esstelstrijd/games/jede/isAvailable";
    public static final String topicJedeCurrentUser = "esstelstrijd/games/jede/currentUser";

    //Topics related to Festival Overal (short version "Fest")
    public static final String topicFestIsAvailable = "esstelstrijd/games/fest/isAvailable";
    public static final String topicFestCurrentUser = "esstelstrijd/games/fest/currentUser";

    //Topics related to Land's
    public static final String topicLegendelandPoints = "esstelstrijd/lands/Legendeland/points";
    public static final String topicFabelwoudPoints = "esstelstrijd/lands/Fabelwoud/points";
    public static final String topicStoerlandPoints = "esstelstrijd/lands/Stoerland/points";

    //MQTT Andriod Client
    public static MqttAndroidClient mqttAndroidClient;
}