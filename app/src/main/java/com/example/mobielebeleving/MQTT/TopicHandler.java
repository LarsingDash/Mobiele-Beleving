package com.example.mobielebeleving.MQTT;

import com.example.mobielebeleving.Data.User.User;

import org.eclipse.paho.client.mqttv3.IMqttToken;

import java.util.Random;

import info.mqtt.android.service.MqttAndroidClient;

public class TopicHandler {

    public static void subscribeToTopic(MqttAndroidClient client, final String topic) {
        // Try to subscribe to the topic
        IMqttToken token = client.subscribe(topic, Settings.qualityOfService);
        System.out.println("Succesvol");
    }

    public static void subscribeToRetainedTopic(MqttAndroidClient client, final String topic) {
        // Try to subscribe to the topic
        IMqttToken token = client.subscribe(topic, 2);
        System.out.println("Succesvol");
    }

    public static void unsubscribeToTopic(MqttAndroidClient client, final String topic) {

        // Try to unsubscribe to the topic
        IMqttToken token = client.unsubscribe(topic);
    }

    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static void runStartupSubscriptions() {
        // Subscribes the user to the each Land's total amount of points.
        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, Settings.topicFabelwoudPoints);
        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, Settings.topicStoerlandPoints);
        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, Settings.topicLegendelandPoints);

        // Subscribes the user to it's own Points.
        String namePoints = "esstelstrijd/users/" + User.getID() + "/points";
        System.out.println(namePoints);
        String randomNumber = Integer.toString(getRandomNumberUsingNextInt(0, 1000));
        Messenger.publishMessage(Settings.mqttAndroidClient, "esstelstrijd/misc/defaultStart", randomNumber);
//        Messenger.publishMessage(Settings.mqttAndroidClient, namePoints, User.getPoints() + "");
        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, namePoints);

        if (User.getLand().getName() != null) {
            String nameLand = "esstelstrijd/users/" + User.getID() + "/land";
            String land = User.getLand().getName();
            Messenger.publishRetainingMessage(Settings.mqttAndroidClient, nameLand, land);
        }
    }

    public static void confirmLandChoice() {
        String nameLand = "esstelstrijd/users/" + User.getID() + "/land";
        String land = User.getLand().getName();
        Messenger.publishRetainingMessage(Settings.mqttAndroidClient, nameLand, land);
    }

    public static void connectToDroom() {
        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, Settings.topicDroomIsAvailable);
        TopicHandler.unsubscribeToTopic(Settings.mqttAndroidClient, Settings.topicDroomIsAvailable);
    }

    public static void connectToJede() {
        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, Settings.topicJedeIsAvailable);
        TopicHandler.unsubscribeToTopic(Settings.mqttAndroidClient, Settings.topicJedeIsAvailable);
    }

    public static void connectToFest() {
        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, Settings.topicFestIsAvailable);
        TopicHandler.unsubscribeToTopic(Settings.mqttAndroidClient, Settings.topicFestIsAvailable);
    }

    public static void linkToDroom() {
        TopicHandler.unsubscribeToTopic(Settings.mqttAndroidClient, Settings.topicDroomIsAvailable);
        Messenger.publishMessage(Settings.mqttAndroidClient, Settings.topicDroomIsAvailable, "no");
        TopicHandler.unsubscribeToTopic(Settings.mqttAndroidClient, Settings.topicDroomCurrentUser);
        Messenger.publishMessage(Settings.mqttAndroidClient, Settings.topicDroomCurrentUser, User.getID());
    }

    public static void linkToJede() {
        TopicHandler.unsubscribeToTopic(Settings.mqttAndroidClient, Settings.topicJedeIsAvailable);
        Messenger.publishMessage(Settings.mqttAndroidClient, Settings.topicJedeIsAvailable, "no");
        TopicHandler.unsubscribeToTopic(Settings.mqttAndroidClient, Settings.topicJedeCurrentUser);
        Messenger.publishMessage(Settings.mqttAndroidClient, Settings.topicJedeCurrentUser, User.getID());
    }

    public static void linkToFest() {
        TopicHandler.unsubscribeToTopic(Settings.mqttAndroidClient, Settings.topicFestIsAvailable);
        Messenger.publishMessage(Settings.mqttAndroidClient, Settings.topicFestIsAvailable, "no");
        TopicHandler.unsubscribeToTopic(Settings.mqttAndroidClient, Settings.topicFestCurrentUser);
        Messenger.publishMessage(Settings.mqttAndroidClient, Settings.topicFestCurrentUser, User.getID());
    }

    public static void dayCycle() {
        Messenger.publishRetainingMessage(Settings.mqttAndroidClient, Settings.topicDroomIsAvailable, "yes");
        Messenger.publishRetainingMessage(Settings.mqttAndroidClient, Settings.topicFestIsAvailable, "yes");
        Messenger.publishRetainingMessage(Settings.mqttAndroidClient, Settings.topicJedeIsAvailable, "yes");

        Messenger.publishRetainingMessage(Settings.mqttAndroidClient, "esstelstrijd/users/" + User.getID() + "/points", "0");

        Messenger.publishRetainingMessage(Settings.mqttAndroidClient, Settings.topicLegendelandPoints, "0");
        Messenger.publishRetainingMessage(Settings.mqttAndroidClient, Settings.topicFabelwoudPoints, "0");
        Messenger.publishRetainingMessage(Settings.mqttAndroidClient, Settings.topicStoerlandPoints, "0");

        Messenger.publishRetainingMessage(Settings.mqttAndroidClient, Settings.topicDroomCurrentUser, "default");
        Messenger.publishRetainingMessage(Settings.mqttAndroidClient, Settings.topicFestCurrentUser, "default");
        Messenger.publishRetainingMessage(Settings.mqttAndroidClient, Settings.topicJedeCurrentUser, "default");
    }
}
