package com.example.mobielebeleving.MQTT;

import com.example.mobielebeleving.Data.User.User;

import org.eclipse.paho.client.mqttv3.IMqttToken;

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

    public static void runStartupSubscriptions() {
        // Subscribes the user to the each Land's total amount of points.
        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, Settings.topicFabelwoudPoints);
        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, Settings.topicStoerlandPoints);
        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, Settings.topicLegendelandPoints);

        // Subscribes the user to it's own Point & Land topic.
        String usesPointsTopic = "esstelstrijd/users/" + User.getID() + "/points";
        String userLandTopic = "esstelstrijd/users/" + User.getID() + User.getLand().getName();
        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, usesPointsTopic);
        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, userLandTopic);
    }
}
