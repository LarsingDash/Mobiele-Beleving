package com.example.mobielebeleving.MQTT;

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
        TopicHandler.subscribeToTopic(Settings.mqttAndroidClient, Settings.topicFabelwoudPoints);
    }
}
