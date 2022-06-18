package com.example.mobielebeleving.MQTT;

public class GameConnector {

    public static void startDroomreis(){

        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, "esstelstrijd/games/droomreis/isAvailable");
        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, "esstelstrijd/games/droomreis/currentUser");


    }
}
