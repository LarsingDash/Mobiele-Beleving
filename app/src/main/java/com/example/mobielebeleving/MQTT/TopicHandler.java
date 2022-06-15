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
        String name = "esstelstrijd/users/" + User.getID() + "/points";
        System.out.println(name);
        String randomNumber = Integer.toString(getRandomNumberUsingNextInt(0, 1000));
        Messenger.publishMessage(Settings.mqttAndroidClient, "esstelstrijd/misc/defaultStart", randomNumber);
        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, name);
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
        Messenger.publishMessage(Settings.mqttAndroidClient, Settings.topicDroomCurrentUser, User.getID());
        Messenger.publishMessage(Settings.mqttAndroidClient, Settings.topicDroomIsAvailable, "no");
        TopicHandler.unsubscribeToTopic(Settings.mqttAndroidClient, Settings.topicDroomCurrentUser);
        TopicHandler.unsubscribeToTopic(Settings.mqttAndroidClient, Settings.topicDroomIsAvailable);
    }

    public static void linkToJede() {
        Messenger.publishMessage(Settings.mqttAndroidClient, Settings.topicJedeCurrentUser, User.getID());
        Messenger.publishMessage(Settings.mqttAndroidClient, Settings.topicJedeIsAvailable, "no");
        TopicHandler.unsubscribeToTopic(Settings.mqttAndroidClient, Settings.topicJedeCurrentUser);
        TopicHandler.unsubscribeToTopic(Settings.mqttAndroidClient, Settings.topicJedeIsAvailable);
    }

    public static void linkToFest() {
        Messenger.publishMessage(Settings.mqttAndroidClient, Settings.topicFestCurrentUser, User.getID());
        Messenger.publishMessage(Settings.mqttAndroidClient, Settings.topicFestIsAvailable, "no");
        TopicHandler.unsubscribeToTopic(Settings.mqttAndroidClient, Settings.topicFestCurrentUser);
        TopicHandler.unsubscribeToTopic(Settings.mqttAndroidClient, Settings.topicFestIsAvailable);
    }
}
